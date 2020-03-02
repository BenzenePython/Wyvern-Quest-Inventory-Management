//Library class for accessing the items table in the WQDatabase
package org.WyvernQuest.Libraries;

import org.WyvernQuest.item;
import java.sql.*;

public class itemLibrary {
    private String databaseURL;
    private StringBuilder itemAddQuery = new StringBuilder("INSERT INTO items (itemID, itemName, quantity, description) VALUES ");
    private StringBuilder itemQuery = new StringBuilder("SELECT itemName, quantity, description FROM items WHERE ");
    private StringBuilder itemUpdateQuery = new StringBuilder("UPDATE items SET quantity = ");

    public itemLibrary(){ this.databaseURL = "jdbc:derby:WQDatabase"; }
    public itemLibrary(String databaseURL){ this.databaseURL = databaseURL; }

    //get methods return object array of name (String), quantity (Integer), and description (String)
    public Object[] findItemByName(String Name){
        Object[] item = new Object[3];
        itemQuery.append("itemName = '");
        itemQuery.append(Name);
        itemQuery.append("'");
        try(Connection connection = DriverManager.getConnection(
                    databaseURL, "Tahrin", "Triad" );
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(itemQuery.toString())) {
            resultset.next();
            item[0] = resultset.getString("itemName");
            item[1] = resultset.getInt("quantity");
            item[2] = resultset.getString("description");
        }
        catch (SQLException e) {}
        return item;
    }
    public Object[] findItemByID(String itemID){
        Object[] item = new Object[3];
        itemQuery.append("itemID = '");
        itemQuery.append(itemID);
        itemQuery.append("'");
        try(Connection connection = DriverManager.getConnection(
                databaseURL, "Tahrin", "Triad" );
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(itemQuery.toString())) {
            resultset.next();
            item[0] = resultset.getString("itemName");
            item[1] = resultset.getInt("quantity");
            item[2] = resultset.getString("description");
        }
        catch (SQLException e) { e.printStackTrace(); }
        return item;
    }
    //inserts new item object into items table
    public void addItem(item item){
        String itemID = item.getItemID();
        String name = item.getName();
        int quantity = item.getQuantity();
        String description = item.getDescription();
        itemAddQuery.append("('");
        itemAddQuery.append(itemID);
        itemAddQuery.append("', '");
        itemAddQuery.append(name);
        itemAddQuery.append("', ");
        itemAddQuery.append(quantity); //currently no single quotes around quantity (not necessary b/c int?)
        itemAddQuery.append(", '");
        itemAddQuery.append(description);
        itemAddQuery.append("')");
        try {
            Connection connection = DriverManager.getConnection(
                databaseURL, "Tahrin", "Triad");
            Statement statement = connection.createStatement();
            statement.execute(itemAddQuery.toString());
        }
        catch (SQLException e) { e.printStackTrace(); }
    }
    //updates existing item's quantity value
    public void updateItem(String itemName, int quantity){
        itemUpdateQuery.append(quantity);
        itemUpdateQuery.append(" WHERE itemID = '");
        itemUpdateQuery.append(getID(itemName));
        itemUpdateQuery.append("'");
        try {
            Connection connection = DriverManager.getConnection(
                databaseURL, "Tahrin", "Triad");
            Statement statement = connection.createStatement();
            statement.execute(itemUpdateQuery.toString());
        }
        catch (SQLException e) { e.printStackTrace(); }
    }
    //allows updateItem to function using a name only
    public String getID(String itemName){
        try (Connection connection = DriverManager.getConnection(
                databaseURL, "Tahrin", "Triad");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT itemID FROM items WHERE itemName = '"
                    +itemName +"'");){
            resultSet.next();
            return resultSet.getString("itemID");
        }
        catch (SQLException e) { e.printStackTrace(); return null; }
    }
    //removes item from items table
    public void deleteItem(String itemID) {
        try {
            Connection connection = DriverManager.getConnection(databaseURL, "Tahrin", "Triad");
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM items WHERE itemID = '" + itemID + "'");
        }
        catch (SQLException e) { e.printStackTrace(); }
    }
    //clear stringBuilder queries to be reused
    public void clearQueries(){
        this.itemAddQuery = new StringBuilder("INSERT INTO items (itemID, itemName, quantity, description) VALUES ");
        this.itemQuery = new StringBuilder("SELECT itemName, quantity, description FROM items WHERE ");
        this.itemUpdateQuery = new StringBuilder("UPDATE items SET quantity = ");
    }
}
