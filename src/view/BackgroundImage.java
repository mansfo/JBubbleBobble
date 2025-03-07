package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * class that can be used to set in an easy way an image as a background of a panel
 */
public class BackgroundImage extends JPanel{

	private static final long serialVersionUID = 7720929541224346136L;
	
	private Image img;
	
	/**
	 * constructor
	 * @param path
	 */
	public BackgroundImage(String path) {
		try {
			img = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}	
		this.setPreferredSize(new Dimension(1000, 900));
	}
	
	@Override
	public void paint(Graphics g)
	{
		Graphics2D g2d=(Graphics2D)g;
		AffineTransform identity = new AffineTransform();
		g2d.drawImage(img.getScaledInstance(1000, -1, Image.SCALE_SMOOTH), identity, this);
	}
	
	/**
	 * method that draws the image as a background of the panel
	 */
	public void draw() {
		this.repaint();
	}
}