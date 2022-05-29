package com.bormberman.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.badlogic.gdx.utils.Array;

public class DatabaseManager {
    private final String nameDatabase = "bomberman";
    private final String username = "root";
    private final String password = "";
    private Connection connection;
    public DatabaseManager() throws SQLException{
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+nameDatabase, username, password);
        System.out.println("Conected");
    }
    public void newPlayer(String userName,String puntuacion) throws SQLException{
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO users(name,puntuacion) VALUES('"+userName+"','"+puntuacion+"')");
        statement.close();
    }
    public Array<String[]> allPlayers() throws SQLException {
        final Array<String[]> datos = new Array<>();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM users");
        while (rs.next()) {
            String[] user = {rs.getString("name"),rs.getString("puntuacion")};
            datos.add(user);
        }
        rs.close();
        statement.close();
        return datos;
    }
}
