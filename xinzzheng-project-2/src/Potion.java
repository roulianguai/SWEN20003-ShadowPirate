import bagel.Image;
import bagel.util.Point;

/**
 * class for item potion
 */
public final class Potion extends Item{
    private static final Image IMAGE = new Image("res/items/potion.png");
    private static final Image IMAGE_INV = new Image("res/items/potionIcon.png");
    private static final int HEALTH_INCREMENT = 25;
    private static final int WIDTH = 32;
    private static final int HEIGHT = 40;

    /**
     * Constructor extended from Bagel.Rectangle
     * @param topLeft top left coordinate
     */
    public Potion(Point topLeft) {
        super(topLeft, WIDTH, HEIGHT);
    }

    /**
     * Method that heals the sailor
     * @param s Sailor instance
     */
    @Override
    public void interact(Sailor s) {
        if(s.getHealth() + HEALTH_INCREMENT <= s.getMaxHealth())
            s.setHealth(s.getHealth() + HEALTH_INCREMENT);
        else
            s.setHealth(s.getMaxHealth());
        printLog(s);
        setPickUp(true);
    }

    /**
     * Method that render inventory icon for potion
     * @param x x coordinate to render
     * @param y y coordinate to render
     */
    @Override
    public void renderInventory(double x, double y) {
        IMAGE_INV.drawFromTopLeft(x, y);
    }

    /**
     * Method that prints log for potion
     * @param s Sailor instance
     */
    @Override
    public void printLog(Sailor s) {
        System.out.println("Sailor finds Potion. Sailor's current health: " + s.getHealth() + "/" + s.getMaxHealth());
    }

    /**
     * Method that draws image for potion
     */
    @Override
    public void update() {
        IMAGE.drawFromTopLeft(left(), top());
    }
}
