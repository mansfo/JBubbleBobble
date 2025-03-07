package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * class that represents the panel in which the entities in the game will be drawn
 */
public class EntityPanel extends JPanel {

	private static final long serialVersionUID = 2201287082537125781L;
	
	private GameView gameView;
	private PlayerView player;
	
	/**
	 * constructor
	 * @param player: represents the view of the player
	 * @param gameView: the panel in which the object of this class will be
	 */
	public EntityPanel(PlayerView player, GameView gameView) {
		this.player = player;
		this.gameView = gameView;
		setBackground(Color.green);
		setOpaque(false);
		setPreferredSize(new Dimension(1000, 630));
		Timer t = new Timer(10, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
			}
			
		});
		t.start();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		drawEnemies(g2d);
		drawBubbles(g2d);
		drawObjects(g2d);
		player.draw(g2d);
	}

	/**
	 * private methods used to draw the enemies, the bubbles, the items and the powerups in the view
	 * @param g2d
	 */
	private void drawEnemies(Graphics2D g2d) {
		for(Integer e: gameView.getEnemyMap().keySet()) if(gameView.getEnemyMap().get(e)!=null) gameView.getEnemyMap().get(e).draw(g2d);
	}
	
	private void drawBubbles(Graphics2D g2d) {
		for(Integer b: gameView.getBubbleMap().keySet()) if(gameView.getBubbleMap().get(b) != null) gameView.getBubbleMap().get(b).draw(g2d);
	}
	
	private void drawObjects(Graphics2D g2d) {
		for(Integer it: gameView.getObjMap().keySet()) if(gameView.getObjMap().get(it) != null) gameView.getObjMap().get(it).draw(g2d);
	}
}
