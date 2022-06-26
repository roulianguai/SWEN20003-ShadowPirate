import bagel.*;
import bagel.util.Point;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Class for level 0 of the game
 */
public final class Level0 extends Level {
    private static final Image BACKGROUND = new Image("res/background0.png");
    private static final String CSV = "res/level0.csv";
    private static final String START_MESSAGE1 = "PRESS SPACE TO START";
    private static final String START_MESSAGE2 = "PRESS S TO ATTACK";
    private static final String START_MESSAGE3 = "USE ARROW KEYS TO FIND LADDER";
    private static final String COMPLETE = "LEVEL COMPLETE!";
    private static final int WIN_X = 990;
    private static final int WIN_Y = 630;
    private long winTime;

    /**
     * Constructor for level 0, read CSV file
     */
    public Level0() {
        readCSV();
    }

    @Override
    protected void readCSV() {
        try(BufferedReader br = new BufferedReader(new FileReader(CSV))) {
            String text = null;
            while((text = br.readLine()) != null) {
                String[] cmd = text.split(",");
                Point p = new Point(Double.parseDouble(cmd[1]), Double.parseDouble(cmd[2]));
                if(cmd[0].equals("Sailor")) {
                    setSailor(new Sailor(p));
                } else if(cmd[0].equals("Block")) {
                    getObstacles().add(new Block(p));
                } else if(cmd[0].equals("Pirate")) {
                    Pirate pirate = new Pirate(p);
                    getEnemies().add(pirate);
                } else if(cmd[0].equals("TopLeft")) {
                    setTopLeft(p);
                } else {
                    setBottomRight(p);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that governs everything about level 0's playing
     * @param input the input control(Keyboard)
     */
    @Override
    public void update(Input input) {
        if(!isGameStart()) {
            renderStartMessage();
            if(input.wasPressed(Keys.SPACE)) setGameStart(true);
        } else {
            BACKGROUND.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
            updateObstacles();
            //draw sailor, control sailor and check bound
            updateSailor(input);

            updateEnemies();

            updateProjectile();
            //press key w to skip level 0
            if(input.wasPressed(Keys.W)) {
                getSailor().moveTo(new Point(WIN_X, WIN_Y));
            }
        }
    }

    @Override
    protected void renderStartMessage() {
        getFont().drawString(START_MESSAGE1, (Window.getWidth() - getFont().getWidth(START_MESSAGE1)) / 2 ,
                getMessageY());
        getFont().drawString(START_MESSAGE2, (Window.getWidth() - getFont().getWidth(START_MESSAGE2)) / 2 ,
                getMessageY() + getMessageYOffset());
        getFont().drawString(START_MESSAGE3, (Window.getWidth() - getFont().getWidth(START_MESSAGE3)) / 2 ,
                getMessageY() + getMessageYOffset() * 2);
    }

    //sailor control
    private void updateSailor(Input input) {
        getSailor().update(input);
        if(!getSailor().isInBound(getTopLeft(), getBottomRight())) getSailor().shiftBack();
        if(getSailor().left() >= WIN_X && getSailor().top() >= WIN_Y){
            winTime = System.currentTimeMillis();
            setWin(true);
        }
        if(!getSailor().isAlive()) setLose(true);
    }

    /**
     * Getter for level 0 win time
     * @return
     */
    public long getWinTime() {
        return winTime;
    }

}
