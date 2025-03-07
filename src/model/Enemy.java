package model;

/**
 * abstract class that represents a generic enemy in the game
 */
public abstract class Enemy extends MovingEntity {

	private EnemyState state;
	private Facing currentlyFacing;
	private Level level;
	private int id;
	private boolean observed;
	
	/**
	 * constructor
	 * @param i
	 * @param j
	 * @param level
	 */
	public Enemy(int i, int j, Level level) {
		super(i, j);
		this.level = level;
		id = level.getEnemyLastId();
		level.setEnemyLastId();
		state =  EnemyState.ALIVE;
	}
	
	/**
	 * enum that represents the current state of the enemy
	 */
	public enum EnemyState {
		DEAD,
		ALIVE;
	}
	
	/**
	 * method that checks if an instance of this class has an observer by checking the boolean value observed: this value will be
	 * set to true by the controller when an observer is associated with the instance
	 * @return the value of observed
	 */
	public boolean isObserved() {
		return observed;
	}
	
	public void observed() {
		observed = true;
	}
	
	/**
	 * method that checks if the enemy is on the floor or not
	 */
	public boolean inTheAir() {
		return !level.isWalkable(getI(), getJ());
	}
	
	public int getId() {
		return id;
	}
	
	public EnemyState getState() {
		return state;
	}
	
	public void setState(EnemyState s) {
		state = s;
		updateObservers();
	}
	
	public Facing getFacing() {
		return currentlyFacing;
	}
	
	public void setFacing(Facing f) {
		currentlyFacing = f;
		updateObservers();
	}
	
	public abstract void move();
	
	@Override
	public abstract void move(Direction dir);
	
	public abstract double getPreciseI();
	
	public abstract double getPreciseJ();
	
	@Override
	public abstract String toString();
}
