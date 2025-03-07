package view;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Observer;

/**
 * abstract class representing the bubbles in the UI
 */
@SuppressWarnings("deprecation")
public abstract class BubbleView implements Observer{
	
	public abstract Image getImage();
	
	public abstract void draw(Graphics2D g2d);
}
