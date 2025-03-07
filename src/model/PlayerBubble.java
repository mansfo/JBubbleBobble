package model;

/**
 * class that represents the bubbles blown by the player in the model
 */
public class PlayerBubble extends Bubble{

	private Player player;
	private double preciseJ;
	private Facing direction;
	private int spawnJ;
	private Level level;
	
	/**
	 * constructor
	 * @param i
	 * @param j
	 * @param level
	 */
	public PlayerBubble(int i, int j, Level level) {
		super(i, j, level);
		try {
			player = (Player) EntityFactory.createEntity("player", i, j);
		} catch (IllegalEntityNameException e) {
			e.printStackTrace();
		}
		this.level = level;
		spawnJ =  player.getFacing()==Facing.RIGHT? j+1:j-1;
		preciseJ = spawnJ;
		direction = player.getFacing();
	}

	@Override
	public double getPreciseI() {
		return (double) getI();
	}
	
	@Override
	public double getPreciseJ() {
		return preciseJ;
	}

	/**
	 * method that updates the precise and the actual coordinates of the bubble
	 */
	@Override
	public void move() {
		if(getState() == BubbleState.DESTROYED) return;
		if(Math.abs(getJ()-spawnJ)<player.getBubbleMaxDist() && level.isFree(getI(),getJ()) && level.isFree(getI(),getJ()+1)) {
			if(direction == Facing.RIGHT) preciseJ += 0.01*player.getBubbleSpeed();
			else preciseJ -= 0.01*player.getBubbleSpeed();
			if(Math.abs(getJ()-preciseJ)>1) setJ(getJ()>preciseJ? getJ()-1:getJ()+1);
		}
		else {
			updateState();
			try {
				level.newBubble((NormalBubble) EntityFactory.createEntity("normalbubble", getI(), getJ()-1, level));
			} catch (IllegalEntityNameException e) {
				e.printStackTrace();
			}
			level.removeBubble(this);
		}
		
		updateObservers();
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null || o.getClass()!=this.getClass()) return false;
		PlayerBubble pb = (PlayerBubble)o;
		return pb.getId() == this.getId();
	}
	
	@Override
	public int hashCode() {
		int hash = 17;
		hash = 31*hash + ((Integer)super.getI()).hashCode();
		hash = 31*hash + ((Integer)super.getJ()).hashCode();
		return hash;
	}
}
