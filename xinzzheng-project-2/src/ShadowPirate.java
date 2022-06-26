import bagel.*;
/**
 * Skeleton Code for SWEN20003 Project 2, Semester 1, 2022
 * There are some minor modifications differing to project specification
 * - A border offset is applied when checking if projectile is out of bound for more natural animation
 * - w key will tp player to winning coordinate for level 1
 *
 * The level class takes a large responsibility(being separated into private method) in monitor the game, doing so
 * is to maximize the information hiding between classes such that the minimal amount of information
 * is passed into each class. However, the level class will only either call for loop to access instances contained
 * or invoke method of instances such that cohesion is ensured.
 *
 * @author Xinze Zheng 1266545
 */
public class ShadowPirate extends AbstractGame {
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static String GAME_TITLE = "ShadowPirate";
    private final static String FAIL_MESSAGE = "GAME OVER";
    private static final String TRANS_MESSAGE = "LEVEL COMPLETE!";
    private static final String WIN_MESSAGE = "CONGRATULATIONS!";
    private static final int TRANS_DURATION = 3000;

    private final Level0 level0 = new Level0();
    private final Level1 level1 = new Level1();

    /**
     * Constructor for the game
     */
    public ShadowPirate() {
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowPirate game = new ShadowPirate();
        game.run();
    }

    /**
     * Performs a state update.
     * allows the game to exit when the escape key is pressed.
     */
    @Override
    public void update(Input input) {
        //game stage logic
        if(!level1.isLose() && !level0.isLose()) {
            if (!level0.isWin()) {
                level0.update(input);
            } else {
                if(level0.getWinTime() + TRANS_DURATION >= System.currentTimeMillis()) {
                    Level.getFont().drawString(TRANS_MESSAGE,
                            (Window.getWidth() - Level.getFont().getWidth(TRANS_MESSAGE)) / 2, Level.getMessageY());
                } else if (!level1.isWin())
                    level1.update(input);
            }

        }
        //check lose
        if(level1.isLose() || level0.isLose()) {
            Level.getFont().drawString(FAIL_MESSAGE,
                    (Window.getWidth() - Level.getFont().getWidth(FAIL_MESSAGE)) / 2, Level.getMessageY());
        }
        //check win
        if(level1.isWin()) {
            Level.getFont().drawString(WIN_MESSAGE,
                    (Window.getWidth() - Level.getFont().getWidth(WIN_MESSAGE)) / 2, Level.getMessageY());
        }

        if (input.wasPressed(Keys.ESCAPE)){
            Window.close();
        }
    }
}
