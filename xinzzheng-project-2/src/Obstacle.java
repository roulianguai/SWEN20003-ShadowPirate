import bagel.util.Point;

/**
 * Abstract class extended from stationary item representing items that tend to block motion of characters
 */
public abstract class Obstacle extends StationaryItem{

    /**
     * Constructor extended from Bagel.Rectangle
     * @param topLeft top left coordinate
     * @param width rectangle width
     * @param height rectangle height
     */
    public Obstacle(Point topLeft, double width, double height) {
        super(topLeft, width, height);
    }

    /**
     * Method that check is a character collide with obstacle
     * @param c character instance
     * @return boolean value indicating whether character collide with an obstacle
     */
    public boolean checkCollision(Character c) {
        return c.intersects(this);
    }
}
