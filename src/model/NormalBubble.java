package model;

/**
 * class that represents a normal bubble, that is spawned when a bubble blown by the player
 * reaches its maximum distance without colliding with an enemy
 */
public class NormalBubble extends Bubble {

	private double preciseI, preciseJ;
	private Level level;
	
	/**
	 * constructor
	 * @param i
	 * @param j
	 * @param level
	 */
	public NormalBubble(int i, int j, Level level) {
		super(i, j, level);
		this.level = level;
		preciseI = i;
		preciseJ = j;
	}
	
	@Override
	public double getPreciseI() {
		return preciseI;
	}
	
	@Override
	public double getPreciseJ() {
		return preciseJ;
	}

	/**
	 * method that updates the precise coordinates and the actual coordinates of the bubble
	 */
	@Override
	public void move() {
		if(getJ()>1 && ((getJ() < (Level.WIDTH-1)/2 && level.isFree(getI(), getJ()-1) && getI()>= 4) ||
				getI()<4 && (getJ() >= (Level.WIDTH-1)/2) && level.isFree(getI(), getJ()-1))) preciseJ -= 0.01;
		else if(getJ()<Level.WIDTH-3 && (getJ() >= (Level.WIDTH-1)/2 && level.isFree(getI(), getJ()+1) && getI() >= 4) ||
				(getI()<4 && (getJ() < (Level.WIDTH-1)/2) && level.isFree(getI(), getJ()+1))) preciseJ += 0.01;
		else if (getI()>1) preciseI -= 0.005;
		else preciseI += 0.005;
		
		if(Math.abs(preciseJ-getJ())>1) {
			if(preciseJ>getJ()) move(Direction.RIGHT);
			else move(Direction.LEFT);
		}
		if(Math.abs(preciseI-getI())>1) {
			if(preciseI>getI()) move(Direction.DOWN);
			else move(Direction.UP);
		}
		
		updateObservers();
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null || o.getClass()!=this.getClass()) return false;
		NormalBubble nb = (NormalBubble)o;
		return nb.getId() == this.getId();
	}
	
	@Override
	public int hashCode() {
		int hash = 17;
		hash = 31*hash + ((Integer)getId()).hashCode();
		return hash;
	}

}
