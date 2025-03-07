package model;

/**
 * class that represents the items in the model
 */
public class Item extends FixedEntity {
	
	/**
	 * enum that represents the Item type with the corresponding value in points
	 */
	public enum ItemType{
		BANANA(300), WATERMELON(800), CRYSTAL(2500), DIAMOND(4000);
		
		private int points;
		
		/**
		 * enum constructor
		 * @param points
		 */
		ItemType(int points) {
			this.points = points;
		}
		
		public int getPoints() {
			return points;
		}
	}
	
	private ItemType type;
	private boolean observed;
	private int id;
	private boolean collected;
	
	/**
	 * constructor
	 * @param i
	 * @param j
	 * @param level
	 */
	public Item(int i, int j, Level level) {
		super(i, j);
		id = level.getObjLastId();
		level.setObjLastId();
		int t = randomInt100((int)(Math.random()*100));
		if(t<=70) type = ItemType.BANANA;
		else if(t>70 && t<90) type = ItemType.WATERMELON;
		else if(t>=90 && t<=96) type = ItemType.CRYSTAL;
		else type = ItemType.DIAMOND;
		
		updateObservers(toString());
	}
	
	public void collect() {
		collected = true;
		updateObservers();
	}
	
	/**
	 * method that checks if the item was collected yet
	 * @return the boolean value collected, that is set to true by the controller when the player collides with the item 
	 */
	public boolean isCollected() {
		return collected;
	}
	
	public int getPoints() {
		return type.getPoints();
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

	public int getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return type.toString();
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
