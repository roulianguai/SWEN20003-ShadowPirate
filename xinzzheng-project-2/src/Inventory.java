import java.util.ArrayList;

/**
 * Class for inventory in the game
 */
public final class Inventory {
    private static final int POS_X = 12;
    private static final int POS_Y = 40;
    private static final int Y_OFFSET = 40;
    private final ArrayList<Item> inventoryItems = new ArrayList<>();

    /**
     * display any picked up item in the game
     */
    public void update() {
        for(int i = 0; i < inventoryItems.size(); i++) {
            inventoryItems.get(i).renderInventory(POS_X, POS_Y + i * Y_OFFSET);
        }
    }

    /**
     * Method that add an item to inventory list
     * @param item item that needs to be added
     */
    public void addItem(Item item) {
        inventoryItems.add(item);
    }
}
