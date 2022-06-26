import bagel.Image;
import bagel.util.Point;

/**
 * Class for block in level 0
 */
public final class Block extends Obstacle {
    private static final Image IMAGE = new Image("res/block.png");
    private static final int WIDTH = 32;
    private static final int HEIGHT = 40;

    /**
     * Constructor extended from Bagel.Rectangle
     * @param topLeft to left coordinate
     */
    public Block(Point topLeft) {
        super(topLeft, WIDTH, HEIGHT);
    }


    /**
     * Method that draw the block
     */
    @Override
    public void update() {
        IMAGE.drawFromTopLeft(super.left(), super.top());
    }
}
