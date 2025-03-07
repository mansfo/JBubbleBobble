package model;

/**
 * class that represents the second level of the game
 * it's implemented using the singleton pattern
 */
public class Level2 extends Level {
	private static Level2 instance;
	private static final int LEVEL_NUMBER_2 = 2;
	
	/**
	 * private constructor
	 */
	private Level2() {
		initLevel();
		initLevel(LEVEL_NUMBER_2);
	}
	
	public static Level2 getInstance() {
		if(instance==null) instance = new Level2();
		return instance;
	}

	@Override
	protected void initLevel(int number) {
		for(int k=6;k<9;k++) {
			field[0][k] = new Block(0,k);
			field[HEIGHT-1][k] = new Block(HEIGHT-1,k);
		}
		for(int k=16;k<19;k++) {
			field[0][k] = new Block(0,k);
			field[HEIGHT-1][k] = new Block(HEIGHT-1,k);
		}
		for(int k=10;k<15;k++) field[3][k]=new Block(3,k);
		for(int k=8;k<17;k++) field[6][k]=new Block(6,k);
		for(int k=6;k<19;k++) if(k!=11 && k!=12 && k!=13) field[9][k] = new Block(9,k);
		for(int k=4;k<10;k++) field[12][k]=new Block(12,k);
		for(int k=15;k<21;k++) field[12][k]=new Block(12,k);
			
	}
	
	@Override
	public void addLevelEnemies() {
		try {
			newEnemy((Enemy)EntityFactory.createEntity("Benzo",2,10,this));
			newEnemy((Enemy)EntityFactory.createEntity("Benzo",2,14,this));
			newEnemy((Enemy)EntityFactory.createEntity("Benzo",5,8,this));
			newEnemy((Enemy)EntityFactory.createEntity("Benzo",5,16,this));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
