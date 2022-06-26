import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.Window;
import bagel.util.Point;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Class for level 1 in the game
 */
public final class Level1 extends Level {
    private static final Image BACKGROUND = new Image("res/background1.png");
    private static final String CSV = "res/level1.csv";
    private static final String START_MESSAGE1 = "PRESS SPACE TO START";
    private static final String START_MESSAGE2 = "PRESS S TO ATTACK";
    private static final String START_MESSAGE3 = "FIND THE TREASURE";
    private final ArrayList<Item> items = new ArrayList<>();
    private final Inventory inventory = new Inventory();
    private Treasure treasure;

    /**
     * Constructor, read csv file
     */
    public Level1(){
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
                } else if(cmd[0].equals("Sword")) {
                    items.add(new Sword(p));
                } else if(cmd[0].equals("Potion")) {
                    items.add(new Potion(p));
                } else if(cmd[0].equals("Elixir")) {
                    items.add(new Elixir(p));
                } else if(cmd[0].equals("Treasure")) {
                    treasure = new Treasure(p);
                } else if(cmd[0].equals("Block")) {
                    getObstacles().add(new Bomb(p));
                } else if(cmd[0].equals("Pirate")) {
                    getEnemies().add(new Pirate(p));
                } else if(cmd[0].equals("Blackbeard")) {
                    getEnemies().add(new Blackbeard(p));
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
     * Method that governs everything about level1's palying
     * @param input the input control(Keyboard)
     */
    @Override
    public void update(Input input) {
        if(!isGameStart()) {
            renderStartMessage();
            if(input.wasPressed(Keys.SPACE)) setGameStart(true);
        } else {
            BACKGROUND.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
            //check bomb property before common obstacle property
            updateBomb();

            updateItems();

            inventory.update();

            updateSailor(input);

            updateEnemies();

            updateProjectile();

            treasure.update();
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

    private void updateBomb() {
        for(Obstacle obstacle : getObstacles()) {
            if(obstacle instanceof Bomb) {
                //deal damage to sailor only once
                if(((Bomb) obstacle).isInRange(getSailor()) && !((Bomb) obstacle).isExploding()) {
                    ((Bomb) obstacle).attackTo(getSailor());
                }
            }
        }
        //remove exploded item
        for(int i = 0; i < getObstacles().size(); i++) {
            if(!getObstacles().get(i).isExists()) {
                getObstacles().remove(i);
            }
        }
        //check obstacle property
        updateObstacles();
    }

    // item pick up and inventory render
    private void updateItems() {
        for(Item item : items) {
            if(!item.isPickUp()) {
                item.update();
                if (item.isInCollision(getSailor())) {
                    item.interact(getSailor());
                    inventory.addItem(item);
                }
            }
        }
    }

    //sailor control
    private void updateSailor(Input input) {
        getSailor().update(input);
        if(!getSailor().isInBound(getTopLeft(), getBottomRight())) getSailor().shiftBack();
        if(!getSailor().isAlive()) {
            setLose(true);
        }
        if(treasure.isCollided(getSailor())) {
            setWin(true);
        }
    }
}
