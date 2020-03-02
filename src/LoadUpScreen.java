//LoadUpScreen GUI method creates a window where a user can load or create a character. The user can change the
//characters gold as well, and access the inventory screen method

import org.WyvernQuest.*;
import org.WyvernQuest.Libraries.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoadUpScreen {
    private JPanel LoadPanel;
    private JButton createCharacterButton;
    private JButton addSubtractGoldButton;
    private JButton inventoryButton;
    private JButton loadCharacterButton;
    private JLabel goldValueLabel;
    private JLabel playerNameLabel;
    private static String dbURL = "jdbc:derby:C:\\Users\\Drago\\OneDrive\\Documents\\Masters Degree\\CSIS 505\\Project\\Final\\WQPlayerInventory\\lib\\WQDatabase";
    public player currentPlayer;
    public characterLibrary characters = new characterLibrary(dbURL);
    public itemLibrary items = new itemLibrary(dbURL);

    public LoadUpScreen() {
        createCharacterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                characters.clearQueries(); //These clear query statements ensure stringbuilders in library classes don't break the SQL queries
                String characterNameInput = JOptionPane.showInputDialog("Please enter a player name below:");
                if (characterNameInput != null) {
                    currentPlayer = new player(characterNameInput);
                    characters.addPlayer(currentPlayer);
                    goldValueLabel.setText(String.valueOf(currentPlayer.getGold()));
                    playerNameLabel.setText(currentPlayer.getName());
                }
            }
        });
        loadCharacterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                characters.clearQueries();
                String characterNameInput = JOptionPane.showInputDialog("Please enter a player name below:");
                if (characterNameInput != null) {
                    currentPlayer = characters.findPlayer(characterNameInput);
                    try {
                        goldValueLabel.setText(String.valueOf(currentPlayer.getGold()));
                        playerNameLabel.setText(currentPlayer.getName());
                    } catch (NullPointerException p) {}
                }
            }
        });
        addSubtractGoldButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                characters.clearQueries();
                int goldValueInput;
                try {
                    goldValueInput = Integer.parseInt(JOptionPane.showInputDialog(
                            "Please enter a value below:\n(To subtract, please enter a negative value)"));
                }
                catch (NumberFormatException f) {
                    goldValueInput = 0;
                    JOptionPane.showMessageDialog(null, "Input value must be a number.");
                }
                currentPlayer.addGold(goldValueInput);
                characters.updatePlayer(currentPlayer);
                goldValueLabel.setText(String.valueOf(currentPlayer.getGold()));
            }
        });
        //call inventory screen
        inventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InventoryScreen(currentPlayer);
                InventoryScreen.main(null, currentPlayer);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("LoadUpScreen");
        frame.setContentPane(new LoadUpScreen().LoadPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
