package view;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Observable;

import javax.imageio.ImageIO;

import constants.Constants;
import model.Bubble.BubbleState;
import model.PlayerBubble;

/**
 * class that represents the view of the bubbles blown by the player
 */
@SuppressWarnings("deprecation")
public class PlayerBubbleView extends BubbleView{
	
	private int i;
	private double j;
	private int frameSelector;
	private GameView gameView;
	private Image[] frames;
	private BubbleState state;
	
	/**
	 * constructor
	 * @param gameView
	 */
	public PlayerBubbleView(GameView gameView) {
		this.gameView = gameView;
		frames = new Image[3];
		i = Constants.VIEW_ENTITY_INIT;
		j = Constants.VIEW_ENTITY_INIT;
		setFrames();
	}
	
	/**
	 * auxiliary method that fills an array with the images of the bubbles blown by the player
	 */
	private void setFrames() {
		for(int k=0; k<3; k++) {
			try {
				 frames[k] = ImageIO.read(getClass().getResource("../../resources/plBB"+k+".png")).getScaledInstance(40, -1, Image.SCALE_SMOOTH);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * method that receives updates from the observable and updates the fields of this observer
	 */
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof PlayerBubble) {
			PlayerBubble playerBB = (PlayerBubble)o;
			j = playerBB.getPreciseJ()*Constants.CELL_SIZE;
			i = playerBB.getI()*Constants.CELL_SIZE;
			state = playerBB.getState();
			gameView.repaint();
		}
	}
	
	/**
	 * method that selects the correct image to be displayed
	 */
	@Override
	public Image getImage() {
		if(frameSelector < 2) return frames[frameSelector++];
		else return frames[2];
	}
	
	/**
	 * method that draws in the UI the objects of this class; if the bubble is destroyed, it won't be drawn anymore
	 */
	@Override
	public void draw(Graphics2D g2d) {
		if(state == BubbleState.DESTROYED) return;
		g2d.drawImage(getImage(), (int)Math.round(j), Math.round(i), null);
	}
}
