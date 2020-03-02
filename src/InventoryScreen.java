//Inventory screen creates an inventory list for the loaded character. Selecting an item will display information and
//allow the user to modify the quantity or completely remove the item
//Also allows the user to create a new item

import org.WyvernQuest.Libraries.characterLibrary;
import org.WyvernQuest.Libraries.itemLibrary;
import org.WyvernQuest.player;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;

public class InventoryScreen {
    private static final JFrame frame = new JFrame("InventoryScreen");
    private JList<String> inventoryList;
    private JPanel inventoryPanel;
    private JTextArea itemDescriptionDisplay;
    private JButton removeButton;
    private JButton modifyButton;
    private JButton newItemButton;
    private JLabel itemQuantityDisplay;
    private JLabel itemNameDisplay;
    private static String dbURL = "jdbc:derby:C:\\Users\\Drago\\OneDrive\\Documents\\Masters Degree\\CSIS 505\\Project\\Final\\WQPlayerInventory\\lib\\WQDatabase";
    public characterLibrary characters = new characterLibrary(dbURL);
    public itemLibrary items = new itemLibrary(dbURL);

    public InventoryScreen(player currentPlayer) {
        String[] itemIDs = currentPlayer.getInventory();
        DefaultListModel<String> model = new DefaultListModel<>();
        for (String ID : itemIDs){
            items.clearQueries();
            Object[] item = items.findItemByID(ID);
            model.addElement(item[0].toString());
        }
        inventoryList.setModel(model);
        itemDescriptionDisplay.setLineWrap(true);
        itemDescriptionDisplay.setWrapStyleWord(true);

        inventoryList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                items.clearQueries();
                String itemSelection = inventoryList.getSelectedValue();
                Object[] item = items.  findItemByName(itemSelection);
                itemNameDisplay.setText(item[0].toString());
                itemQuantityDisplay.setText(item[1].toString());
                itemDescriptionDisplay.setText(item[2].toString());
            }
        });
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                items.clearQueries();
                String itemSelection = inventoryList.getSelectedValue();
                int itemQuantityInput;
                try {
                    itemQuantityInput = Integer.parseInt(JOptionPane.showInputDialog("Please enter the new quantity:"));
                    items.updateItem(itemSelection, itemQuantityInput);
                    itemQuantityDisplay.setText(String.valueOf(itemQuantityInput));
                }
                catch (NumberFormatException ex) { JOptionPane.showMessageDialog(null, "Input value must be a number.");}
            }
        });
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                items.clearQueries();
                String itemName = itemNameDisplay.getText();
                String confirmMessage = String.format("Are you sure you wish to remove %s?", itemName);
                int deleteSelection = JOptionPane.showConfirmDialog(null, confirmMessage, "Item Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                switch (deleteSelection){
                    case 0:
                        inventoryList.updateUI();
                        currentPlayer.take(items.getID(itemName));
                        characters.updatePlayer(currentPlayer);
                        items.deleteItem(items.getID(itemName));
                        try {
                            model.removeElement(itemName);
                            inventoryList.setModel(model);
                        }
                        catch (NullPointerException p) {}
                        itemNameDisplay.setText(null);
                        itemQuantityDisplay.setText(null);
                        itemDescriptionDisplay.setText(null);
                    case 1:
                        break;
                }
            }
        });
        //open new item screen and close inventory screen
        newItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new itemCreateScreen(currentPlayer);
                itemCreateScreen.main(null, currentPlayer);
                frame.dispose();
            }
        });
    }

    public static void main(String[] args, player currentPlayer) {
        frame.setContentPane(new InventoryScreen(currentPlayer).inventoryPanel);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); //ensure program remains running when inventory is closed
        frame.pack();
        frame.setVisible(true);
    }
}
