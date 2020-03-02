//for creating sample database entries for testing purposes
//loadable characters are: Eralor and Grintor Prilmon
package org.WyvernQuest.Libraries;

import java.sql.*;

public class createDatabase {
    private String dataURL = "jdbc:derby:C:\\Users\\Drago\\OneDrive\\Documents\\Masters Degree\\CSIS 505\\Project" +
            "\\Final\\WQPlayerInventory\\lib\\WQDatabase;create=true";

    public createDatabase(){
        try {
            Connection connection = DriverManager.getConnection(dataURL, "Tahrin", "Triad");

            DropTables(connection);

            buildTables(connection);

            connection.close();
        }
        catch (SQLException e) { e.printStackTrace(); }
    };
    public createDatabase(String databaseURL){
        this.dataURL = databaseURL;
        try {
            Connection connection = DriverManager.getConnection(dataURL, "Tahrin", "Triad");

            DropTables(connection);

            buildTables(connection);

            connection.close();
        }
        catch (SQLException e) { e.printStackTrace(); }
    }
    public static void main(String[] args) {
        createDatabase WQ = new createDatabase();
    }

    private void DropTables(Connection connection){
        try {
            Statement statement = connection.createStatement();
            try {
                statement.execute("DROP TABLE ITEMS");
            }
            catch (SQLException e) {}
            try {
                statement.execute("DROP TABLE PLAYERS");
            }
            catch (SQLException e){}
        }
        catch (SQLException e) { e.printStackTrace(); }
    }

    private void buildTables(Connection connection){
        try {
            Statement statement = connection.createStatement();

            statement.execute("CREATE TABLE ITEMS (itemID CHAR(6), itemName VARCHAR(50), quantity INTEGER, description VARCHAR(250))");
            //Eralor items
            statement.execute("INSERT INTO ITEMS (itemID, itemName, quantity, description) VALUES " +
                    "('C0G0H0', 'Cherry Recurve Bow of Piercing', 1, " +
                    "'This bow can fire shots at a much longer distance than normal, and flip a coin to halve target’s AC')");
            statement.execute("INSERT INTO ITEMS (itemID, itemName, quantity, description) VALUES " +
                    "('S0R0I0', 'Silver Dagger', 2, 'Standard daggers made of a reinforced silver alloy')");
            statement.execute("INSERT INTO ITEMS (itemID, itemName, quantity, description) VALUES " +
                    "('L0H0E0', 'Leather Armor of Breath', 1, " +
                    "'User has prolonged breath while wearing this armor, all accuracy checks receive +2. Includes gloves and draw-guard')");
            statement.execute("INSERT INTO ITEMS (itemID, itemName, quantity, description) VALUES " +
                    "('A0S0R0', 'Arrows', 32, 'Standard quiver full of arrows')");
            //Grintor Prilmon items
            statement.execute("INSERT INTO ITEMS (itemID, itemName, quantity, description) VALUES " +
                    "('D0R0W0', 'Dwarven Brass Plate Armor', 1, 'Dwarven armor hard as mitril, but with a brassy appearance.')");
            statement.execute("INSERT INTO ITEMS (itemID, itemName, quantity, description) VALUES " +
                    "('D0D0W0', 'Dwarven Brass Tower Shield', 1, 'Tower shield with a reflective surface')");
            statement.execute("INSERT INTO ITEMS (itemID, itemName, quantity, description) VALUES " +
                    "('S0S0T0', 'Standard Health Potions', 3, 'Restores 40% of max HP')");
            statement.execute("INSERT INTO ITEMS (itemID, itemName, quantity, description) VALUES " +
                    "('S0E0T0', 'Steve', 1, 'Grintors pet rock')");
        }
        catch (SQLException e) { e.printStackTrace(); }

        try {
            Statement statement = connection.createStatement();

            statement.execute("CREATE TABLE PLAYERS (playerID CHAR(6), playerName VARCHAR(50), gold INTEGER, inventory VARCHAR(5000))");
            //Eralor
            statement.execute("INSERT INTO PLAYERS (playerID, playerName, gold, inventory) VALUES " +
                    "('E0R0R0', 'Eralor', 246, 'C0G0H0,S0R0I0,L0H0E0,A0S0R0')");
            //Grintor Prilmon
            statement.execute("INSERT INTO PLAYERS (playerID, playerName, gold, inventory) VALUES " +
                    "('G0N0R0', 'Grintor Prilmon', 40, 'D0R0W0,D0D0W0,S0S0T0,S0E0T0')");
        }
        catch (SQLException e) { e.printStackTrace(); }
    }
}
