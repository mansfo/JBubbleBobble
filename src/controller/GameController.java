package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoField;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import constants.Constants;
import model.Bubble;
import model.Bubble.BubbleState;
import model.Enemy;
import model.Enemy.EnemyState;
import model.IllegalLevelNumberException;
import model.Item;
import model.Level;
import model.LevelFactory;
import model.Player;
import model.PowerUp;
import model.Profile;
import view.BubbleBobbleFrame.PanelType;

/**
 * class that represents the main controller of the game
 */
@SuppressWarnings("deprecation")
public class GameController {

	/**
	 * enum used to signal which is the state of the game
	 */
	public enum GameState{
		WON,
		LOST,
		PLAYING;
	}
	
	private int currentLevelNumber;
	private Level currentLevel;
	private static GameState gState;
	private Player player;
	private Controller controller;
	private UserInputController input;
	private LoopController loop;
	private Set<Enemy> enemySet;
	private long initTime;
	private boolean loading;
	
	/**
	 * constructor
	 * @param controller
	 */
	public GameController(Controller controller) {
		this.controller = controller;
		input = new UserInputController();
		loop = new LoopController(this);
		try {
			player = Player.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		player.addObserver(controller.getView().getGameView().getPlayerView());
		initGame();
	}
	
	/**
	 * private method that initializes a game, it's used either to start the first game and to restart a game after a quit, a win or a loss
	 */
	private void initGame() {
		gState = GameState.PLAYING;
		currentLevelNumber = 1;
		try {
			currentLevel = LevelFactory.createLevel(currentLevelNumber);
		} catch (Exception e) {
			e.printStackTrace();
		}
		player.setLevel(currentLevel);
		enemySet = new HashSet<Enemy>();
		setEnemySet();
	}
	
	/**
	 * method that saves in an HashSet the enemies in the level and adds an observer for each one of them
	 * The observer is the corresponding view of every enemy: every enemy is assigned to the correct observer through an hashmap
	 */
	public void setEnemySet() {
		controller.getView().getGameView().repaint();
		if(currentLevel.getAliveEnemies().size()==0) currentLevel.addLevelEnemies();
		enemySet = currentLevel.getAliveEnemies();
		for(Enemy e: enemySet) {
			e.observed();
			controller.getView().getGameView().updateEnemyMap(e);
		}
		for(Enemy e: enemySet) e.addObserver(controller.getView().getGameView().getEnemyMap().get(e.getId()));
		initTime = Instant.now().getLong(ChronoField.INSTANT_SECONDS);
		loading = false;
		controller.getView().getGameView().repaint();
	}
	
	/**
	 * method that resets the collections of the level, the player's stats and powerups and the hashmaps for the observers
	 */
	public void reset() {
		enemySet.clear();
		controller.getView().getGameView().getEnemyMap().clear();
		controller.getView().getGameView().getBubbleMap().clear();
		controller.getView().getGameView().getObjMap().clear();
		player.reset();
		initGame();
	}
	
	public UserInputController getInputController() {
		return input;
	}
	
	public void setUsername(String s) {
		player.setUsername(s);
	}
	
	public void setAvatar(String s) {
		player.setAvatar(s);
	}
	
	public Level getCurrentLevel() {
		return currentLevel;
	}
	
	public int getCurrentLevelNumber() {
		return currentLevelNumber;
	}
	
	public LoopController getLoop() {
		return loop;
	}
	
	/**
	 * method that is called by the loop controller to update the position on every entity in the game
	 */
	public void updateGame() {
		if(loading) return;
		updatePlayerPosition();
		updateEnemiesPosition();
		updateBubblePositions();
		updateItems();
		updatePowerUps();
		if(Instant.now().getLong(ChronoField.INSTANT_SECONDS)-initTime > 30L) {
			initTime = Instant.now().getLong(ChronoField.INSTANT_SECONDS);
			spawnPowerUp();
		}
		if(player.getLives()<0) loosing();
		if(currentLevel.countAliveEnemies()<1 && currentLevel.countBubbleEnemies()<1 && !currentLevel.getLoadingNext()) {
			currentLevel.setLoadingNext();
			if(currentLevelNumber+1 <= Constants.NUMBER_OF_LEVELS) nextLevel();
			else winning();
		}
	}
	
	/**
	 * method that for every enemy in the level, checks if that enemy has an observer, updates the position in the field and checks if that enemy
	 * has collided with a bubble blown by the player
	 */
	private void updateEnemiesPosition() {
		for(Enemy e: currentLevel.getAliveEnemies()) {
			if(e.getState()==EnemyState.DEAD) continue;
			if(player.getUmbrella()) e.setState(EnemyState.DEAD);
			if(!e.isObserved()) {
				e.observed();
				controller.getView().getGameView().updateEnemyMap(e);
				e.addObserver(controller.getView().getGameView().getEnemyMap().get(e.getId()));
			}
			Timer t = new Timer();
			t.schedule(new TimerTask() {
	
				@Override
				public void run() {
					e.move();
					currentLevel.enemyBubbleCollision(e);
				}
			}, 100);
		}
	}
	
	/**
	 * method that for every bubble in the level, checks if that bubble has an observer, updates the position of the bubble and in the end 
	 * adds to the collection the bubbles in an auxiliary set, used to prevent the ConcurrentModificationException in the main bubble set
	 */
	private void updateBubblePositions() {	
		Bubble[] bb = currentLevel.getBubbles().toArray(new Bubble[currentLevel.getBubbles().size()]);
		for(int k=0;k<bb.length;k++) {
			if(bb[k] == null || (bb[k].getState()==BubbleState.DESTROYED && bb[k].getI()==Constants.HEIGHT-1)) continue;
			if(!bb[k].isObserved()) {
				bb[k].observed();
				controller.getView().getGameView().updateBubbleMap(bb[k]);
				bb[k].addObserver(controller.getView().getGameView().getBubbleMap().get(bb[k].getId()));
			}
			bb[k].move();
		}
		currentLevel.mergeBubbleSets();
	}
	
	/**
	 * method that updates the player's position in the game and checks if the player collided with a bubble or with an enemy
	 */
	private void updatePlayerPosition() {
		if(!player.isJustSpawned()) currentLevel.enemyPlayerCollision();
		currentLevel.bubblePlayerCollision();
		currentLevel.objPlayerCollision();
	}
	
	/**
	 * method that checks if every item has an observer
	 */
	private void updateItems() {
		Item[] it = currentLevel.getItems().toArray(new Item[currentLevel.getItems().size()]);
		for(int k=0;k<it.length;k++) {
			if(it[k] == null) continue;
			if(!it[k].isObserved()) {
				it[k].observed();
				controller.getView().getGameView().updateObjMap(it[k]);
				it[k].addObserver(controller.getView().getGameView().getObjMap().get(it[k].getId()));
			}
			else it[k].updateObservers(it[k].toString());
		}
	}
	
	/**
	 * method that checks if every powerup has an observer
	 */
	private void updatePowerUps() {
		for(PowerUp p: currentLevel.getPowerUps()) {
			if(!p.isObserved()) {
				p.observed();
				controller.getView().getGameView().updateObjMap(p);
				p.addObserver(controller.getView().getGameView().getObjMap().get(p.getId()));
			}
			else p.updateObservers(p.toString());
		}
	}
	
	/**
	 * method that spawns a random powerup in a random cell
	 */
	private void spawnPowerUp() {
		currentLevel.newPowerUp();
	}
	
	/**
	 * method that is used to change the current level, it is called when the number of alive enemies and the number of enemybubbles are 0
	 */
	private void nextLevel(){
		int control = player.getResets();
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				try {
					if(control == player.getResets() && currentLevel.countAliveEnemies()<1 && currentLevel.countBubbleEnemies()<1) {
						loading = true;
						enemySet.clear();
						controller.getView().getGameView().getEnemyMap().clear();
						controller.getView().getGameView().getBubbleMap().clear();
						controller.getView().getGameView().getObjMap().clear();
						currentLevel = LevelFactory.createLevel(++currentLevelNumber+player.getSkipNext());
						player.setLevel(currentLevel);
						player.resetSkipNext();
						player.resetPowerUps();
						controller.changeLevelView();
						while(!controller.getView().getGameView().isLoadingCompleted());
						setEnemySet();
					}
				} catch (IllegalLevelNumberException e) {
					e.printStackTrace();
				}
			}
			
		}, 10000);	
	}
	
	public GameState getGameState() {
		return gState;
	}
	
	/**
	 * method that is used to end a game when the player won
	 */
	private void winning() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				gState = GameState.WON;
				collectPlayerStats();
				controller.changeView(PanelType.WIN);
			}
			
		}, 10000);	
	}
	
	/**
	 * method that is used to end the game when the player has lost
	 */
	private void loosing() {
		gState = GameState.LOST;
		collectPlayerStats();
		controller.changeView(PanelType.GAME_OVER);
	}
	
	/**
	 * method that collects the stats of the player after the end of the game and stores it in the file ranking.txt, 
	 * rewriting the row that contains the player's username if it's present or adding a new row otherwise
	 */
	private void collectPlayerStats() {
		String user = player.getUsername();
		LinkedList<String[]> fileRows = new LinkedList<>();
		boolean matched = false;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("../Ranking.txt"));
			while(br.ready()) {
				String line = br.readLine();
				if(line.contains(user)) {
					String[] found = line.split(" ");
					found[3] = ""+(Integer.parseInt(found[3])+1);
					if(gState==GameState.WON) found[4] = ""+(Integer.parseInt(found[4])+1);
					found[5] = ""+(Integer.parseInt(found[5])+player.getPoints());
					if(Integer.parseInt(found[6])<player.getPoints()) found[6] = ""+player.getPoints();
					found[2] = "" + (1 + (int) (Integer.parseInt(found[5]) / 100000));
					fileRows.add(found);
					matched = true;
				}
				else fileRows.add(line.split(" "));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if(br!=null) {
				try { br.close(); } catch(Exception e) { e.printStackTrace();}
			}
		}
		
		BufferedWriter bw = null;
		try {
			ListIterator<String[]> it = fileRows.listIterator();
			StringBuilder sb = new StringBuilder();
			while(it.hasNext()) {
				String[] line = it.next();
				for(int k=0; k<line.length; k++) {
					if(k!=0) sb.append(" ");
					sb.append(line[k]);
				}
				sb.append("\n");
			}
			if(!matched) {
				Profile playerProfile = new Profile(player.getUsername(), player.getAvatar(), 1+ (int) (player.getPoints()/100000), 
						1, gState==GameState.WON? 1:0, player.getPoints(), player.getPoints());
				sb.append(playerProfile.toString());
			}
			bw = new BufferedWriter(new FileWriter("../Ranking.txt"));
			bw.write(sb.toString(),0,sb.toString().length());
		} catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if(bw!=null) {
				try { bw.close(); } catch(Exception e) { e.printStackTrace();}
			}
		}
	}
}
