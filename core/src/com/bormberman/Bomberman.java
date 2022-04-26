package com.bormberman;

import java.util.EnumMap;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.bormberman.screens.ScreenType;

public class Bomberman extends Game {
	private static final String TAG = Bomberman.class.getSimpleName();
	private EnumMap<ScreenType, Screen> screenCache;
	private FitViewport screenViewport;

	private World world;
	private Box2DDebugRenderer box2dDebugRenderer;

	private static final float FIXED_TIME_STEP = 1/60f;
	private float accumulator;


	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		accumulator =0;
		Box2D.init();
		box2dDebugRenderer = new Box2DDebugRenderer();
		world= new World(new Vector2(0,-9.81f), true);

		screenViewport = new FitViewport(9, 16);
		screenCache = new EnumMap<>(ScreenType.class);
		setScreen(ScreenType.GAME);
	}
	public void setScreen(final ScreenType screenType) {
		final Screen screen = screenCache.get(screenType);
		if (screen == null) {
			try {
				Gdx.app.debug(TAG, "Creando una nueva scena llamada: " + screenType);
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
	@Override
	public void render() {
		super.render();
		accumulator += Math.min(0.25f, Gdx.graphics.getDeltaTime());
		while (accumulator>=FIXED_TIME_STEP) {
			world.step(FIXED_TIME_STEP, 6, 2);
			accumulator -= FIXED_TIME_STEP;
		}
		//final float alpha = accumulator/FIXED_TIME_STEP;
	}
	@Override
	public void dispose() {
		super.dispose();
		box2dDebugRenderer.dispose();
		world.dispose();
	}
	public Box2DDebugRenderer getBox2dDebugRenderer() {
		return box2dDebugRenderer;
	}
	public World getWorld() {
		return world;
	}
	public FitViewport getScreenViewport() {
		return screenViewport;
	}

}
