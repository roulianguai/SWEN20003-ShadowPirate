import bagel.Image;
import bagel.util.Point;

/**
 * Class for item sword
 */
public final class Sword extends Item{
    private static final Image IMAGE = new Image("res/items/sword.png");
    private static final Image IMAGE_INV = new Image("res/items/swordIcon.png");
    private static final int WIDTH = 32;
    private static final int HEIGHT = 40;
    private static final int DAMAGE_INCREMENT = 15;

    /**
     * Constructor extended from Bagel.Rectangle
     * @param topLeft top left coordinate
     */
    public Sword(Point topLeft) {
        super(topLeft, WIDTH, HEIGHT);
    }

    /**
     * Method that increases sailor's damage
     * @param s Sailor instance
     */
    @Override
    public void interact(Sailor s) {
        s.setDamage(s.getDamage() + DAMAGE_INCREMENT);
        printLog(s);
        setPickUp(true);
    }

    /**
     * Method that renders inventory icon for sword
     * @param x x coordinate to render
     * @param y y coordinate to render
     */
    @Override
    public void renderInventory(double x, double y) {
        IMAGE_INV.drawFromTopLeft(x, y);
    }

    /**
     * Method that draws image for sword
     */
    @Override
    public void update() {
        IMAGE.drawFromTopLeft(left(), top());
    }

    /**
     * Method that prints log for sword
     * @param s Sailor instance
     */
    @Override
    public void printLog(Sailor s) {
        System.out.println("Sailor finds Sword. Sailor's damage points increased to " + s.getDamage());
    }

}
