package view;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;

import constants.Constants;
import model.MovingEntity.Facing;
import model.Player;

/**
 * class that represents the view of the player
 */
@SuppressWarnings("deprecation")
public class PlayerView implements Observer{
	
	private double i;
	private double j;
	private Facing direction;
	private Image[] frames;
	private int frameSelector;
	private GameView gameView;
	private boolean isAttacking;
	
	/**
	 * constructor
	 * @param gameView
	 */
	public PlayerView(GameView gameView) {
		this.gameView = gameView;
		direction = Facing.RIGHT;
		frameSelector = 1;
		i = Constants.I_SPAWN*Constants.CELL_SIZE;
		j = Constants.J_SPAWN*Constants.CELL_SIZE;
		setFrames();
	}
	
	/**
	 * auxiliary method that fills an array with the images of the player
	 */
	private void setFrames() {
		frames = new Image[6];
		for(int i=0; i<6; i++) {
			try {
				frames[i] = ImageIO.read(getClass().getResource("../../resources/player"+i+".png")).getScaledInstance(40, -1, Image.SCALE_SMOOTH);
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
		if(o instanceof Player) {
			if(arg != null && arg instanceof String && arg.equals("attack")) {
				isAttacking = true;
				frameSelector = 0;
			}
			else {
				if(frameSelector>=1) isAttacking = false;
				frameSelector++;
				Player player = (Player) o;
				i = player.getPreciseI()*Constants.CELL_SIZE;
				j = player.getPreciseJ()*Constants.CELL_SIZE;
				direction = player.getFacing();
				gameView.updateLives(player.getLives());
				gameView.updateScore(player.getPoints());
			}
		}
	}
	
	/**
	 * method that selects the correct image to be displayed
	 */
	public Image getImage() {
		if(isAttacking) {
			if(direction == Facing.RIGHT) return frames[5];
			else return frames[4];
		}
		else {
			if(direction == Facing.RIGHT) return frames[(frameSelector/10 % 2)+2];
			else return frames[(frameSelector/10 % 2)];
		}
	}
	
	/**
	 * method that draws the player in the view
	 * @param g2d
	 */
	public void draw(Graphics2D g2d) {
		g2d.drawImage(getImage(), (int)Math.round(j), (int)Math.round(i), null);
	}

}
