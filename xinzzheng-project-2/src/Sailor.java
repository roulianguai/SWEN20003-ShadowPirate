import bagel.*;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * Class for Sailor entity
 */
public final class Sailor extends Character implements MeleeAttackable<Enemy>{
    private static final int FONT_SIZE = 30;
    private static final int WIDTH = 40;
    private static final int HEIGHT = 58;
    private static final int HEALTH_X = 10;
    private static final int HEALTH_Y = 25;
    private static final int ATTACK_DURATION = 1000;
    private static final int COOLING = 2000;
    private static final double SPEED = 1;
    private static final Image IMAGE_L = new Image("res/sailor/sailorLeft.png");
    private static final Image IMAGE_R = new Image("res/sailor/sailorRight.png");
    private static final Image IMAGE_HIT_L = new Image("res/sailor/sailorHitLeft.png");
    private static final Image IMAGE_HIT_R = new Image("res/sailor/sailorHitRight.png");
    private static final Font FONT = new Font("res/wheaton.otf", FONT_SIZE);
    private int maxHealth = 100;
    private int damage = 15;
    private long attackStartTime;
    private boolean attacking = false;
    private boolean cooling = false;
    private boolean faceRight = true;
    private Sailor previousPos;

    /**
     * Constructor extended from Bagel.Rectangle, initialize health to max
     * @param rect Another rectangle class
     */
    public Sailor(Rectangle rect) {
        super(rect);
        setHealth(maxHealth);
    }

    /**
     * Constructor extended from Bagel.Rectangle, initialize health to max
     * @param topLeft top left coordinate
     */
    public Sailor(Point topLeft) {
        super(topLeft, WIDTH, HEIGHT);
        setHealth(maxHealth);
    }

    /**
     * Method that monitor sailor's movement, rendering, and status
     * @param input Input from bagel
     */
    public void update(Input input) {
        control(input);
        renderHP();
        if(faceRight && !attacking) {
            IMAGE_R.drawFromTopLeft(super.left(), super.top());
        } else if (faceRight) {
            IMAGE_HIT_R.drawFromTopLeft(super.left(), super.top());
        } else if(!attacking) {
            IMAGE_L.drawFromTopLeft(super.left(), super.top());
        } else {
            IMAGE_HIT_L.drawFromTopLeft(super.left(), super.top());
        }
        if(attacking) {
            if(attackStartTime + ATTACK_DURATION <=  System.currentTimeMillis()) {
                attacking = false;
                setCooling(true);
            }
        }
        if(cooling) {
            if(getCoolingStartTime() + COOLING <= System.currentTimeMillis()) {
                cooling = false;
            }
        }
    }

    /**
     * Method that makes the sailor shift back to previous valid position
     */
    public void shiftBack() {
        this.moveTo(previousPos.topLeft());
    }

    /**
     * Method that attacks to an enemy and update enemy's state
     * @param e An enemy instance
     */
    @Override
    public void attackTo(Enemy e) {
        e.getHurt(getDamage());
        printLog(e);
        e.setInvincible(true);
    }

    /**
     * Method that check is an enemy is in attack range
     * @param e enemy instance
     * @return boolean value indicating whether enemy is in attack range of sailor
     */
    @Override
    public boolean isInRange(Enemy e) {
        return e.intersects(this);
    }

    /**
     * Method that print log when sailor attacks an enemy
     * @param o Instance being attacked
     */
    @Override
    public void printLog(Enemy o) {
        if(o instanceof Pirate) {
            System.out.println("Sailor inflicts " + damage + " damage points on Pirate. " +
                    "Pirate's current health: " + o.getHealth() + "/" + Pirate.getMaxHealth());
        } else {
            System.out.println("Sailor inflicts " + damage + " damage points on Blackbeard. " +
                    "Blackbeard's current health: " + o.getHealth() + "/" + Blackbeard.getMaxHealth());
        }
    }

    @Override
    protected void renderHPDetail(String health, DrawOptions drawOptions) {
        FONT.drawString(health, HEALTH_X, HEALTH_Y, drawOptions);
    }

    private void control(Input input) {
        previousPos = new Sailor(this);
        if(input.isDown(Keys.LEFT)) {
            faceRight = false;
            this.moveTo(new Point(this.left() - SPEED, this.top()));
        } else if(input.isDown(Keys.RIGHT)) {
            faceRight = true;
            this.moveTo(new Point(this.left() + SPEED, this.top()));
        } else if(input.isDown(Keys.UP)) {
            this.moveTo(new Point(this.left(), this.top() - SPEED));
        } else if(input.isDown(Keys.DOWN)) {
            this.moveTo(new Point(this.left(), this.top() + SPEED));
        }
        if(input.isDown(Keys.S) && !cooling && !attacking) {
            setAttacking(true);
        }
    }

    protected double getPercentage(double health) {
        return health/maxHealth;
    }

    /**
     * Getter for constant WIDTH
     * @return int constant WIDTH
     */
    public static int getWidth() {
        return WIDTH;
    }

    /**
     * Getter for constant HEIGHT
     * @return int constant HEIGHT
     */
    public static int getHeight() {
        return HEIGHT;
    }

    /**
     * Getter for attacking status
     * @return boolean for status
     */
    public boolean isAttacking() {
        return attacking;
    }

    /**
     * Getter for damage
     * @return int damage point of sailor
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Getter for maxHealth
     * @return in max health point
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * Setter for attacking status
     * @param attacking value being set to
     */
    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
        attackStartTime = System.currentTimeMillis();
    }

    /**
     * Setter for cooling status
     * @param cooling value being set to
     */
    public void setCooling(boolean cooling) {
        this.cooling = cooling;
        setCoolingStartTime(System.currentTimeMillis());
    }

    /**
     * Setter for damage
     * @param damage value being set to
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * Setter for max health
     * @param maxHealth value being set to
     */
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }


}
