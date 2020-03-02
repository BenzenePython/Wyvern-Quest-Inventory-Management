//inventory class defines how a player's inventory should function and be used

package org.WyvernQuest;

import java.util.ArrayList;

public class inventory {
    private ArrayList<String> itemIDs = new ArrayList<>();

    public inventory(){}
    //creates inventory object from comma delimited string
    public inventory(String idCSV){
        StringBuilder IDs = new StringBuilder(idCSV);
        int i = 0;
        while (IDs.length() > 0) {
            if (IDs.indexOf(",") == -1){
                itemIDs.add(i, IDs.toString());
                IDs.delete(0,(IDs.length() - 1));
                IDs.setLength(0);
            }
            else {
                itemIDs.add(i, IDs.substring(0, IDs.indexOf(",")));
                IDs.delete(0, (IDs.indexOf(",") + 1));
            }
            i++;
        }
    }

    public static void main(String[] args) {
        inventory in = new inventory("C0G0H0,S0R0I0,L0H0E0,A0S0R0");
    }

    public void addItem(String ID) { itemIDs.add(ID); }

    public void removeItem(String ID) { itemIDs.remove(ID); }

    public void clearInventory(){ itemIDs.clear(); }

    public int length(){ return itemIDs.size(); }

    public String[] getInventory(){
        String[] items = new String[itemIDs.size()];
        int i = 0;
        for (String ID : itemIDs) {
            items[i] = ID;
            i++;
        }
        return items;
    }
    //returns single string of ID's in a comma delimited fashion
    public String getInventoryAsString(){
        StringBuilder items = new StringBuilder();
        for (String ID : itemIDs){
            items.append(ID);
            if (itemIDs.indexOf(ID) != (itemIDs.size() - 1))
                items.append(",");
        }
        return items.toString();
    }
}
