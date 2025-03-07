package model;

/**
 * abstract class that represents a generic cell of the level
 */
public abstract class Cell extends FixedEntity {
	
	/**
	 * constructor
	 * @param i
	 * @param j
	 */
	public Cell(int i, int j) {
		super(i,j);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append(getI());
		sb.append(", ");
		sb.append(getJ());
		sb.append(")");
		return sb.toString();
	}
}
