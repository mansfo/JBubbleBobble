package model;

import java.time.Instant;
import java.time.temporal.ChronoField;
import java.util.Timer;
import java.util.TimerTask;

/**
 * class that represents a bubble containing an enemy
 */
public class EnemyBubble extends Bubble {
	private long initTime;
	private String enemyInside;
	private Level level;
	private double preciseI, preciseJ;
	
	/**
	 * constructor
	 * @param i
	 * @param j
	 * @param level
	 * @param enemyInside
	 */
	public EnemyBubble(int i, int j, Level level, String enemyInside) {
		super(i,j, level);
		this.enemyInside = enemyInside;
		this.initTime = Instant.now().getLong(ChronoField.INSTANT_SECONDS);
		this.level = level;
		preciseI = i;
		preciseJ = j;
	}
	
	public int getKillPoints() {
		if(enemyInside.toLowerCase().equals("benzo")) return Benzo.getKillPoints();
		else if(enemyInside.toLowerCase().equals("boris")) return Boris.getKillingPoints();
		else return Blubba.getKillingPoints();
	}
	
	/**
	 * method that updates the precise coordinates of the bubble
	 */
	@Override
	public void move() {
		if(!isFloating()) {
			Timer t = new Timer();
			t.schedule(new TimerTask() {
				@Override
				public void run() {
					if(getI()<Level.HEIGHT-2) {
						preciseI += 0.01;
						if(Math.abs(preciseI-getI())>1) move(Direction.DOWN);
						move();
					}
				}		
			}, 20);
			if(getI()>=Level.HEIGHT-2) { 
				level.getBubbles().remove(this);
				return;
			}
		}
		else {
			if(getJ() > 1 && ((getJ() < (Level.WIDTH-1)/2 && level.isFree(getI(), getJ()-1) && getI()>= 4) ||
					getI()<4 && (getJ() >= (Level.WIDTH-1)/2) && level.isFree(getI(), getJ()-1))) preciseJ -= 0.01;
			else if(getJ() < Level.WIDTH-3 && ((getJ() >= (Level.WIDTH-1)/2 && level.isFree(getI(), getJ()+1) && getI() >= 4) ||
					(getI()<4 && (getJ() < (Level.WIDTH-1)/2) && level.isFree(getI(), getJ()+1)))) preciseJ += 0.01;
			else if (getI()>1) preciseI -= 0.01;
			else preciseI += 0.01;
			
			if(Math.abs(preciseJ-getJ())>1) {
				if(preciseJ>getJ()) move(Direction.RIGHT);
				else move(Direction.LEFT);
			}
			if(Math.abs(preciseI-getI())>1) {
				if(preciseI>getI()) move(Direction.DOWN);
				else move(Direction.UP);
			}
		}
		updateObservers(enemyInside);
	}
	
	/**
	 * method that counts how many seconds before the invocation the bubble was created
	 * @return the time elapsed in seconds
	 */
	public long checkElapsedTime() {
		long time = Instant.now().getLong(ChronoField.INSTANT_SECONDS) - initTime;
		if(time > 30L && getState() != BubbleState.DESTROYED) {
			try {
				level.newEnemy((Enemy) EntityFactory.createEntity(enemyInside, getI(), getJ(), level));
			} catch (IllegalEntityNameException e) {
				e.printStackTrace();
			}
			updateState();
			level.removeBubble(this);
		}
		return time;
	}
	
	@Override
	public double getPreciseI() {
		return preciseI;
	}
	
	@Override
	public double getPreciseJ() {
		return preciseJ;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o==null || !this.getClass().equals(o.getClass())) return false;
		EnemyBubble b = (EnemyBubble)o;
		return b.getId() == this.getId();
	}
	
	@Override
	public int hashCode() {
		int hash = 17;
		hash = 31*hash + ((Integer)getId()).hashCode();
		return hash;
	}
}
