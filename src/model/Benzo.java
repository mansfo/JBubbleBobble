package model;

import java.util.Timer;
import java.util.TimerTask;
import constants.Constants;

/**
 * class that represents the enemies of type Benzo
 */
public class Benzo extends Enemy{
	
	private static final int KILLING_POINTS = 10000;
	private double preciseI, preciseJ;
	private Level level;
	
	/**
	 * constructor: i,j are the coordinates in the level
	 * @param i
	 * @param j
	 * @param level
	 */
	public Benzo(int i, int j, Level level) {
		super(i, j, level);
		this.level = level;
		preciseI = i;
		preciseJ = j;
		if(getId()%2==0) setFacing(Facing.LEFT);
		else setFacing(Facing.RIGHT);
		updateObservers();
	}
	
	public static int getKillPoints() {
		return KILLING_POINTS;
	}

	/**
	 * method that updates the preciseI and the preciseJ coordinates of the enemy: these values are used to display the enemy in the view 
	 */
	@Override
	public void move() {
		if(getFacing() == Facing.RIGHT) {
			if (preciseJ<Constants.WIDTH-3) preciseJ += 0.03;
			else {
				if(randomInt(getId()) < 5 && ((getI()>2 && level.isWalkable(getI()-3, getJ())) || (getI()>1 && level.isWalkable(getI()-2, getJ())) || (getI()>0 && level.isWalkable(getI()-1, getJ())))) move(Direction.UP);
				else setFacing(Facing.LEFT);
			}
		}
		else {
			if(preciseJ>0) preciseJ -= 0.03;
			else {
				if(randomInt(getId()) < 5 && ((getI()>2 && level.isWalkable(getI()-3, getJ())) || (getI()>1 && level.isWalkable(getI()-2, getJ())) || (getI()>0 && level.isWalkable(getI()-1, getJ())))) move(Direction.UP);
				else setFacing(Facing.RIGHT);
			}
		}
		
		if((randomInt(getId()) % 3 == 0) && ((getI()>2 && level.isWalkable(getI()-3, getJ())) || (getI()>1 && level.isWalkable(getI()-2, getJ())) || (getI()>0 && level.isWalkable(getI()-1, getJ())))) move(Direction.UP);
		if(preciseJ-getJ() > 0) move(Direction.RIGHT);
		if(getJ()-preciseJ > 1.3) move(Direction.LEFT);
		updateObservers();
	}
	
	/**
	 * method used to move up in the level the enemy
	 */
	public void moveUP(int startingH) {
		Timer t = new Timer();
		t.schedule(new TimerTask() {

			@Override
			public void run() {
				preciseI -= 0.01;
				if(Math.abs(preciseI-getI())>1) setI(getI()-1);
				updateObservers();
				if(Math.abs(getI()-startingH)<3) moveUP(startingH);
				else move(Direction.DOWN);
			}
			
		}, 10);
	}
	
	/**
	 * method that updates the i,j coordinates of the enemy
	 */
	@Override
	public void move(Direction dir) {
		switch(dir) {
		case UP:{
			if(level.isWalkable(getI(), getJ())) {
				moveUP(getI());
			}
			break;
		}
		case LEFT:{
			setFacing(Facing.LEFT);
			if(level.isWalkable(getI(), getJ()-1)) setJ(getJ()-1);
			else if(level.isFree(getI(), getJ()-1)) {
				setJ(getJ()-1);
				move(Direction.DOWN);
			}
			else {
				this.setFacing(Facing.RIGHT);
			}
			break;
		}
		case RIGHT:{
			this.setFacing(Facing.RIGHT);
			if(level.isWalkable(getI(), getJ()+1)) setJ(getJ()+1);
			else if(level.isFree(getI(), getJ()+1)) {
				setJ(getJ()+1);
				move(Direction.DOWN);
			}
			else {
				this.setFacing(Facing.LEFT);
			}
			break;
		}
		case DOWN:{
			if(inTheAir()) {
				Timer t = new Timer();
				t.schedule(new TimerTask() {

					@Override
					public void run() {
						preciseI += 0.01;
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
	public String toString() {
		return "benzo";
	}

	@Override
	public boolean equals(Object o) {
		if(o==null || !this.getClass().equals(o.getClass())) return false;
		Benzo b = (Benzo)o;
		return b.getId() == this.getId();
	}
	
	@Override
	public int hashCode() {
		int hash = 17;
		hash = 31*hash + ((Integer)getI()).hashCode();
		hash = 31*hash + ((Integer)getJ()).hashCode();
		hash = 31*hash + ((Integer)getId()).hashCode();
		return hash;
	}
}
