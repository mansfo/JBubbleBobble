package model;

import java.util.Observable;

/**
 * abstract class that represents a generic entity in the game
 */
@SuppressWarnings("deprecation")
public abstract class Entity extends Observable{
	/**
	 * i,j are the coordinates of the entity in the level, they are used to represent the actual position of the entity in the logical 
	 * scheme of the level and to check for example if two entities collided
	 */
	private int i, j;
	
	/**
	 * constructor
	 * @param i, the i coordinate
	 * @param j, the j coordinate
	 */
	public Entity(int i, int j) {
		this.i=i;
		this.j=j;
	}
	
	/**
	 * getter for the i coordinate
	 * @return i
	 */
	public int getI() {
		return i;
	}
	
	/**
	 * getter for the j coordinate
	 * @return j
	 */
	public int getJ() {
		return j;
	}
	
	public void setI(int newI) {
		i = newI;
	}
	
	public void setJ(int newJ) {
		j = newJ;
	}
	

	/**
	 * method that given a seed returns a random int in [0,9]
	 * @param seed
	 * @return an integer in [0,9]
	 */
	public int randomInt(int seed) {
		return (int) ((System.nanoTime()+seed)*37) % 10;
	}
	
	/**
	 * method that given a seed returns a random int in [0,99]
	 * @param seed
	 * @return an integer in [0,99]
	 */
	public int randomInt100(int seed) {
		return (int) ((System.nanoTime()+seed)*37) % 100;
	}
	
	/**
	 * method that update the observers associated with the entity
	 */
	public void updateObservers() {
		setChanged();
		notifyObservers();
	}
	
	/**
	 * method that update the observers associated with the entity, adding a description of the event
	 */
	public void updateObservers(Object description) {
		setChanged();
		notifyObservers(description);
	}
}
