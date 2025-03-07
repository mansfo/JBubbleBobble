package view;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Observer;

import model.MovingEntity.Facing;

/**
 * abstract class that represents the view of the enemies
 */
@SuppressWarnings("deprecation")
public abstract class EnemyView implements Observer{

	private Facing direction;
	
	public Facing getFacing() {
		return direction;
	}
	
	public void setDirection(Facing f) {
		direction = f;
	}
	
	public abstract Image getImage();
	
	public abstract void draw(Graphics2D g2d);

}
