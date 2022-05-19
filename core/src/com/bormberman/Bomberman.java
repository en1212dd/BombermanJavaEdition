package com.bormberman;

import java.util.EnumMap;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.SkinLoader.SkinParameter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Colors;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.bormberman.audio.AudioManager;
import com.bormberman.ecs.ESCEngine;
import com.bormberman.input.InputManager;
import com.bormberman.map.MapManager;
import com.bormberman.screens.ScreenType;
import com.bormberman.ui.GameRederer;



public class Bomberman extends Game {
	//static constants
    public static final float UNIT_SCALE=1/16f;
	private static final String TAG = Bomberman.class.getSimpleName();

	private static final float FIXED_TIME_STEP = 1/60f;
	public static final BodyDef BODY_DEF = new BodyDef();
	public static final FixtureDef FIXTURE_DEF = new FixtureDef();
    public static final short BIT_PLAYER = 1 << 0;
    public static final short BIT_GROUND = 1 << 1;

	private EnumMap<ScreenType, Screen> screenCache;
	private FitViewport screenViewport;

	private World world;

	private float accumulator;

	private AssetManager assetManager;
	private OrthographicCamera orthographicCamera;

	private SpriteBatch spriteBatch;

	private Skin skin;
	private Stage stage;
	private I18NBundle i18nBundle;

	private InputManager inputManager;
	private AudioManager audioManager;
	private MapManager mapManager;

	private ESCEngine escEngine;

	private GameRederer gameRederer;
	@Override
	public void create() {
		//set Debug Mode
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		spriteBatch = new SpriteBatch();
		accumulator =0;
		//Box2d stuff
		Box2D.init();
		world= new World(new Vector2(0,0), true);
		//Initialize AssetManager
		assetManager = new AssetManager();
		assetManager.setLoader(TiledMap.class, new TmxMapLoader(assetManager.getFileHandleResolver()));
		initializeSkin();
		stage = new Stage(new FitViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()),spriteBatch);
		//audio
		audioManager = new AudioManager(this);
		//Input creation
		inputManager = new InputManager();
		Gdx.input.setInputProcessor(new InputMultiplexer(inputManager,stage));
		//ECS
		escEngine = new ESCEngine(this);
		//map manager
		mapManager = new MapManager(this);
		//Set first Screen
		orthographicCamera = new OrthographicCamera();
		screenViewport = new FitViewport(17, 16, orthographicCamera);
		screenCache = new EnumMap<>(ScreenType.class);
		setScreen(ScreenType.MENU);
		//Game Render
		gameRederer = new GameRederer(this);

	}
	@Override
	public void render() {
		super.render();
		final float deltaTime = Math.min(0.25f, Gdx.graphics.getDeltaTime());
		escEngine.update(deltaTime);
		accumulator += deltaTime;
		while (accumulator>=FIXED_TIME_STEP) {
			world.step(FIXED_TIME_STEP, 6, 2);
			accumulator -= FIXED_TIME_STEP;
		}
		gameRederer.render(accumulator/FIXED_TIME_STEP);
		stage.getViewport().apply();
		stage.act(deltaTime);
		stage.draw();
	}
	public void setScreen(final ScreenType screenType) {
		final Screen screen = screenCache.get(screenType);
		if (screen == null) {
			try {
				Gdx.app.debug(TAG, "Creando una nueva escena llamada: " + screenType);
				final Screen newScreen = (Screen) ClassReflection
						.getConstructor(screenType.getScreenClass(), Bomberman.class).newInstance(this);
				screenCache.put(screenType, newScreen);
				setScreen(newScreen);
			} catch (ReflectionException e) {
				throw new GdxRuntimeException("La escena " + screenType + " no se ha creado por : ", e);
			}
		} else {
			Gdx.app.debug(TAG, "Cambiando a la scenea llamada: " + screenType);
			setScreen(screen);
		}
	}
	    public static void resetBodieAndFixture() {
        BODY_DEF.position.set(0, 0);
        BODY_DEF.gravityScale = 1;
        BODY_DEF.type = BodyType.StaticBody;
        BODY_DEF.fixedRotation = false;

        FIXTURE_DEF.density = 0;
        FIXTURE_DEF.isSensor = false;
        FIXTURE_DEF.restitution = 0;
        FIXTURE_DEF.friction = 0.2f;
        FIXTURE_DEF.filter.categoryBits = 0x0001;
        FIXTURE_DEF.filter.maskBits = -1;
        FIXTURE_DEF.shape = null;
    }
	private void initializeSkin() {
		//setup markut color
		Colors.put("orange", Color.ORANGE);
		Colors.put("black", Color.BLACK);
		Colors.put("white", Color.WHITE);
		//converter a = x/255;
		Colors.put("orangeGame",new Color(1f, 0.615f, 0.2f, 1));
		Colors.put("blueGame",new Color(0.196f, 0.274f, 0.843f, 1));
		//generate ttf bitmaps
		ObjectMap<String,Object> resources = new ObjectMap<>();
		FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("interface/font/font.ttf"));
		FreeTypeFontParameter fontParameter = new FreeTypeFontParameter();
		fontParameter.borderWidth =2.5f;
		fontParameter.borderColor = Color.BLACK;
		fontParameter.minFilter = Texture.TextureFilter.Linear;
		fontParameter.magFilter = Texture.TextureFilter.Linear;
		final int[] sizesToCreate = {12,20,26,32};
		for (int size : sizesToCreate) {
			fontParameter.size = size;
			BitmapFont bitmapFont =  fontGenerator.generateFont(fontParameter);
			bitmapFont.getData().markupEnabled = true;
			resources.put("font_"+size, bitmapFont);
		}
		fontGenerator.dispose();
		//load skin
		SkinLoader.SkinParameter skinParameter = new SkinParameter("interface/hud/hud.atlas",resources);
		assetManager.load("interface/hud/hud.json",Skin.class,skinParameter);
		assetManager.load("properties/Interface",I18NBundle.class);
		assetManager.finishLoading();
		skin = assetManager.get("interface/hud/hud.json",Skin.class);
		i18nBundle = assetManager.get("properties/Interface",I18NBundle.class);
	}
	@Override
	public void dispose() {
		gameRederer.dispose();
		spriteBatch.dispose();
		super.dispose();
		world.dispose();
		assetManager.dispose();
		stage.dispose();
	}
	public MapManager getMapManager() {
		return mapManager;
	}
	public ESCEngine getEscEngine() {
		return escEngine;
	}
	public AudioManager getAudioManager() {
		return audioManager;
	}
	public InputManager getInputManager() {
		return inputManager;
	}
	public I18NBundle getI18nBundle() {
		return i18nBundle;
	}
	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}
	public OrthographicCamera getOrthographicCamera() {
		return orthographicCamera;
	}
	public AssetManager getAssetManager() {
		return assetManager;
	}
	public World getWorld() {
		return world;
	}
	public FitViewport getScreenViewport() {
		return screenViewport;
	}
	public Skin getSkin() {
		return skin;
	}
	public Stage getStage() {
		return stage;
	}

}
