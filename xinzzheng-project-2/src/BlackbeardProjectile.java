import bagel.DrawOptions;
import bagel.Image;
import bagel.util.Point;
import bagel.util.Vector2;

/**
 * Class for blackbeard projectile in the game
 */
public final class BlackbeardProjectile extends Projectile{
    private static final Image IMAGE = new Image("res/blackbeard/blackbeardProjectile.png");
    private static final double SPEED = 0.8;

    /**
     * Constructor, initialize speed and angle of motion
     * @param topLeft top left coordinate of starting point
     * @param direction direction of moving
     */
    public BlackbeardProjectile(Point topLeft, Vector2 direction) {
        super(topLeft, SIZE, SIZE);
        //normalize the direction vector and fit with speed
        this.direction = direction.normalised().mul(SPEED);
        angle = getAngle();
    }

    /**
     * Method that update blackbeard projectile's motion
     */
    @Override
    public void update() {
        move();
        DrawOptions drawOptions = new DrawOptions();
        drawOptions.setRotation(angle);
        IMAGE.draw(this.left(), this.top(), drawOptions);
    }

    /**
     * Method that prints log for blackbeard projectile
     * @param s Sailor instance
     */
    @Override
    public void printLog(Sailor s) {
        System.out.println("Blackbeard inflicts " + Blackbeard.getDamage() + " damage points on Sailor. " +
                "Sailor's current health: " + s.getHealth() + "/" + s.getMaxHealth());
    }

    /**
     * Check if sailor is in attack range of the projectile
     * @param o Sailor instance
     * @return boolean value indication whether sailor is in attack range
     */
    @Override
    public boolean isInRange(Sailor o) {
        return o.intersects(this);
    }

    /**
     * Method that does damage to sailor and call print log method
     * @param s Sailor instance
     */
    @Override
    public void attackTo(Sailor s) {
        s.getHurt(Blackbeard.getDamage());
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
