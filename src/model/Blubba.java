package model;

import constants.Constants;

/**
 * class that represents the enemies of type Blubba
 */
public class Blubba extends Enemy{
	
	private static final int KILLING_POINTS = 50000;
	private double preciseI, preciseJ;
	private Level level;
	private boolean goingUp;
	
	/**
	 * constructor
	 * @param i
	 * @param j
	 * @param level
	 */
	public Blubba(int i, int j, Level level) {
		super(i, j, level);
		preciseI = i;
		preciseJ = j;
		this.level = level;
		goingUp = System.nanoTime() % 2 == 0;
		if(getId()%2==0) setFacing(Facing.LEFT);
		else setFacing(Facing.RIGHT);
		updateObservers();
	}

	@Override
	public String toString() {
		return "Blubba";
	}
	
	public static int getKillingPoints() {
		return KILLING_POINTS;
	}
	
	public boolean isGoingUp() {
		return goingUp;
	}

	/**
	 * method that updates the preciseI and the preciseJ coordinates of the enemy: these values are used to display the enemy in the view 
	 */
	@Override
	public void move() {
		if(getFacing()==Facing.LEFT) {
			if(preciseJ>0) {
				preciseJ -= 0.05;
				if(goingUp) { 
					if(preciseI > 0) preciseI -= 0.05;
					else goingUp = false;
				}
				else {
					if(preciseI < Level.HEIGHT-1) preciseI += 0.05;
					else goingUp = true;
				}
			}
			else setFacing(Facing.RIGHT);
		}
		else {
			if (preciseJ<Constants.WIDTH-3) {
				preciseJ += 0.05;
				if(goingUp) { 
					if(preciseI > 0) preciseI -= 0.05;
					else goingUp = false;
				}
				else {
					if(preciseI < Level.HEIGHT-1) preciseI += 0.05;
					else goingUp = true;
				}
			}
			else setFacing(Facing.LEFT);
		}
		
		if(preciseJ-getJ() > -0.3) move(Direction.RIGHT);
		if(getJ()-preciseJ > 1) move(Direction.LEFT);
		if(preciseI-getI()> 0) move(Direction.DOWN);
		if(getI()-preciseI > 1) move(Direction.UP);
		updateObservers();
	}
	
	/**
	 * method that updates the i,j coordinates of the enemy
	 */
	public void move(Direction dir) {
		switch(dir) {
		case UP->{
			if(level.getCell(getI()-1,getJ()) instanceof Block) goingUp = false;
			else {
				setI(getI()-1);
				if(getI()<=0) {
					setI(Level.HEIGHT-1);
					preciseI = Level.HEIGHT-1.5;
					goingUp = true;
				}
			}
		}
		case LEFT->{
			if(level.getCell(getI(),getJ()-1) instanceof Block) setFacing(Facing.RIGHT);
			else setJ(getJ()-1);
		}
		case RIGHT->{
			if(level.getCell(getI(),getJ()+1) instanceof Block) setFacing(Facing.LEFT);
			else setJ(getJ()+1);
		}
		case DOWN->{
			if(level.getCell(getI()+1,getJ()) instanceof Block) goingUp = true;
			else {
				setI(getI()+1);
				if(getI()>=Level.HEIGHT-1){
					setI(0);
					preciseI = 0.5;
					goingUp = false;
				}
			}
		}
		}
		updateObservers();
	}
	
	@Override
	public boolean equals(Object o) {
		if(o==null || !this.getClass().equals(o.getClass())) return false;
		Blubba b = (Blubba)o;
		return b.getId() == this.getId();
	}
	
	@Override
	public int hashCode() {
		int hash = 17;
		hash = 31*hash + ((Integer)super.getI()).hashCode();
		hash = 31*hash + ((Integer)super.getJ()).hashCode();
		hash = 31*hash + ((Integer)getId()).hashCode();
		return hash;
	}

	@Override
	public double getPreciseI() {
		return preciseI;
	}

	@Override
	public double getPreciseJ() {
		return preciseJ;
	}

}
