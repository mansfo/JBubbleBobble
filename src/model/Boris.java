package model;

import java.util.Timer;
import java.util.TimerTask;
import constants.Constants;

/**
 * class that represents the enemies of type Boris
 */
public class Boris extends Enemy{

	private static final int KILLING_POINTS = 30000;
	private double preciseI, preciseJ;
	private Level level;
	
	/**
	 * constructor
	 * @param i
	 * @param j
	 * @param level
	 */
	public Boris(int i, int j, Level level) {
		super(i, j, level);
		this.level = level;
		preciseI = i;
		preciseJ = j;
		updateObservers();
	}
	
	@Override
	public String toString() {
		return "boris";
	}
	
	public static int getKillingPoints() {
		return KILLING_POINTS;
	}

	/**
	 * method that updates the preciseI and the preciseJ coordinates of the enemy: these values are used to display the enemy in the view 
	 */
	@Override
	public void move() {
		if(getFacing()==Facing.LEFT) {
			if(preciseJ>0) preciseJ -= 0.08;
			else setFacing(Facing.RIGHT);
		}
		else {
			if (preciseJ<Constants.WIDTH-3) preciseJ += 0.08;
			else setFacing(Facing.LEFT);
		}
		
		if(preciseJ-getJ() > 0) move(Direction.RIGHT);
		if(getJ()-preciseJ > 1.3) move(Direction.LEFT);
		updateObservers();
	}
	
	/**
	 * method that updates the i,j coordinates of the enemy
	 */
	@Override
	public void move(Direction dir) {
		switch(dir) {
		case LEFT:{
			setFacing(Facing.LEFT);
			if(level.isWalkable(getI(), getJ()-1)) setJ(getJ()-1);
			else if(level.isFree(getI(), getJ()-1)) {
				setJ(getJ()-1);
				move(Direction.DOWN);
			}
			else this.setFacing(Facing.RIGHT);
			break;
		}
		case RIGHT:{
			this.setFacing(Facing.RIGHT);
			if(level.isWalkable(getI(), getJ()+1)) setJ(getJ()+1);
			else if(level.isFree(getI(), getJ()+1)) {
				setJ(getJ()+1);
				move(Direction.DOWN);
			}
			else this.setFacing(Facing.LEFT);
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
		default: break;
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
	public boolean equals(Object o) {
		if(o==null || !this.getClass().equals(o.getClass())) return false;
		Boris b = (Boris)o;
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