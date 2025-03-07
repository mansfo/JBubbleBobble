package model;

/**
 * class that represents a block in a level
 */
public class Block extends Cell {

	/**
	 * constructor
	 * @param i
	 * @param j
	 */
	public Block(int i, int j) {
		super(i, j);
	}
	
	@Override
	public String toString() {
		return "#";
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null || o.getClass() != this.getClass()) return false;
		Block b = (Block)o;
		return b.getI()==getI() && b.getJ()==getJ();
	}
	
	@Override
	public int hashCode() {
		int hash = 17;
		hash = 31*hash + ((Integer)getI()).hashCode();
		hash = 31*hash + ((Integer)getJ()).hashCode();
		return hash;
	}
}
