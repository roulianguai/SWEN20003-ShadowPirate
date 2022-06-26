import bagel.util.Point;

/**
 * Item class. Refer to all collectable item in the game
 */
public abstract class Item extends StationaryItem {
    private boolean pickUp = false;
    /**
     * Constructor extended from Bagel.Rectangle
     * @param topLeft Point topLeft coordinate
     * @param width double width of rectangle
     * @param height double height of rectangle
     */
    public Item(Point topLeft, double width, double height) {
        super(topLeft, width, height);
    }
    /**
     *
     * @param s Sailor a Sailor instance
     * @return boolean indicating whether sailor touched the item
     */
    public boolean isInCollision(Sailor s) {
        return s.intersects(this);
    }

    protected abstract void interact(Sailor s);

    protected abstract void printLog(Sailor s);

    protected abstract void renderInventory(double x, double y);

    /**
     * Getter for pickup
     * @return boolean return private attribute pickUp
     */
    public boolean isPickUp() {
        return pickUp;
    }

    /**
     * Setter for pickup
     */
    public void setPickUp(boolean pickUp) {
        this.pickUp = pickUp;
    }
}
