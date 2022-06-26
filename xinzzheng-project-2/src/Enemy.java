import bagel.DrawOptions;
import bagel.Font;
import bagel.util.Point;
import bagel.util.Vector2;

import java.util.Random;

/**
 * SuperClass for enemy in the game
 */
public abstract class Enemy extends Character {

    private static final int HEALTH_OFFSET = 6;
    private static final int INVINCIBLE_DURATION = 1500;
    private static final int RANDOM_BOUND = 4;
    private static final int RANDOM_UP = 0;
    private static final int RANDOM_DOWN = 1;
    private static final int RANDOM_LEFT = 2;
    private static final double SPEED_MIN = 0.2;
    private static final double SPEED_MAX = 0.7;
    private static final Vector2 UP = new Vector2(0, 1);
    private static final Vector2 DOWN = new Vector2(0, -1);
    private static final Vector2 LEFT = new Vector2(-1, 0);
    private static final Vector2 RIGHT = new Vector2(1, 0);
    private static final Font FONT = new Font("res/wheaton.otf", 15);
    private boolean invincible = false;
    private boolean cooling = false;
    private long invincibleStartTime;
    private final double speed;
    private Vector2 direction;

    /**
     * Constructor. Generate random speed and direction for an enemy
     * @param topLeft top left coordinate
     * @param width width of rectangle
     * @param height height of rectangle
     */
    public Enemy(Point topLeft, double width, double height) {
        super(topLeft, width, height);
        speed = generateSpeed();
        direction = generateDirection();
    }

    /**
     * Calculate the position vector from current enemy position to sailor's position
     * @param s sailor instance
     * @return Position vector for projectile
     */
    protected Vector2 rangeAttack(Sailor s) {
        double x = s.centre().x - this.centre().x;
        double y = s.centre().y - this.centre().y;
        return new Vector2(x, y);
    }

    /**
     * Method that check whether sailor is in attack range of enemy
     * @param s Sailor instance
     * @return boolean indicating whether sailor is in an enemy's attack range
     */
    public abstract boolean isInAttackRange(Sailor s);

    /**
     * Method that monitor and update enemy, including moving and attacking
     */
    public abstract void update();

    /**
     * Method that updates enemy's cooling state based on time after firing
     */
    public abstract void updateCooling();

    /**
     * Method that reverse direction for enemy
     */
    public void reverseDirection() {
        direction = new Vector2(direction.x * -1, direction.y * -1);
    }

    @Override
    protected void renderHPDetail(String health, DrawOptions drawOptions) {
        FONT.drawString(health, this.left(), this.top() - HEALTH_OFFSET, drawOptions);
    }

    protected void updateInvincible() {
        if(isInvincible()) {
            if(invincibleStartTime + INVINCIBLE_DURATION<= System.currentTimeMillis())
                setInvincible(false);
        }
    }

    protected void move() {
        this.moveTo(new Point(this.left() + direction.x * speed, this.top() + direction.y * speed));
    }

    // Generate a random direction with vector size 1
    private Vector2 generateDirection() {
        int num = new Random().nextInt(RANDOM_BOUND);
        if(num == RANDOM_UP) {
            return UP;
        } else if(num == RANDOM_DOWN) {
            return DOWN;
        } else if (num == RANDOM_LEFT) {
            return LEFT;
        } else {
            return RIGHT;
        }
    }

    // Generate random speed between max and min
    private double generateSpeed() {
        return Math.random()*(SPEED_MAX-SPEED_MIN)+SPEED_MIN;
    }

    /**
     * Getter for invincible
     * @return boolean value for invincible
     */
    public boolean isInvincible() {
        return invincible;
    }

    /**
     * Getter for cooling
     * @return boolean value for cooling
     */
    public boolean isCooling() {
        return cooling;
    }

    /**
     * Getter for direction
     * @return Vector2 value for direction
     */
    public Vector2 getDirection() {
        return direction;
    }

    /**
     * Getter for LEFT
     * @return Vector2 value for LEFT
     */
    public Vector2 getLeft() {
        return LEFT;
    }

    /**
     * Setter for invincible
     * @param invincible value set to
     */
    public void setInvincible(boolean invincible) {
        this.invincible = invincible;
        this.invincibleStartTime = System.currentTimeMillis();
    }

    /**
     * Setter for cooling
     * @param cooling value set to
     */
    public void setCooling(boolean cooling) {
        this.cooling = cooling;
        super.setCoolingStartTime(System.currentTimeMillis());
    }

}
