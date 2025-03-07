package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JPanel;

import constants.Constants;
import model.Block;
import model.Cell;

/**
 * class that represents the panel containing the cells of the current level
 */
public class LevelPanel extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = -2840842718360018305L;
	
	private CellView cellView;
	private Cell[][] levelCells;
	private int currentLevel;

	/**
	 * constructor
	 * @param levelCells: the cells to be displayed
	 * @param currentLevel: the level that is going to be drawn
	 */
	public LevelPanel(Cell[][] levelCells, int currentLevel) {
		this.currentLevel = currentLevel;
		this.levelCells = levelCells;
		cellView = new CellView();
		levelCells = new Cell[Constants.HEIGHT][Constants.WIDTH];
		setBackground(Color.black);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		drawCells(g2d);
	}
	
	/**
	 * method that draws the cells by turning the two-dimensional array in a stream of cells and drawing each one of them
	 * @param g2d
	 */
	private void drawCells(Graphics2D g2d) {
		Arrays.stream(levelCells)
		.flatMap(x->Arrays.stream(x))
		.filter(x->x instanceof Block)
		.forEach(x->cellView.draw(g2d, currentLevel, x.getI(), x.getJ()));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}
	
}
