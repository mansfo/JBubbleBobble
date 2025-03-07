package view;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import constants.Constants;

/**
 * class representing the cells in the UI
 */
public class CellView {

	private List<BufferedImage> cells;
	
	/**
	 * constructor
	 */
	public CellView() {
		cells = new ArrayList<>();
		try {
			cells.add(ImageIO.read(getClass().getResource("../../resources/Space.png")));
			for(int k=1;k<9;k++) cells.add(ImageIO.read(getClass().getResource("../../resources/Block_"+k+".png")));
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * returns the specific image for the cells of the level
	 * @param level
	 * @return
	 */
	public BufferedImage getImage(int level) {
		return cells.get(level);
	}
	
	public void draw(Graphics2D g2d, int n, int i, int j) {
		g2d.drawImage(getImage(n).getScaledInstance(40, 40, Image.SCALE_SMOOTH), j*Constants.CELL_SIZE, i*Constants.CELL_SIZE, null);
	}
}