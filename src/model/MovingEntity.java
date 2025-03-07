package model;

/**
 * abstract class that represents a generic entity that can move in the game
 */
public abstract class MovingEntity extends Entity implements Movable {

	/**
	 * constructor
	 * @param i
	 * @param j
	 */
	public MovingEntity(int i, int j) {
		super(i, j);
	}
	
	/**
	 * enum that represents the direction where the entity is looking
	 */
	public enum Facing{
		RIGHT,
		LEFT;
	}
}
