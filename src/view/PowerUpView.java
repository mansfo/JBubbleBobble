package view;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.util.Observable;

import javax.imageio.ImageIO;

import constants.Constants;
import model.PowerUp;

/**
 * class that represents the view of a powerup
 */
@SuppressWarnings("deprecation") 
public class PowerUpView extends FixedEntityView{
	
	private Image[] frames;
	private int powerUpType;
	private int i,j;
	private boolean collected;
	
	/**
	 * constructor
	 */
	public PowerUpView() {
		frames = new Image[10];
		setFrames();
	}
	
	/**
	 * auxiliary method that fills an array with the images of the powerups
	 */
	private void setFrames() {
		for(int k=0; k<10; k++) {
			try {
				frames[k]=ImageIO.read(getClass().getResource("../../resources/powerup"+k+".png")).getScaledInstance(40, -1, Image.SCALE_SMOOTH);
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
		if(o instanceof PowerUp) {
			PowerUp p = (PowerUp)o;
			powerUpType = p.getPowerUpType().toInt();
			i = p.getI()*Constants.CELL_SIZE;
			j = p.getJ()*Constants.CELL_SIZE;
			collected = p.isCollected();
		}
	}

	/**
	 * method that selects the correct image to be displayed
	 */
	@Override
	public Image getImage() {
		return frames[powerUpType-1];
	}

	/**
	 * method that draws in the UI the objects of this class; if a powerup is collected or its type isn't updated yet, it won't be drawn
	 */
	@Override
	public void draw(Graphics2D g2d) {
		if(collected || powerUpType == 0) return;
		g2d.drawImage(getImage(), j, i, null);
	}

}
