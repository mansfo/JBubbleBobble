package model;

/**
 * class that represents the first level of the game
 * it's implemented using the singleton pattern
 */
public class Level1 extends Level {
	private static Level1 instance;
	private static final int LEVEL_NUMBER_1 = 1;
	
	/**
	 * private constructor
	 */
	private Level1() {
		initLevel();
		initLevel(LEVEL_NUMBER_1);
	}
	
	public static Level1 getInstance() {
		if(instance==null) instance = new Level1();
		return instance;
	}
	
	@Override
	protected void initLevel(int num) {
		for(int k=6;k<9;k++) {
			field[0][k] = new Block(0,k);
			field[HEIGHT-1][k] = new Block(HEIGHT-1,k);
		}
		for(int k=16;k<19;k++) {
			field[0][k] = new Block(0,k);
			field[HEIGHT-1][k] = new Block(HEIGHT-1,k);
		}
		for(int k=HEIGHT-4; k>4; k-=3) {
			field[k][1] = new Block(k,1);
			field[k][2] = new Block(k,2);
			field[k][WIDTH-2] = new Block(k,WIDTH-2);
			field[k][WIDTH-3] = new Block(k,WIDTH-3);
			for(int l=8; l<WIDTH-8; l++) 
				field[k][l]=new Block(k,l);
		}
		//addLevelEnemies();
	}
	
	@Override
	public void addLevelEnemies() {
		try {
			newEnemy((Enemy)EntityFactory.createEntity("Benzo",5,10,this));
			newEnemy((Enemy)EntityFactory.createEntity("Benzo",5,12,this));
			newEnemy((Enemy)EntityFactory.createEntity("Benzo",5,14,this));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
