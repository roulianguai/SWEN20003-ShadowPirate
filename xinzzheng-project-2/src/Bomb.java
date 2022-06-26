import bagel.Image;
import bagel.util.Point;

/**
 * Class for bomb entity in level 1
 */
public final class Bomb extends Obstacle implements MeleeAttackable<Sailor>{
    private static final Image IMAGE = new Image("res/bomb.png");
    private static final Image EXPLODE = new Image("res/explosion.png");
    private static final int EXPLOSION_DURATION = 500;
    private static final int WIDTH = 32;
    private static final int HEIGHT = 40;
    private static final int DAMAGE = 10;
    private boolean exploding = false;
    private long explosionStartTime;

    /**
     * Constructor extended from Bagel.Rectangle
     * @param topLeft top left coordinate
     */
    public Bomb(Point topLeft) {
        super(topLeft, WIDTH, HEIGHT);
    }

    /**
     * Method that prints log when attacks
     * @param s Sailor instance
     */
    @Override
    public void printLog(Sailor s) {
        System.out.println("Bomb inflicts " + DAMAGE + " damage points on Sailor. " +
                "Sailor's current health: " + s.getHealth() + "/" + s.getMaxHealth());
    }

    /**
     * Method that check is sailor is in attack range
     * @param o Sailor instance
     * @return boolean value indicating if sailor is in attack range
     */
    @Override
    public boolean isInRange(Sailor o) {
        return o.intersects(this);
    }

    /**
     * Method that starts attack and set exploding time
     * @param s Sailor instance
     */
    @Override
    public void attackTo(Sailor s) {
        s.getHurt(DAMAGE);
        printLog(s);
        explosionStartTime = System.currentTimeMillis();
        exploding = true;
    }

    /**
     * The method monitors the drawing, whether exist and attack if applicable
     */
    @Override
    public void update() {
        if(exploding) {
            //if already exploded, display exploding image and maintain block property for 0.5s
            EXPLODE.drawFromTopLeft(super.left(), super.top());
            if(explosionStartTime + EXPLOSION_DURATION <= System.currentTimeMillis()) {
                setExists(false);
            }
        } else {
            IMAGE.drawFromTopLeft(super.left(), super.top());
        }
    }

    /**
     * Getter for exploding
     * @return boolean value for whether bomb is exploding
     */
    public boolean isExploding() {
        return exploding;
    }
}
