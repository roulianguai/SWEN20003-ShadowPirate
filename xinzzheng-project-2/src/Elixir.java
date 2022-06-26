import bagel.Image;
import bagel.util.Point;

/**
 * Class for item Elixir
 */
public final class Elixir extends Item{
    private static final Image IMAGE = new Image("res/items/elixir.png");
    private static final Image IMAGE_INV = new Image("res/items/elixirIcon.png");
    private static final int HEALTH_INCREMENT = 35;
    private static final int WIDTH = 32;
    private static final int HEIGHT = 40;

    /**
     * Constructor extended from Bagel.Rectangle
     * @param topLeft top left coordinate
     */
    public Elixir(Point topLeft) {
        super(topLeft, WIDTH, HEIGHT);
    }

    /**
     * Method that increase sailor's health and heal sailor
     * @param s a Sailor instance
     */
    @Override
    public void interact(Sailor s) {
        s.setMaxHealth(s.getMaxHealth() + HEALTH_INCREMENT);
        s.setHealth(s.getMaxHealth());
        printLog(s);
        setPickUp(true);
    }

    /**
     * Method that render the inventory icon for elixir
     * @param x x coordinate to render
     * @param y y coordinate to render
     */
    @Override
    public void renderInventory(double x, double y) {
        IMAGE_INV.drawFromTopLeft(x, y);
    }

    /**
     * Method that prints log for the interaction
     * @param s Sailor instance
     */
    @Override
    public void printLog(Sailor s) {
        System.out.println("Sailor finds Elixir. Sailor's current health: " + s.getHealth() + "/" + s.getMaxHealth());
    }

    /**
     * Method that draw the elixir
     */
    @Override
    public void update() {
        IMAGE.drawFromTopLeft(left(), top());
    }
}
