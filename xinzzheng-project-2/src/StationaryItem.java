import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * Abstract class for non-moving stationary item
 */
public abstract class StationaryItem extends Rectangle {
    private boolean exists = true;

    /**
     * Constructor extended from Bagel.Rectangle
     * @param topLeft top left coordinate
     * @param width width for rectangle
     * @param height height for rectangle
     */
    public StationaryItem(Point topLeft, double width, double height) {
        super(topLeft, width, height);
    }

    /**
     * The main method monitor the drawing, whether exist and attack if applicable
     */
    public abstract void update();

    /**
     * Getter for whether stationary item is valid to exist
     * @return boolean for whether an item is valid to exist
     */
    public boolean isExists() {
        return exists;
    }

    protected void setExists(boolean exists) {
        this.exists = exists;
    }
}
