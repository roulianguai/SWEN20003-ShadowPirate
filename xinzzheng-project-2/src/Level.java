import bagel.Font;
import bagel.Input;
import bagel.util.Point;

import java.util.ArrayList;

/**
 * Abstract class for levels in the game
 */
public abstract class Level {
    private static final Font FONT = new Font("res/wheaton.otf", 55);
    private static final int MESSAGE_Y = 402;
    private static final int MESSAGE_Y_OFFSET = 80;
    private Point topLeft;
    private Point bottomRight;
    private boolean gameStart = false;
    private boolean win = false;
    private boolean lose = false;
    private final ArrayList<Enemy> enemies = new ArrayList<>();
    private final ArrayList<Projectile> projectiles = new ArrayList<>();
    private final ArrayList<Obstacle> obstacles = new ArrayList<>();
    private Sailor sailor;

    /**
     * Method that monitor the game status and update any change
     * @param input the input control(Keyboard)
     */
    public abstract void update(Input input);

    protected abstract void renderStartMessage();

    protected abstract void readCSV();

    // Update behaviour of projectile stored
    protected void updateProjectile() {
        for(Projectile p : projectiles) {
            p.update();
            if(!p.isInBound(topLeft, bottomRight)) p.setFlying(false);
            if(p.isInRange(sailor)) {
                p.attackTo(sailor);
                p.setFlying(false);
            }
        }
        for(int i = 0; i < projectiles.size(); i++) {
            if(!projectiles.get(i).isFlying()) projectiles.remove(i);
        }
    }

    protected void updateEnemies() {
        for(Enemy e : getEnemies()) {
            e.update();
            //check whether direction needs to be reversed
            if(!e.isInBound(getTopLeft(), getBottomRight())) e.reverseDirection();
            for(Obstacle obstacle : obstacles) {
                if(obstacle.checkCollision(e)) {
                    e.reverseDirection();
                    //reverse at most once
                    break;
                }
            }
            //check whether an enemy should fire
            if(e.isInAttackRange(sailor) && !e.isCooling()) {
                if(e instanceof Pirate) {
                    PirateProjectile p = new PirateProjectile(e.centre(), e.rangeAttack(sailor));
                    projectiles.add(p);
                } else if (e instanceof Blackbeard) {
                    BlackbeardProjectile p = new BlackbeardProjectile(e.centre(), e.rangeAttack(sailor));
                    projectiles.add(p);
                }
                e.setCooling(true);
            }
            //check whether is under attack
            if(sailor.isInRange(e) && sailor.isAttacking() && !e.isInvincible()) {
                sailor.attackTo(e);
            }
        }
        //clear dead enemies
        for(int i = 0; i < enemies.size(); i++) {
            if(!enemies.get(i).isAlive()) enemies.remove(i);
        }
    }
    //update obstacles, and check collision for characters
    protected void updateObstacles() {
        for (Obstacle obstacle : obstacles) {
            obstacle.update();
            if(obstacle.checkCollision(getSailor())) getSailor().shiftBack();
        }
    }

    /**
     * Getter for constant message render y coordinate
     * @return int constant value for message render y coordinate
     */
    public static int getMessageY() {
        return MESSAGE_Y;
    }

    /**
     * Getter for constant message render y offset
     * @return int constant value for message render y offset
     */
    public static int getMessageYOffset() {
        return MESSAGE_Y_OFFSET;
    }

    /**
     * Getter for constant font for message
     * @return Font for message
     */
    public static Font getFont() {
        return FONT;
    }

    /**
     * Getter for top left coordinate
     * @return Point for top left border
     */
    public Point getTopLeft() {
        return topLeft;
    }

    /**
     * Getter for bottom left coordinate
     * @return Point for bottom left border
     */
    public Point getBottomRight() {
        return bottomRight;
    }


    /**
     * Getter for arraylist of obstacles
     * @return arraylist reference to obstacles
     */
    public ArrayList<Obstacle> getObstacles() {
        return obstacles;
    }

    /**
     * Getter for game start status
     * @return boolean value indicating whether game start
     */
    public boolean isGameStart() {
        return gameStart;
    }

    /**
     * Getter for arraylist of enemies
     * @return arraylist reference to enemies
     */
    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    /**
     * Setter for top left coordinate
     * @param topLeft value being set to
     */
    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }

    /**
     * Setter for bottom left coordinate
     * @param bottomRight value being set to
     */
    public void setBottomRight(Point bottomRight) {
        this.bottomRight = bottomRight;
    }

    /**
     * Setter for game start status
     * @param gameStart value being set to
     */
    public void setGameStart(boolean gameStart) {
        this.gameStart = gameStart;
    }

    /**
     * Getter for sailor
     * @return reference to a sailor instance
     */
    public Sailor getSailor() {
        return sailor;
    }

    /**
     * Setter for sailor
     * @param sailor an object being set to
     */
    public void setSailor(Sailor sailor) {
        this.sailor = sailor;
    }

    /**
     * Getter for win condition
     * @return boolean value indicating is a level is won
     */
    public boolean isWin() {
        return win;
    }

    /**
     * Setter for win condition
     * @param win boolean value being set to
     */
    public void setWin(boolean win) {
        this.win = win;
    }

    /**
     * Getter for lose status of a level
     * @return boolean value indicating is a level is failed
     */
    public boolean isLose() {
        return lose;
    }

    /**
     * Setter for lose status
     * @param lose value being set to
     */
    public void setLose(boolean lose) {
        this.lose = lose;
    }
}
