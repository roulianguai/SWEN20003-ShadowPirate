import bagel.DrawOptions;
import bagel.util.Colour;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * Super class for character in the game
 */
public abstract class Character extends Rectangle {
    private static final  Colour RED = new Colour(1,0,0);
    private static final  Colour ORANGE = new Colour(0.9, 0.6, 0);
    private static final  Colour GREEN = new Colour(0, 0.8, 0.2);
    private static final int PERCENTAGE_CONVERTOR = 100;
    private static final double HEALTHY_THRESHOLD = 0.65;
    private static final double LOW_HEALTH_THRESHOLD = 0.35;
    private boolean alive = true;
    private boolean faceRight = true;
    private long coolingStartTime;
    private int health;

    /**
     * Constructor extended from Bagel.Rectangle
     * @param rect Another rectangle
     */
    public Character(Rectangle rect) {
        super(rect);
    }

    /**
     * Constructor extended from Bagel.Rectangle
     * @param topLeft top left coordinate
     * @param width width for rectangle
     * @param height height for rectangle
     */
    public Character(Point topLeft, double width, double height) {
        super(topLeft, width, height);
    }

    /**
     * Method that inflict damage to a character and update its alive status
     * @param damage damage point taken
     */
    public void getHurt(int damage) {
        health -= damage;
        if(health <= 0) {
            alive = false;
            health = 0;
        }
    }

    /**
     * Check whether a character is in bound
     * @param topLeft top left coordinate
     * @param bottomRight bottom right coordinate
     * @return boolean value for whether character is in bound
     */
    public boolean isInBound(Point topLeft, Point bottomRight) {
        if(this.left() < topLeft.x || this.left() > bottomRight.x
                || this.top() < topLeft.y || this.top() > bottomRight.y)
            return false;
        else
            return true;
    }

    // Template pattern for rendering hp
    protected void renderHP() {
        double percentage = getPercentage(health);
        String res = String.format("%d", (int)(percentage * PERCENTAGE_CONVERTOR));
        res += "%";
        DrawOptions drawOptions = new DrawOptions();
        if(percentage >= HEALTHY_THRESHOLD) {
            drawOptions.setBlendColour(GREEN);
        } else if (percentage >= LOW_HEALTH_THRESHOLD) {
            drawOptions.setBlendColour(ORANGE);
        } else {
            drawOptions.setBlendColour(RED);
        }
        renderHPDetail(res, drawOptions);
    }

    protected abstract void renderHPDetail(String health, DrawOptions drawOptions);

    protected abstract double getPercentage(double health);

    /**
     * Getter for character's health
     * @return int value for health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Getter for character's facing
     * @return boolean value for whether character is facing right
     */
    public boolean isFaceRight() {
        return faceRight;
    }

    /**
     * Getter for coolingStartTime
     * @return long for cooling start time
     */
    public long getCoolingStartTime() {
        return coolingStartTime;
    }

    /**
     * Getter for alive status of character
     * @return boolean for whether character is alive
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Setter for health
     * @param health value set to
     */
    public void setHealth(int health) {
        this.health = health;
    }

    protected void setFaceRight(boolean faceRight) {
        this.faceRight = faceRight;
    }

    protected void setCoolingStartTime(long coolingStartTime) {
        this.coolingStartTime = coolingStartTime;
    }

}
