import bagel.DrawOptions;
import bagel.Image;
import bagel.util.Point;
import bagel.util.Vector2;

/**
 * Class for pirate projectile entity in the game
 */
public final class PirateProjectile extends Projectile{
    private static final Image IMAGE = new Image("res/pirate/pirateProjectile.png");
    private static final double SPEED = 0.4;

    /**
     * Constructor for projectile, set velocity vector, speed, and angle for image rotation
     * @param topLeft top left coordinate
     * @param direction direction of vector's movement
     */
    public PirateProjectile(Point topLeft, Vector2 direction) {
        super(topLeft, SIZE, SIZE);
        this.direction = direction.normalised().mul(SPEED);
        angle = getAngle();
    }

    /**
     * Method that update pirate projectile's motion
     */
    @Override
    public void update() {
        move();
        DrawOptions drawOptions = new DrawOptions();
        drawOptions.setRotation(angle);
        IMAGE.draw(this.left(), this.top(), drawOptions);
    }

    /**
     * Method that prints log for pirate projectile
     * @param s Sailor instance
     */
    @Override
    public void printLog(Sailor s) {
        System.out.println("Pirate inflicts " + Pirate.getDamage() + " damage points on Sailor. " +
                "Sailor's current health: " + s.getHealth() + "/" + s.getMaxHealth());
    }

    /**
     * Check if sailor is in attack range of the projectile
     * @param s Sailor instance
     * @return boolean value indication whether sailor is in attack range
     */
    @Override
    public boolean isInRange(Sailor s) {
        if(s.intersects(this.topLeft())) return true;
        else return false;
    }

    /**
     * Method that does damage to sailor and call print log method
     * @param s Sailor instance
     */
    @Override
    public void attackTo(Sailor s) {
        s.getHurt(Pirate.getDamage());
        printLog(s);
    }

    /**
     * Method that allows projectile to move
     */
    @Override
    public void move() {
        if(isFlying())
            this.moveTo(new Point(this.left() + direction.x, this.top() + direction.y));
    }
}
