package model;

/**
 * class that implements the powerups in the model
 */
public class PowerUp extends FixedEntity {

	/**
	 * enum that represents the possible powerups
	 */
	public enum PowerUpType{
		PURPLE_GUM(1), BLUE_GUM(2), RED_SHOE(3), RED_TROPHY(4), BLUE_TROPHY(5), RED_RING(6), PURPLE_RING(7), BLUE_RING(8), UMBRELLA(9), RED_UMBRELLA(10);

		private int num;
		
		/**
		 * enum constructor
		 * @param num
		 */
		PowerUpType(int num) {
			this.num = num;
		}
		
		public int toInt() {
			return num;
		}
	}
	
	private PowerUpType pType;
	private int id;
	private boolean observed;
	private boolean collected;
	
	/**
	 * constructor: the powerup created is selected randomly with different probabilities
	 * @param i
	 * @param j
	 * @param level
	 */
	public PowerUp(int i, int j, Level level) {
		super(i,j);
		id = level.getObjLastId();
		level.setObjLastId();
		int pwr = randomInt100((int)(id*System.nanoTime() % 1000));
		if (pwr > 98) pType = PowerUpType.RED_UMBRELLA;
		else if (pwr>= 97 && pwr<= 98) pType = PowerUpType.UMBRELLA;
		else if (pwr<97 && pwr>=90) pType = PowerUpType.BLUE_RING;
		else if (pwr>80 && pwr<90) pType = PowerUpType.PURPLE_RING;
		else if (pwr>68 && pwr<=80) pType = PowerUpType.RED_RING;
		else if (pwr>56 && pwr<=68) pType = PowerUpType.BLUE_TROPHY;
		else if (pwr>44 && pwr<=56) pType = PowerUpType.RED_TROPHY;
		else if (pwr>30 && pwr<=44) pType = PowerUpType.RED_SHOE;
		else if (pwr>15 && pwr<=30) pType = PowerUpType.BLUE_GUM;
		else pType = PowerUpType.PURPLE_GUM;
	}
	
	/**
	 * method that returns the points associated to a powerup
	 * @return
	 */
	public int getPoints() {
		switch(pType.toInt()) {
		case 1:
		case 2:
		case 3: return 100;
		case 4:
		case 5: return 200;
		case 6:
		case 7:
		case 8: return 300;
		case 9: return 400;
		case 10: return 500;
		default: return 0;
		}
	}
	
	public PowerUpType getPowerUpType() {
		return pType;
	}
	
	public int getId() {
		return id;
	}
	
	/**
	 * method that checks if the powerup was collected yet
	 * @return the boolean value collected, that is set to true by the controller when the player collides with the powerup 
	 */
	public boolean isCollected() {
		return collected;
	}
	
	public void collect() {
		collected = true;
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
		updateObservers();
	}
	
	@Override
	public String toString() {
		return pType.toString();
	}
	
	@Override
	public boolean equals(Object o) {
		if(o==null || !this.getClass().equals(o.getClass())) return false;
		Item it = (Item)o;
		return it.getId() == this.getId();
	}
	
	@Override
	public int hashCode() {
		int hash = 17;
		hash = 31*hash + ((Integer)super.getI()).hashCode();
		hash = 31*hash + ((Integer)super.getJ()).hashCode();
		hash = 31*hash + ((Integer)getId()).hashCode();
		return hash;
	}
}