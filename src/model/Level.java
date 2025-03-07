package model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;
import constants.Constants;
import model.Bubble.BubbleState;
import model.Enemy.EnemyState;

/**
 * class that represents a generic level in the game
 */
public abstract class Level{
	protected final static int HEIGHT = Constants.HEIGHT;
	protected final static int WIDTH = Constants.WIDTH;
	protected Cell[][] field;
	private HashSet<Enemy> aliveEnemies;
	private HashSet<Bubble> bubbles;
	private HashSet<Bubble> bubblesToInsert;
	private HashSet<PowerUp> powerups;
	private HashSet<Item> items;
	private Player player;
	private int enemyLastId;
	private int bubbleLastId;
	private int objLastId;
	private boolean loadingNext;
	
	/**
	 * method that initializes the level, building the common structure among every level
	 */
	protected void initLevel() {
		aliveEnemies = new HashSet<>();
		bubbles = new HashSet<>();
		bubblesToInsert = new HashSet<>();
		items = new HashSet<>();
		powerups = new HashSet<>();
		try {
			player = (Player) EntityFactory.createEntity("player", Player.I_SPAWN, Player.J_SPAWN);
		} catch (IllegalEntityNameException e) {
			e.printStackTrace();
		}
		field = new Cell[HEIGHT][WIDTH];
		try {
		for(int k=0; k<WIDTH; k++) {
			if(k<6||(k>8 && k<16)||k>18) {
				field[0][k] = new Block(0,k);
				field[HEIGHT-1][k] = new Block(HEIGHT-1,k);
			} else {
				field[0][k] = new Space(0,k);
				field[HEIGHT-1][k] = new Space(HEIGHT-1,k);
			}
		}
		for(int k=0; k<HEIGHT; k++) {
			field[k][0] = new Block(k,0);
			field[k][WIDTH-1] = new Block(k,WIDTH-1);
		}
		for(int k=1;k<HEIGHT-1;k++) {
			for(int l=1;l<WIDTH-1;l++)
				field[k][l]= new Space(k,l);
		}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	protected abstract void initLevel(int number);
	
	public abstract void addLevelEnemies();
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int k=0;k<HEIGHT;k++) {
			for(int l=0;l<WIDTH;l++) {
				if(isWalkable(k,l)) sb.append("W");
				else sb.append(field[k][l]);
				}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	public Cell[][] getLevelField(){
		return field;
	}
	
	public boolean getLoadingNext() {
		return loadingNext;
	}
	
	public void setLoadingNext() {
		loadingNext = !loadingNext;
	}
	
	public int countAliveEnemies() {
		return (int) aliveEnemies.stream().filter(x->x.getState()!=EnemyState.DEAD).count();
	}
	
	public int countBubbleEnemies() {
		return (int) bubbles.stream().filter(x->x instanceof EnemyBubble && x.isFloating()).count();
	}
	
	public HashSet<Enemy> getAliveEnemies(){
		return aliveEnemies;
	}
	
	public HashSet<Bubble> getBubbles(){
		return bubbles;
	}
	
	public HashSet<Item> getItems(){
		return items;
	}
	
	public void newEnemy(Enemy en) {
		aliveEnemies.add(en);
	}
	
	/**
	 * method that returns the last id assigned to an enemy
	 */
	public int getEnemyLastId() {
		return enemyLastId;
	}
	
	public void setEnemyLastId() {
		enemyLastId++;
	}
	
	public void newBubble(Bubble b) {
		bubblesToInsert.add(b);
	}
	
	public void removeBubble(Bubble b) {
		bubbles.remove(b);
	}
	
	public void mergeBubbleSets() {
		bubbles.addAll(bubblesToInsert);
	}
	
	public HashSet<Bubble> getBubblesToInsert() {
		return bubblesToInsert;
	}
	
	/**
	 * method that returns the last id assigned to a bubble
	 */
	public int getBubbleLastId() {
		return bubbleLastId;
	}
	
	public void setBubbleLastId() {
		bubbleLastId++;
	}
	
	/**
	 * method that returns the last id assigned to a powerup or to an item
	 */
	public int getObjLastId() {
		return objLastId;
	}
	
	public void setObjLastId() {
		objLastId++;
	}
	
	public HashSet<PowerUp> getPowerUps(){
		return powerups;
	}
	
	/**
	 * method that selects a random cell that doesn't contain anything and spawns in there a random powerup
	 */
	public void newPowerUp() {
		List<Cell> spawnable = getFreeCells().stream()
				.filter(x->isWalkable(x.getI(), x.getJ()) && !containsObject(x.getI(), x.getJ()) && x.getJ()<WIDTH-2)
				.toList();
		if(spawnable.size()>0) {
			Cell spawnCell = spawnable.get(Math.abs(new Random().nextInt(spawnable.size())));
			try {
				powerups.add((PowerUp) EntityFactory.createEntity("Powerup", spawnCell.getI(), spawnCell.getJ(), this));
			} catch (IllegalEntityNameException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * method that given two coordinates k,l spawns in there a random item
	 * @param k
	 * @param l
	 */
	public void spawnItem(int k, int l) {
		try {
			items.add((Item) EntityFactory.createEntity("Item",k,l,this));
		} catch (IllegalEntityNameException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * method that checks if the cell with coordinates k,l contains an enemy
	 * @param k
	 * @param l
	 */
	public boolean containsEnemy(int k, int l){ 
		return aliveEnemies.stream()
				.filter(x->x.getI()==k && x.getJ()==l)
				.count()>0;
	}
	
	/**
	 * method that checks if the cell with coordinates k,l contains a powerup or an item
	 * @param k
	 * @param l
	 */
	public boolean containsObject(int k, int l) {
		return (items.stream()
				.filter(x->x.getI()==k && x.getJ()==l)
				.count()>0) || (powerups.stream()
						.filter(x->x.getI()==k && x.getJ()==l)
						.count()>0);
	}
	
	/**
	 * method that checks if the cell with coordinates k,l contains a bubble blown by the player
	 * @param k
	 * @param l
	 */
	public boolean containsPlayerBB(int k, int l) {
		return bubbles.stream()
				.filter(x->x instanceof PlayerBubble && x.getI()==k && x.getJ()==l)
				.count()>0;
	}
	
	/**
	 * method that checks if an enemy has collided with a bubble blown by the player: if so, the enemy's state is set to DEAD,
	 * the bubble state is set to DESTROYED and a new enemy bubble is spawned
	 * @param e: the enemy
	 */
	public void enemyBubbleCollision(Enemy e) {
		Bubble[] bb = bubbles.toArray(new Bubble[bubbles.size()+10]);
		for(int k=0;k<bb.length;k++) {
			if(bb[k] == null || bb[k].getState()==BubbleState.DESTROYED || !(bb[k] instanceof PlayerBubble)) continue;
			if(Math.abs(bb[k].getPreciseI()-e.getPreciseI())<0.5 && Math.abs(bb[k].getPreciseJ()-e.getPreciseJ())<0.5 && e.getState()!=EnemyState.DEAD) {
				bb[k].updateState();
				e.setState(EnemyState.DEAD);
				try {
					newBubble((EnemyBubble) EntityFactory.createEntity("EnemyBubble", e.getI(), e.getJ(), this, e.toString()));
				}
				catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}					
	
	/**
	 * method that checks if the player collided with an enemy, if so it's called the method that decreases the player's lives
	 */
	public void enemyPlayerCollision() {
		if(aliveEnemies.stream()
				.filter(x->Math.abs(x.getPreciseI()-player.getPreciseI())<0.5 && Math.abs(x.getPreciseJ()-player.getPreciseJ())<0.5 && x.getState() != EnemyState.DEAD)
				.count()>0) player.decreaseLives();
	}
	
	/**
	 * method that checks if the player collided with a bubble: if so, the bubble state is set to DESTROYED
	 */
	public void bubblePlayerCollision() {
		if(player.getLastDir() == Direction.UP || player.getLastDir() == Direction.DOWN) {
			HashSet<Integer> idHit = (HashSet<Integer>) bubbles.stream().filter(x->Math.abs(x.getPreciseI()-player.getPreciseI())<0.5 &&
					Math.abs(x.getPreciseJ()-player.getPreciseJ())<0.5 && x.isFloating() && !(x instanceof PlayerBubble)).map(x->x.getId()).collect(Collectors.toSet());
			for(Bubble b: bubbles) {
				if(player.getUmbrella()) b.updateState();
				if(idHit.contains(b.getId())) {
					b.updateState();
					if(b instanceof EnemyBubble) {
						player.setPoints(((EnemyBubble)b).getKillPoints());
						Timer t = new Timer();
						t.schedule(new TimerTask() {

							@Override
							public void run() {
								spawnItem(HEIGHT-2,b.getJ());
							}
							
						}, 1000);
					}
					else player.setPoints(100);
				}
			}
		}
	}
	
	/**
	 * method that checks if the player collided with an item: if so, the item is collected
	 */
	public void objPlayerCollision() {
		List<Item> it = items.stream()
				.filter(x->Math.abs(x.getI()-player.getPreciseI())<0.5 && Math.abs(x.getJ() - player.getPreciseJ())<0.5 && !x.isCollected())
				.toList();
		if(it.size()>0) {
			Item item = it.getFirst();
			player.setPoints(item.getPoints());
			item.collect();
		}
		else checkPowerUp();
	}
	
	/**
	 * method that checks if the player collided with a powerup: if so, the powerup is collected and its effects are assigned to the player by the
	 * private method takePowerUp
	 */
	public void checkPowerUp() {
		List<PowerUp> pup = powerups.stream()
				.filter(x->Math.abs(x.getI()-player.getPreciseI())<0.5 && Math.abs(x.getJ() - player.getPreciseJ())<0.5 && !x.isCollected())
				.toList();
		if(pup.size()>0) takePowerUp(pup.getFirst());
	}
	
	private void takePowerUp(PowerUp p) {
		player.setPoints(p.getPoints());
		p.collect();
		switch(p.getPowerUpType().toInt()) {
		case 1 -> player.setBubbleMaxDist(10);
		case 2 -> player.setBubbleSpeed(3);
		case 3 -> player.setSpeed(2);
		case 4 -> {
			player.setBubbleMaxDist(8);
			player.setBubbleSpeed(2);
			player.setSpeed(2);
		}
		case 5 -> player.setBlueTrophy();
		case 6 -> player.setRedRing();
		case 7 -> player.setPurpleRing();
		case 8 -> player.setBlueRing();
		case 9 -> player.setUmbrella(true);
		case 10 ->player.skipNext();
		default ->{}
		}
	}
	
	/**
	 * @param k
	 * @param l
	 * @return the cell in position k,l
	 */
	protected Cell getCell(int k, int l) {
		return field[k][l];
	}
	
	/**
	 * 
	 * @return an hashset containing all the cells that are not blocks
	 */
	public HashSet<Cell> getFreeCells(){
		HashSet<Cell> result = (HashSet<Cell>) Arrays.stream(field)
				.flatMap(x->Arrays.stream(x))
				.filter(e->!(e instanceof Block))
				.collect(Collectors.toSet());
		return result;
	}
	
	/**
	 * check if the cell in position k,l is a block or not
	 * @param k
	 * @param l
	 */
	public boolean isFree(int k, int l) {
		return !(getCell(k,l) instanceof Block);
	}
	
	/**
	 * checks if the cell in position k,l is not a block and the cell in position k+1,l is a block
	 * @param k
	 * @param l
	 */
	public boolean isWalkable(int k, int l) {
		if(k>=HEIGHT-1) return false;
		return !(getCell(k,l) instanceof Block) && getCell(k+1,l) instanceof Block;
	}
}
