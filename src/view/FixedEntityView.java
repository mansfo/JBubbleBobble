package view;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Observer;

/**
 * abstract class that represents entities that can't move like items and powerups in the view
 */
@SuppressWarnings("deprecation")
public abstract class FixedEntityView implements Observer{
	
	public abstract Image getImage();
	
	public abstract void draw(Graphics2D g2d);
}
