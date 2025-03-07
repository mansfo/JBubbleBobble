package view;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.util.Observable;

import javax.imageio.ImageIO;

import constants.Constants;
import model.Benzo;
import model.Enemy.EnemyState;
import model.MovingEntity.Facing;

/**
 * class that represents the view of the enemy type Benzo
 * an instance of this class will be created to be the observer of a Benzo, for every Benzo
 */
@SuppressWarnings("deprecation")
public class BenzoView extends EnemyView{

	private int frameSelector;
	private Image[] frames;
	private double i,j;
	private EnemyState state;
	
	/**
	 * constructor
	 * @param view
	 */
	public BenzoView() {
		frames = new Image[4];
		setFrames();
		i = Constants.VIEW_ENTITY_INIT;
		j = Constants.VIEW_ENTITY_INIT;
	}
	
	/**
	 * auxiliary method that fills an array with the images of the enemy Benzo
	 */
	private void setFrames() {
		for(int i=0; i<4; i++) {
			try {
				frames[i] = ImageIO.read(getClass().getResource("../../resources/benzo"+i+".png")).getScaledInstance(40, -1, Image.SCALE_SMOOTH);
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
		if(o instanceof Benzo) {
			Benzo b = (Benzo) o;
			i = b.getPreciseI()*Constants.CELL_SIZE-5;
			j = b.getPreciseJ()*Constants.CELL_SIZE;
			setDirection(b.getFacing());
			state = b.getState();
		}
	}
	
	/**
	 * method that selects the correct image to be displayed
	 */
	@Override
	public Image getImage() {
		return frames[frameSelector++/12 % 2 + (getFacing() == Facing.RIGHT? 0:2)];
	}
	
	/**
	 * method that draws in the UI the objects of this class; if the enemy is dead, it won't be drawn anymore
	 */
	public void draw(Graphics2D g2d) {
		if(state == EnemyState.DEAD) return;
		g2d.drawImage(getImage(), (int)Math.round(j), (int)Math.round(i), null);
	}
}
