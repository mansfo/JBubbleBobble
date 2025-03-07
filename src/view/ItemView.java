package view;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.util.Observable;
import javax.imageio.ImageIO;
import constants.Constants;
import model.Item;

/**
 * class that represents the view of an item
 */
@SuppressWarnings("deprecation")
public class ItemView extends FixedEntityView{

	private int i,j;
	private Image[] frames;
	private int itemType;
	public boolean collected;
	
	/**
	 * constructor
	 * itemType is a parameter that is used to identify which is the enemy inside the bubble and select then the correct image
	 */
	public ItemView() {
		frames = new Image[4];
		itemType = -1;
		setFrames();
	}
	
	/**
	 * auxiliary method that fills an array with the images of the items
	 */
	private void setFrames() {
		for(int k=0;k<4;k++) {
			try {
				frames[k]=ImageIO.read(getClass().getResource("../../resources/item"+k+".png")).getScaledInstance(40, -1, Image.SCALE_SMOOTH);
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
		if(o instanceof Item) {
			Item it = (Item)o;
			i = (Constants.HEIGHT-2)*Constants.CELL_SIZE;
			j = it.getJ()*Constants.CELL_SIZE;
			collected = it.isCollected();
			if(arg != null && itemType == -1) {
				if(arg.equals("DIAMOND")) itemType = 0;
				else if(arg.equals("CRYSTAL")) itemType = 1;
				else if(arg.equals("BANANA")) itemType = 2;
				else itemType = 3;
			}
		}
	}
	
	/**
	 * method that selects the correct image to be displayed
	 */
	@Override
	public Image getImage() {
		return frames[itemType];
	}
	
	/**
	 * method that draws in the UI the objects of this class; if an item is collected or its type isn't updated yet, it won't be drawn
	 */
	@Override
	public void draw(Graphics2D g2d) {
		if(collected || itemType == -1) return;
		g2d.drawImage(getImage(), j, i, null);
	}

}
