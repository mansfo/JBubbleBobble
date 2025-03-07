package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.Benzo;
import model.Blubba;
import model.Boris;
import model.Bubble;
import model.Cell;
import model.Enemy;
import model.EnemyBubble;
import model.FixedEntity;
import model.Item;
import model.NormalBubble;
import model.PlayerBubble;
import model.PowerUp;

/**
 * class that represents the main panel in which every game will be displayed
 */
public class GameView extends JPanel{

	private static final long serialVersionUID = 3907740770177113519L;

	protected static final int CELL_SIZE = 40;
	private JButton button;
	private LevelPanel levelField;
	private EntityPanel entPanel;
	private JLabel currentLives;
	private JLabel currentScore;
	private PlayerView playerView;
	private ConcurrentHashMap<Integer, EnemyView> enemies;
	private ConcurrentHashMap<Integer, BubbleView> bubbles;
	private ConcurrentHashMap<Integer, FixedEntityView> objects;
	private boolean loadingCompleted;
	
	/**
	 * constructor
	 * it initializes the player's view and three maps that assign the id of an enemy/bubble/item/powerup to the corresponding view
	 */
	public GameView() {
		playerView = new PlayerView(this);
		enemies = new ConcurrentHashMap<Integer, EnemyView>();
		bubbles = new ConcurrentHashMap<Integer, BubbleView>();
		objects = new ConcurrentHashMap<Integer, FixedEntityView>();
		initPanel();
	}
	
	/**
	 * private method that initializes the components in the main panel
	 */
	private void initPanel() {
		setLayout(new BorderLayout());
		JPanel north = new JPanel(new FlowLayout());
		button = new JButton("MENU");
		button.setPreferredSize(new Dimension(250,50));
		button.setBackground(Color.black);
		button.setForeground(Color.red);
		button.setBorder(BorderFactory.createEmptyBorder());
		button.setFont(new Font("Press Start 2P", Font.PLAIN, 25));
		north.setPreferredSize(new Dimension(600,60));
		north.setBackground(Color.black);
		north.add(button);
		
		JLabel lives = new JLabel();
		lives.setPreferredSize(new Dimension(200,50));
		lives.setFont(new Font("Press Start 2P", Font.PLAIN, 25));
		lives.setText("LIVES:");
		lives.setForeground(Color.green);
		lives.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		
		JLabel score = new JLabel();
		score.setPreferredSize(new Dimension(200,50));
		score.setFont(new Font("Press Start 2P", Font.PLAIN, 25));
		score.setText("SCORE:");
		score.setForeground(Color.red);
		
		currentLives = new JLabel();
		currentLives.setPreferredSize(new Dimension(200,50));
		currentLives.setFont(new Font("Press Start 2P", Font.PLAIN, 25));
		currentLives.setForeground(Color.white);
		currentLives.setText("03");
		
		currentScore = new JLabel();
		currentScore.setPreferredSize(new Dimension(200,50));
		currentScore.setFont(new Font("Press Start 2P", Font.PLAIN, 25));
		currentScore.setForeground(Color.white);
		currentScore.setText("0000000");
		
		north.add(lives);
		north.add(currentLives);
		north.add(score);
		north.add(currentScore);
		add(north, BorderLayout.NORTH);
		
		JPanel west = new JPanel();
		west.setPreferredSize(new Dimension(110,160));
		west.setBackground(Color.black);
		add(west, BorderLayout.WEST);
		
		JPanel east = new JPanel();
		east.setPreferredSize(new Dimension(110,160));
		east.setBackground(Color.black);
		add(east, BorderLayout.EAST);
	}
	
	public PlayerView getPlayerView() {
		return playerView;
	}
	
	public LevelPanel getLevelPanel() {
		return levelField;
	}
	
	public Map<Integer, EnemyView> getEnemyMap(){
		return enemies;
	}
	
	public Map<Integer,BubbleView> getBubbleMap(){
		return bubbles;
	}
	
	public Map<Integer,FixedEntityView> getObjMap(){
		return objects;
	}
	
	/**
	 * method that adds an enemy e in the map, using is id as the key and the corresponding view as value 
	 * @param e
	 */
	public void updateEnemyMap(Enemy e) {
		if(e instanceof Benzo) {
			enemies.put(e.getId(), new BenzoView());
		}
		else if(e instanceof Boris) {
			enemies.put(e.getId(), new BorisView());
		}
		else if(e instanceof Blubba) {
			enemies.put(e.getId(), new BlubbaView());
		}
	}
	
	/**
	 * method that adds a bubble b in the map, using is id as the key and the corresponding view as value 
	 * @param b
	 */
	public void updateBubbleMap(Bubble b) {
		if(b instanceof NormalBubble) {
			bubbles.put(b.getId(), new NormalBubbleView());
		}
		else if(b instanceof PlayerBubble) {
			bubbles.put(b.getId(), new PlayerBubbleView(this));
		}
		else if(b instanceof EnemyBubble) {
			bubbles.put(b.getId(), new EnemyBubbleView());
		}
	}
	
	/**
	 * method that adds a powerup or an item in the map, using is id as the key and the corresponding view as value
	 * @param ent
	 */
	public void updateObjMap(FixedEntity ent) {
		if(ent instanceof Item) objects.put(((Item)ent).getId(), new ItemView());
		if(ent instanceof PowerUp) objects.put(((PowerUp)ent).getId(), new PowerUpView());
	}
	
	/**
	 * method that initializes the entity panel and the level panel, passing to the latter the matrix containing the cells to be drawn and the level
	 * @param c
	 * @param l
	 */
	public void setField(Cell[][] c, int l) {
		loadingCompleted = false;
		entPanel = new EntityPanel(playerView,this);
		levelField = new LevelPanel(c,l);
		levelField.add(entPanel);
		add(levelField, BorderLayout.CENTER);
		loadingCompleted = true;
	}
	
	public boolean isLoadingCompleted() {
		return loadingCompleted;
	}
	
	public JButton getBackButton() {
		return button;
	}
	
	/**
	 * method that updates the number of lives displayed in the view
	 * @param num
	 */
	public void updateLives(int num) {
		currentLives.removeAll();
		currentLives.setText("0"+num);
	}
	
	/**
	 * method that updates the score in the game displayed in the view
	 * @param num
	 */
	public void updateScore(int num) {
		currentScore.removeAll();
		currentScore.setText(num>=1000000? num+"":(num>=100000? "0"+num:(num>=10000? "00"+num:(num>=1000? "000"+num:(num>=100? "0000"+num:(num>=10?"00000"+num:"000000"+num))))));
	}
}
