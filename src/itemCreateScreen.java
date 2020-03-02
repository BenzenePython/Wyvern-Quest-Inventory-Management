//ItemCreateScreen makes a GUI to input valid information for a new item.

import org.WyvernQuest.Libraries.characterLibrary;
import org.WyvernQuest.Libraries.itemLibrary;
import org.WyvernQuest.item;
import org.WyvernQuest.player;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class itemCreateScreen {
    private static final JFrame frame = new JFrame("itemCreateScreen");
    private JTextField itemNameEntry;
    private JSpinner itemQuantityEntry;
    private JTextArea itemDescriptionEntry;
    private JButton submitButton;
    private JButton clearButton;
    private JPanel itemCreatePanel;
    private static String dbURL = "jdbc:derby:C:\\Users\\Drago\\OneDrive\\Documents\\Masters Degree\\CSIS 505\\Project\\Final\\WQPlayerInventory\\lib\\WQDatabase";
    public characterLibrary characters = new characterLibrary(dbURL);
    public itemLibrary items = new itemLibrary(dbURL);

    public itemCreateScreen(player currentPlayer) {
        SpinnerModel model = new SpinnerNumberModel(0,0,1000,1);
        itemQuantityEntry.setModel(model);
        itemDescriptionEntry.setLineWrap(true);
        itemDescriptionEntry.setWrapStyleWord(true);
        //upon closing this window, it will always relaunch the inventory screen
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                InventoryScreen newScreen = new InventoryScreen(currentPlayer);
                newScreen.main(null, currentPlayer);
                frame.dispose();
            }
        });
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String itemName = itemNameEntry.getText();
                System.out.println(itemName.length());
                int itemQuantity = (int) itemQuantityEntry.getValue();
                String itemDescription = itemDescriptionEntry.getText();
                if (! itemName.equalsIgnoreCase("") || itemQuantity != 0 || ! itemDescription.equalsIgnoreCase("")) {
                    item newItem = new item(itemName, itemQuantity, itemDescription);
                    currentPlayer.give(newItem);
                    characters.updatePlayer(currentPlayer);
                    items.addItem(newItem);
                    InventoryScreen newScreen = new InventoryScreen(currentPlayer);
                    newScreen.main(null, currentPlayer);
                    frame.dispose();
                }
                else
                    JOptionPane.showMessageDialog(null,
                            "Please make sure all fields have input");
            }
        });
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                itemNameEntry.setText("");
                itemQuantityEntry.setValue(0);
                itemDescriptionEntry.setText("");
            }
        });
    }

    public static void main(String[] args, player currentPlayer) {
        frame.setContentPane(new itemCreateScreen(currentPlayer).itemCreatePanel);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE); //ensure close operation works correctly
        frame.pack();
        frame.setVisible(true);
    }
}
