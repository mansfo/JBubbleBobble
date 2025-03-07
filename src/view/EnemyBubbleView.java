package view;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.util.Observable;

import javax.imageio.ImageIO;

import constants.Constants;
import model.Bubble.BubbleState;
import model.EnemyBubble;

/**
 * class that represents the view of the bubbles that contain an enemy
 */
@SuppressWarnings("deprecation")
public class EnemyBubbleView extends BubbleView {

	private double i, j;
	private Image[] frames;
	private long time;
	private int enemyType;
	private BubbleState state;
	private int currCellH;
	
	/**
	 * constructor
	 * enemyType is a parameter that is used to identify which is the enemy inside the bubble and select then the correct image
	 */
	public EnemyBubbleView() {
		frames = new Image[12];
		enemyType = -1;
		setFrames();
	}
	
	/**
	 * auxiliary method that fills an array with the images of the bubbles containing enemies
	 */
	private void setFrames() {
		for(int k=0; k<12; k++) {
			try {
				frames[k] = ImageIO.read(getClass().getResource("../../resources/enemyBB"+k+".png")).getScaledInstance(40, -1, Image.SCALE_SMOOTH);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * method that receives updates from the observable and updates the fields of this observer
	 */
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof EnemyBubble) {
			EnemyBubble eb = (EnemyBubble)o;
			if (enemyType == -1) {
				if(arg.equals("benzo")) enemyType = 0;
				else if(arg.equals("boris")) enemyType = 4;
				else enemyType = 8;
			}
			currCellH = eb.getI();
			i = eb.getPreciseI()*Constants.CELL_SIZE;
			j = eb.getPreciseJ()*Constants.CELL_SIZE;
			time = eb.checkElapsedTime();
			state = eb.getState();
		}
	}

	/**
	 * method that selects the correct image to be displayed
	 */
	@Override
	public Image getImage() {
		if(state == BubbleState.DESTROYED) return frames[enemyType+3];
		if(time <= 20L) return frames[enemyType];
		else if(time > 20L && time <= 25L) return frames[enemyType+1];
		else return frames[enemyType+2];
	}

	/**
	 * method that draws in the UI the objects of this class; if the bubble is destroyed, it won't be drawn anymore
	 */
	@Override
	public void draw(Graphics2D g2d) {
		if(time > 30L || (state==BubbleState.DESTROYED && currCellH == Constants.HEIGHT-3) || enemyType == -1) return;
		g2d.drawImage(getImage(), (int)Math.round(j), (int)Math.round(i), null);
	}

}
