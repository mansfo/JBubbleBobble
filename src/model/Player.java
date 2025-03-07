package model;

import java.time.Instant;
import java.time.temporal.ChronoField;
import java.util.Timer;
import java.util.TimerTask;

import constants.Constants;

/**
 * class that represents the player in the model;
 * it's implemented using the singleton pattern
 */
public class Player extends MovingEntity {

	private static Player instance;
	public static final int I_SPAWN = Constants.I_SPAWN, J_SPAWN = Constants.J_SPAWN;
	private Facing currentlyFacing;
	private int lives;
	private int points;
	private Level level;
	private double speed;
	private double preciseI, preciseJ;
	private boolean blueTrophy;
	private boolean redRing;
	private boolean purpleRing;
	private boolean blueRing;
	private int bubbleMaxDist;
	private boolean umbrella;
	private double bubbleSpeed;
	private String username;
	private String avatar;
	private Direction lastDir;
	private int nextLevel;
	private long spawnTime;
	private long lastAttackTime;
	private int startCount;
	
	/**
	 * private constructor
	 */
	private Player() {
		super(I_SPAWN, J_SPAWN);
		setPoints();
		lives = Constants.STARTING_LIVES;
		preciseI = (double) Constants.I_SPAWN;
		preciseJ = (double) Constants.J_SPAWN;
		lastDir = Direction.RIGHT;
		spawnTime = Instant.now().getLong(ChronoField.INSTANT_SECONDS);
		speed = Constants.STARTING_SPEED;
		bubbleSpeed = Constants.STARTING_BUBBLE_SPEED;
		bubbleMaxDist = Constants.STARTING_BUBBLE_MAX_DIST;
		username = Constants.DEFAULT_USERNAME;
		avatar = Constants.DEFAULT_AVATAR;
		currentlyFacing = Facing.RIGHT;
		startCount = 1;
		resetPowerUps();
		updateObservers();
	}
	
	/**
	 * public static method to access the single instance of player
	 * @return the instance
	 */
	public static Player getInstance() {
		if(instance==null) instance=new Player();
		return instance;
	}
	
	/**
	 * method that checks if the player was spawned less than three seconds ago: if so, the player will be invincible until three seconds will be passed
	 */
	public boolean isJustSpawned() {
		return Instant.now().getLong(ChronoField.INSTANT_SECONDS)-spawnTime <= 3L;
	}

	public void setLevel(Level level) {
		this.level = level;
		if(level.getLoadingNext()) level.setLoadingNext();
		preciseI = (double) Constants.I_SPAWN;
		preciseJ = (double) Constants.J_SPAWN;
		setI((int)preciseI);
		setJ((int)preciseJ);
		updateObservers();
	}
	
	/**
	 * method that resets the instance of the player
	 */
	public void reset() {
		setPoints();
		preciseI = (double) Constants.I_SPAWN;
		preciseJ = (double) Constants.J_SPAWN;
		lastDir = Direction.RIGHT;
		spawnTime = Instant.now().getLong(ChronoField.INSTANT_SECONDS);
		lives = Constants.STARTING_LIVES;
		startCount++;
		resetPowerUps();
		updateObservers();
	}
	
	public int getResets() {
		return startCount;
	}
	
	public Level getLevel() {
		return level;
	}
	
	public Facing getFacing() {
		return currentlyFacing;
	}
	
	public void setFacing(Facing f) {
		currentlyFacing = f;
		updateObservers();
	}

	@Override
	public String toString() {
		return "P";
	}
	
	public Direction getLastDir() {
		return lastDir;
	}
	
	/**
	 * auxiliary method that moves up in the level the player
	 * @param startingHeight
	 */
	public void moveUP(int startingHeight) {
		Timer t = new Timer();
		t.schedule(new TimerTask() {

			@Override
			public void run() {
				if(getI()==0) {
					move(Direction.DOWN);
					return;
				}
				preciseI -= 0.1;
				if(Math.abs(preciseI-getI())>1) setI(getI()-1);
				updateObservers();
				if(Math.abs(getI()-startingHeight)<3) moveUP(startingHeight);
				else move(Direction.DOWN);
			}
			
		}, 1);
	}

	/**
	 * method that updates the precise coordinates and the actual coordinates of the player
	 */
	@Override
	public void move(Direction dir) { 
		if(getI() == Level.HEIGHT-2 && blueTrophy) setPoints(100); 
		if(blueRing) setPoints(1);
		lastDir = dir;
		switch(dir) {
		case UP:{
			if(level.isWalkable(getI(), getJ())) {
				if(purpleRing) setPoints(500);
				moveUP(getI());
			}
			break;
		}
		case LEFT:{
			this.setFacing(Facing.LEFT);
			if(preciseJ>0.5 && level.isWalkable(getI(), getJ())) {
				preciseJ -= 0.2*speed;
				if(getJ()-preciseJ > 1.3) setJ(getJ()-1);
				if(!level.isWalkable(getI(), getJ()-1) && level.isFree(getI(), getJ()-1)) move(Direction.DOWN);
			}
			break;
		}
		case RIGHT:{
			this.setFacing(Facing.RIGHT);
			if(preciseJ<Constants.WIDTH-3 && level.isWalkable(getI(), getJ())) {
				preciseJ += 0.2*speed;
				if(preciseJ-getJ() > 0) setJ(getJ()+1);
				if(!level.isWalkable(getI(), getJ()+1)) move(Direction.DOWN);
			}
			break;
		}
		case DOWN:{
			if(!level.isWalkable(getI(),getJ())) {
				Timer t = new Timer();
				t.schedule(new TimerTask() {

					@Override
					public void run() {
						preciseI += 0.05;
						if(Math.abs(preciseI-getI())>1) setI(getI()+1);
						if(getI()>=Level.HEIGHT) {
							setI(0);
							preciseI = 0.5;
						}
						updateObservers();
						move(Direction.DOWN);
					}
					
				}, 1);
			}
			break;
		}
		}
		updateObservers();
	}
	
	public boolean getUmbrella() {
		return umbrella;
	}
	
	public void setUmbrella(boolean b) {
		umbrella = b;
	}
	
	public double getPreciseI() {
		return preciseI;
	}
	
	public double getPreciseJ() {
		return preciseJ;
	}
	
	/**
	 * method that decreases the lives of the player after a collision with an enemy, resets the player's position and the player's powerups
	 */
	public void decreaseLives() {
		preciseI = I_SPAWN;
		preciseJ = J_SPAWN+0.5;
		setI(I_SPAWN);
		setJ(J_SPAWN);
		resetPowerUps();
		lives--;
		spawnTime = Instant.now().getLong(ChronoField.INSTANT_SECONDS);
		updateObservers();
	}
	
	/**
	 * method that resets the effects of the powerups on the player
	 */
	public void resetPowerUps() {
		nextLevel = 0;
		setSpeed(Constants.STARTING_SPEED);
		setBubbleSpeed(Constants.STARTING_BUBBLE_SPEED);
		setBubbleMaxDist(Constants.STARTING_BUBBLE_MAX_DIST);
		blueTrophy = false;
		blueRing = false;
		redRing = false;
		umbrella = false;
		purpleRing = false;
	}
	
	public void setBubbleSpeed(double s) {
		bubbleSpeed = s;
	}
	
	public void setBubbleMaxDist(int d) {
		bubbleMaxDist = d;
	}
	
	public int getBubbleMaxDist() {
		return bubbleMaxDist;
	}
	
	public double getBubbleSpeed() {
		return bubbleSpeed;
	}
	
	public void setBlueTrophy(){
		blueTrophy = true;
	}
	
	public void setRedRing() {
		redRing = true;
	}
	
	public void setPurpleRing() {
		purpleRing = true;
	}
	
	public void setBlueRing() {
		blueRing = true;
	}
	
	public int getPoints() {
		return points;
	}
	
	public void setPoints() {
		points = 0;
	}
	
	public void setPoints(int p) {
		points += p;
	}
	
	public int getLives() {
		return lives;
	}
	
	public void skipNext() {
		nextLevel = 1;
		umbrella = true;
	}
	
	public int getSkipNext() {
		return nextLevel;
	}
	
	public void resetSkipNext() {
		nextLevel = 0;
	}
 
	/**
	 * method that is called when the player attacks, it spawns a new player-bubble that will move in the direction where the player is looking
	 * If after the last attack occurred less than one second ago, it won't be created a new player-bubble 
	 */
	public void attack() {
		if(Instant.now().getLong(ChronoField.INSTANT_SECONDS)-lastAttackTime<1) return;
		if(redRing) {
			setPoints(100);
		}
		try {
			PlayerBubble pb = (PlayerBubble) EntityFactory.createEntity("playerbubble",getI(),getJ(),level);
			level.newBubble(pb);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		lastAttackTime = Instant.now().getLong(ChronoField.INSTANT_SECONDS);
		updateObservers("attack");
	}
	
	public void setSpeed(int s) {
		speed = s;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String s) {
		username = s;
	}
	
	public String getAvatar() {
		return avatar;
	}
	
	public void setAvatar(String path) {
		avatar = path;
	}
}
