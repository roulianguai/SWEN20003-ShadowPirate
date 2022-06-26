import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * Class for blackbeard in the game
 */
public final class Blackbeard extends Enemy{
    private static final int ATTACK_RANGE = 200;
    private static final int MAX_HEALTH = 90;
    private static final int DAMAGE = 20;
    private static final int COOLING_TIME = 1500;
    private static final int WIDTH = 40;
    private static final int HEIGHT = 58;
    private static final Image IMAGE_L = new Image("res/blackbeard/blackbeardLeft.png");
    private static final Image IMAGE_R = new Image("res/blackbeard/blackbeardRight.png");
    private static final Image IMAGE_HIT_L = new Image("res/blackbeard/blackbeardHitLeft.png");
    private static final Image IMAGE_HIT_R = new Image("res/blackbeard/blackbeardHitRight.png");

    /**
     * Constructor, set health to max
     * @param topLeft top left coordinate of starting position
     */
    public Blackbeard(Point topLeft) {
        super(topLeft, WIDTH, HEIGHT);
        setHealth(MAX_HEALTH);
    }

    /**
     * Method that check if sailor is in attack range of blackbeard
     * @param s Sailor instance
     * @return boolean value indicating if sailor is in attack range
     */
    @Override
    public boolean isInAttackRange(Sailor s) {
        Rectangle rec = new Rectangle(new Point(this.centre().x - (double)ATTACK_RANGE / 2,
                this.centre().y - (double)ATTACK_RANGE / 2), ATTACK_RANGE, ATTACK_RANGE);
        return rec.intersects(s);
    }

    /**
     * Method that monitor and update blackbeard, including moving and attacking
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
        updateCooling();
        updateInvincible();

    }

    /**
     * Method that updates blackbeard's cooling state based on time after firing
     */
    @Override
    public void updateCooling() {
        if(isCooling()) {
            if(getCoolingStartTime() + COOLING_TIME <= System.currentTimeMillis())
                setCooling(false);
        }
    }

    /**
     * Method that computes percentage health of blackbeard
     * @param health current health
     * @return double percentage health
     */
    @Override
    public double getPercentage(double health) {
        return health/MAX_HEALTH;
    }

    /**
     * Getter for constant max health of blackbeard
     * @return int value for blackbeard max health
     */
    public static int getMaxHealth() {
        return MAX_HEALTH;
    }

    /**
     * Getter for the blackbeard damage
     * @return int value for damage point of blackbeard
     */
    public static int getDamage() {
        return  DAMAGE;
    }
}
