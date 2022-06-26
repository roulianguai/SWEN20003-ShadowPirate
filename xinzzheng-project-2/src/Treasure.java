import bagel.Image;
import bagel.util.Point;

/**
 * Class for treasure in Level 2
 */
public final class Treasure extends StationaryItem{
    private static final int WIDTH = 40;
    private static final int HEIGHT = 48;
    private static final Image IMAGE = new Image("res/treasure.png");

    /**
     * Constructor extended from Bagel.Rectangle
     * @param topLeft top left coordinate
     */
    public Treasure(Point topLeft) {
        super(topLeft, WIDTH, HEIGHT);
    }

    /**
     * Method that draw the treasure
     */
    @Override
    public void update() {
        IMAGE.drawFromTopLeft(left(), top());
    }

    /**
     * Method that checks whether sailor gets the treasure
     * @param s Sailor instance
     * @return boolean indicates whether sailor gets the treasure
     */
    public boolean isCollided(Sailor s) {
        return s.intersects(this);
    }
}
