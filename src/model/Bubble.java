package model;

/**
 * abstract class that represents a generic bubble in the game
 */
public abstract class Bubble extends MovingEntity {
	
	private BubbleState state;
	private int id;
	private boolean observed;
	
	/**
	 * constructor
	 * @param i
	 * @param j
	 * @param level
	 */
	public Bubble(int i, int j, Level level) {
		super(i, j);
		state = BubbleState.FLOATING;
		id = level.getBubbleLastId();
		level.setBubbleLastId();
	}
	
	/**
	 * enum that describes the current state of a bubble
	 */
	public enum BubbleState{
		FLOATING,
		DESTROYED;
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
	
	public abstract double getPreciseI();
	
	public abstract double getPreciseJ();
	
	public abstract void move();
	
	/**
	 * method that updates the i,j coordinates of the enemy
	 */
	@Override
	public void move(Direction d) {
		switch(d) {
		case Direction.UP: {
			setI(getI()-1);
			break;
		}
		case Direction.LEFT: {
			setJ(getJ()-1);
			break;
		}
		case Direction.RIGHT: {
			setJ(getJ()+1);
			break;
		}
		case Direction.DOWN: {
			setI(getI()+1);
			break;
		}
		}
	}
	
	/**
	 * method that checks if the bubble is destroyed or not
	 * @return
	 */
	public boolean isFloating() {
		return state == BubbleState.FLOATING;
	}
	
	public int getId() {
		return id;
	}
	
	public BubbleState getState() {
		return state;
	}
	
	/**
	 * method that, after a collision between the bubble and an enemy or the player, sets the state to DESTROYED
	 */
	public void updateState() {
		state = BubbleState.DESTROYED;
		updateObservers();
	}
}
