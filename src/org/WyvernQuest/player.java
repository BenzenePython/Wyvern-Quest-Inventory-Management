//defines what a player is and generates a unique ID upon initializing a new player
package org.WyvernQuest;

import java.io.Serializable;
import java.security.SecureRandom;

public class player implements Serializable {
    private int gold = 0;
    private inventory playerInventory = new inventory();
    private String playerName;
    private String playerID;
    private SecureRandom rand = new SecureRandom();
    //for creating a character to save in the database
    public player(String Name){
        playerName = Name;
        StringBuffer token = new StringBuffer();
            token.append(Name.charAt(0));
            token.append(rand.nextInt(10));
            token.append(Name.charAt(Name.length() - 1));
            token.append(rand.nextInt(10));
            token.append(Name.charAt(1));
            token.append(rand.nextInt(10));
        playerID = token.toString();
    }
    //for loading a character from database
    public player(String playerName, int Gold, inventory playerInventory, String playerID){
        this.playerName = playerName;
        this.gold = Gold;
        this.playerInventory = playerInventory;
        this.playerID = playerID;
    }

    public String getName(){ return playerName; }
    public String getID(){ return playerID; }
    public int getGold(){ return gold; }
    public String[] getInventory(){ return playerInventory.getInventory(); }
    public String getInventoryCSV(){ return  playerInventory.getInventoryAsString(); }

    public void setGold(int gold) { this.gold = gold; }
    public void addGold(int gold) { this.gold += gold; }
    public void give(item item){
        playerInventory.addItem(item.getItemID());
    }
    public void take(String itemID){
        playerInventory.removeItem(itemID);
    }

}
