//class item: defines what an item is composed of and creates each item with a unique itemID
package org.WyvernQuest;

import java.security.SecureRandom;

public class item {
    private String name;
    private int quantity;
    private String description;
    private String itemID;
    private SecureRandom rand = new SecureRandom();

    public item(String Name, int Quantity, String Description){
        name = Name;
        quantity = Quantity;
        description = Description;
        //create unique token
        StringBuffer token = new StringBuffer();
            token.append(Name.charAt(0));
            token.append(rand.nextInt(10));
            token.append(Name.charAt(Name.length() - 1));
            token.append(rand.nextInt(10));
            token.append(Name.charAt(1));
            token.append(rand.nextInt(10));
        itemID = token.toString(); //makes item id six-character random string of format: a0b0z0
    }

    public String getItemID() { return itemID; }
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public String getDescription() { return description; }

    //set method includes return type to increase ease of updating database
    public int setQuantity(int Quantity) {
        quantity = Quantity;
        return quantity;
    }
}
