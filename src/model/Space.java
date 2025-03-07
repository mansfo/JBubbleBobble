package model;

/**
 * class that represents a cell that doesn't contain a block
 */
public class Space extends Cell{

	/**
	 * constructor
	 * @param i
	 * @param j
	 */
	public Space(int i, int j) {
		super(i, j);
	}

	@Override
	public String toString() {
		return " ";
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null || o.getClass() != this.getClass()) return false;
		Space s = (Space)o;
		return s.getI()==getI() && s.getJ()==getJ();
	}
	
	@Override
	public int hashCode() {
		int hash = 17;
		hash = 31*hash + ((Integer)getI()).hashCode();
		hash = 31*hash + ((Integer)getJ()).hashCode();
		return hash;
	}
}
