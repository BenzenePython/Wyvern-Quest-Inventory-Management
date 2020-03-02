//Library class for accessing the players table in the WQDatabase
package org.WyvernQuest.Libraries;

import org.WyvernQuest.inventory;
import org.WyvernQuest.player;
import javax.swing.*;
import java.awt.*;
import java.net.CookieHandler;
import java.sql.*;

public class characterLibrary {
    private String databaseURL;
    private StringBuilder playerAddQuery = new StringBuilder("INSERT into PLAYERS (playerID, playerName, gold, inventory) VALUES ");
    private StringBuilder playerQuery = new StringBuilder("SELECT playerID, playerName, gold, inventory FROM PLAYERS WHERE playerID = ");
    private StringBuilder playerIDQuery = new StringBuilder("SELECT playerID FROM PLAYERS WHERE playerName = ");
    private StringBuilder playerUpdateQuery = new StringBuilder("UPDATE PLAYERS SET ");

    public characterLibrary(){ this.databaseURL = "jdbc:derby:WQDatabase"; }
    public characterLibrary(String databaseURL){
        this.databaseURL = databaseURL;
    }
    //finds player first by name to access id, to more accurately pull information for the player class return
    public player findPlayer(String Name){
        player Player;
        playerIDQuery.append("'");
        playerIDQuery.append(Name);
        playerIDQuery.append("'");
        try(Connection connection = DriverManager.getConnection(
                databaseURL, "Tahrin", "Triad");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(playerIDQuery.toString())){
            resultSet.next();
            playerQuery.append("'");
            playerQuery.append(resultSet.getString("playerID"));
            playerQuery.append("'");
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No such player was found");
            return null;
        }
        try(Connection connection = DriverManager.getConnection(
                databaseURL, "Tahrin", "Triad");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(playerQuery.toString())) {
            resultSet.next();
            String PID = resultSet.getString("PlayerID");
            String pname = resultSet.getString("playerName");
            int gold = resultSet.getInt("gold");
            inventory playerinv = new inventory(resultSet.getString("inventory"));
            Player = new player(pname, gold, playerinv, PID);
            return Player;
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No such player was found");
            return null;
        }
    }
    //add player object to players database
    public void addPlayer(player Player){
        String playerID = Player.getID();
        String playerName = Player.getName();
        int gold = Player.getGold();
        String inventory = Player.getInventoryCSV();
        playerAddQuery.append("('");
        playerAddQuery.append(playerID);
        playerAddQuery.append("', '");
        playerAddQuery.append(playerName);
        playerAddQuery.append("', ");
        playerAddQuery.append(gold);
        playerAddQuery.append(", '");
        playerAddQuery.append(inventory);
        playerAddQuery.append("')");
        try {
            Connection connection = DriverManager.getConnection(
                    databaseURL, "Tahrin", "Triad");
            Statement statement = connection.createStatement();
            statement.execute(playerAddQuery.toString());
        }
        catch (SQLException e) { e.printStackTrace(); }
    }
    //updates a player's gold and inventory in the database
    public void updatePlayer(player Player){
        int gold = Player.getGold();
        String inventory = Player.getInventoryCSV();
        String PID = Player.getID();
        playerUpdateQuery.append("gold = ");
        playerUpdateQuery.append(gold);
        playerUpdateQuery.append(", ");
        playerUpdateQuery.append("inventory = '");
        playerUpdateQuery.append(inventory);
        playerUpdateQuery.append("' ");
        playerUpdateQuery.append("WHERE playerID = '");
        playerUpdateQuery.append(PID);
        playerUpdateQuery.append("'");
        try{
            Connection connection = DriverManager.getConnection(
                    databaseURL, "Tahrin", "Triad");
            Statement statement = connection.createStatement();
            statement.execute(playerUpdateQuery.toString());
        }
        catch (SQLException e) { e.printStackTrace(); }
    }
    //clears stringBuilder queries for reuse
    public void clearQueries(){
        this.playerAddQuery = new StringBuilder("INSERT into PLAYERS (playerID, playerName, gold, inventory) VALUES ");
        this.playerQuery = new StringBuilder("SELECT playerID, playerName, gold, inventory FROM PLAYERS WHERE playerID = ");
        this.playerIDQuery = new StringBuilder("SELECT playerID FROM PLAYERS WHERE playerName = ");
        this.playerUpdateQuery = new StringBuilder("UPDATE PLAYERS SET ");
    }
}
