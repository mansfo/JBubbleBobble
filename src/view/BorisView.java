package view;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.util.Observable;

import javax.imageio.ImageIO;

import constants.Constants;
import model.Boris;
import model.Enemy.EnemyState;
import model.MovingEntity.Facing;

/**
 * class that represents the view of the enemy type Boris
 * an instance of this class will be created to be the observer of a Boris, for every Boris
 */
@SuppressWarnings("deprecation")
public class BorisView extends EnemyView{
	private int frameSelector;
	private Image[] frames;
	private double i,j;
	private EnemyState state;
	
	/**
	 * constructor
	 */
	public BorisView() {
		frames = new Image[4];
		setFrames();
		i = 1000;
		j = 1000;
	}
	
	/**
	 * method that receives updates from the observable and updates the fields of this observer
	 */
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof Boris) {
			Boris b = (Boris) o;
			i = b.getPreciseI()*Constants.CELL_SIZE;
			j = b.getPreciseJ()*Constants.CELL_SIZE;
			setDirection(b.getFacing());
			state = b.getState();
		}
	}
	
	/**
	 * auxiliary method that fills an array with the images of the enemy Boris
	 */
	private void setFrames() {
		for(int i=0; i<4; i++) {
			try {
				frames[i] = ImageIO.read(getClass().getResource("../../resources/boris"+i+".png")).getScaledInstance(40, -1, Image.SCALE_SMOOTH);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * method that draws in the UI the objects of this class; if the enemy is dead, it won't be drawn anymore
	 */
	public void draw(Graphics2D g2d) {
		if(state == EnemyState.DEAD) return;
		g2d.drawImage(getImage(), (int)Math.round(j), (int)Math.round(i), null);
	}
	
	/**
	 * method that selects the correct image to be displayed
	 */
	@Override
	public Image getImage() {
		if(getFacing() == Facing.RIGHT) {
			return frames[frameSelector++ % 2];
		}
		else return frames[frameSelector++ % 2 +2];
	}

}
