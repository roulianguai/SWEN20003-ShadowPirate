import bagel.util.Point;
import bagel.util.Rectangle;
import bagel.util.Vector2;

/**
 * Abstract class for general projectile properties in the game
 */
public abstract class Projectile extends Rectangle implements MeleeAttackable<Sailor>{
    protected static final int SIZE = 1;
    protected double angle;
    protected Vector2 direction;
    protected boolean flying = true;

    /**
     * Constructor extended from Bagel.Rectangle
     * @param topLeft top left coordinate
     * @param width width of rectangle
     * @param height height of rectangle
     */
    public Projectile(Point topLeft, double width, double height) {
        super(topLeft, width, height);
    }

    /**
     * Method that update projectiles' motion and attack
     */
    public abstract void update();

    /**
     * Method that implements how a projectile move based on their own speed
     */
    public abstract void move();

    /**
     * Method that checks if projectile is in bound given border coordinates
     * @param topLeft top left border
     * @param bottomRight bottom right border
     * @return boolean for whether the projectile is in bound
     */
    public boolean isInBound(Point topLeft, Point bottomRight) {
        if(super.left() < topLeft.x || super.left() > bottomRight.x
                || super.top() < topLeft.y || super.top() > bottomRight.y + Sailor.getHeight())
            return false;
        else
            return true;
    }

    protected  double getAngle() {
        return Math.atan(direction.y / direction.x);
    }

    /**
     * Getter for flying
     * @return boolean for whether a projectile is still valid for flying
     */
    public boolean isFlying() {
        return flying;
    }

    /**
     * Setter for flying
     * @param flying value set to
     */
    public void setFlying(boolean flying) {
        this.flying = flying;
    }

}
