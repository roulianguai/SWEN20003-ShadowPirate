import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * Class for pirate entity in the game
 */
public class Pirate extends Enemy{
    private static final int ATTACK_RANGE = 100;
    private static final int MAX_HEALTH = 45;
    private static final int DAMAGE = 10;
    private static final int COOLING_TIME = 3000;
    private static final int WIDTH = 40;
    private static final int HEIGHT = 58;
    private static final Image IMAGE_L = new Image("res/pirate/pirateLeft.png");
    private static final Image IMAGE_R = new Image("res/pirate/pirateRight.png");
    private static final Image IMAGE_HIT_L = new Image("res/pirate/pirateHitLeft.png");
    private static final Image IMAGE_HIT_R = new Image("res/pirate/pirateHitRight.png");

    /**
     * Constructor for pirate, initialize health
     * @param topLeft top left coordinate
     */
    public Pirate(Point topLeft) {
        super(topLeft, WIDTH, HEIGHT);
        setHealth(MAX_HEALTH);
    }


    /**
     * Method that draw pirate, monitor cooling and invincible time, movement
     */
    @Override
    public void update() {
        if(getDirection().equals(getLeft())) setFaceRight(false);
        else setFaceRight(true);
        //drawing
        if(isFaceRight() && !isInvincible()) {
            IMAGE_R.drawFromTopLeft(super.left(), super.top());
        } else if (isFaceRight() && isInvincible()) {
            IMAGE_HIT_R.drawFromTopLeft(super.left(), super.top());
        } else if(!isFaceRight() && !isInvincible()) {
            IMAGE_L.drawFromTopLeft(super.left(), super.top());
        } else {
            IMAGE_HIT_L.drawFromTopLeft(super.left(), super.top());
        }
        //auto moving
        move();
        //display HP
        renderHP();
        //count cooling time and invincible time
        updateCooling();
        updateInvincible();
    }

    /**
     * Method that check whether sailor is in attack range of pirate
     * @return boolean value indicating whether is in attack range
     */
    @Override
    public boolean isInAttackRange(Sailor s) {
        Rectangle rec = new Rectangle(new Point(this.centre().x - (double)ATTACK_RANGE / 2,
                this.centre().y - (double)ATTACK_RANGE / 2), ATTACK_RANGE, ATTACK_RANGE);
        return rec.intersects(s);
    }

    /**
     * Method that updates pirate's cooling state based on time after firing
     */
    @Override
    public void updateCooling() {
        if(isCooling()) {
            if(getCoolingStartTime() + COOLING_TIME <= System.currentTimeMillis())
                setCooling(false);
        }
    }

    /**
     * Method that computes percentage health of pirate
     * @param health current health
     * @return double percentage health
     */
    @Override
    public double getPercentage(double health) {
        return health/MAX_HEALTH;
    }

    /**
     * Static getter for MAX_HEALTH
     * @return int value for MAX_HEALTH
     */
    public static int getMaxHealth() {
        return MAX_HEALTH;
    }

    /**
     * Static getter for WIDTH
     * @return int value for constant WIDTH
     */
    public static int getWidth() {
        return WIDTH;
    }

    /**
     * Static getter for HEIGHT
     * @return int value for constant HEIGHT
     */
    public static int getHeight() {
        return HEIGHT;
    }

    /**
     * Static getter for DAMAGE
     * @return int value for constant DAMAGE
     */
    public static int getDamage() {
        return  DAMAGE;
    }

}
