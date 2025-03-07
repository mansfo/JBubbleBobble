package view;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.util.Observable;

import javax.imageio.ImageIO;

import constants.Constants;
import model.Bubble.BubbleState;
import model.NormalBubble;

@SuppressWarnings("deprecation")
public class NormalBubbleView extends BubbleView{
	private double i;
	private double j;
	private String frame;
	private BubbleState state;
	
	/**
	 * constructor
	 * it initializes the path to the image of the normal bubbles
	 */
	public NormalBubbleView() {
		frame = "../../resources/bubble.png";
	}
	
	/**
	 * method that receives updates from the observable and updates the fields of this observer
	 */
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof NormalBubble) {
			NormalBubble bubble = (NormalBubble)o;
			i = bubble.getPreciseI()*Constants.CELL_SIZE;
			j = bubble.getPreciseJ()*Constants.CELL_SIZE;
			state = bubble.getState();
		}
	}
	
	/**
	 * method that selects the image to be displayed
	 */
	@Override
	public Image getImage() {
		Image img = null;
		try {
			img = ImageIO.read(getClass().getResource(frame)).getScaledInstance(40, -1, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	
	/**
	 * method that draws in the UI the objects of this class; if the bubble is destroyed, it won't be drawn anymore
	 */
	@Override
	public void draw(Graphics2D g2d) {
		if(state == BubbleState.DESTROYED) return;
		g2d.drawImage(getImage(), (int)Math.round(j), (int)Math.round(i), null);
	}
}
