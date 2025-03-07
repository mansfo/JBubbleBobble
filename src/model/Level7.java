package model;

/**
 * class that represents the seventh level of the game
 * it's implemented using the singleton pattern
 */
public class Level7 extends Level {
	private static Level7 instance;
	private static final int LEVEL_NUMBER_7 = 7;
	
	/**
	 * private constructor
	 */
	private Level7() {
		initLevel();
		initLevel(LEVEL_NUMBER_7);
	}
	
	public static Level7 getInstance() {
		if(instance==null) instance = new Level7();
		return instance;
	}

	@Override
	protected void initLevel(int number) {
		for(int k=3; k<HEIGHT-3;k+=3) {
			for(int l=1;l<WIDTH-1;l++) {
				switch(k) {
				case 3:{
					if(l<5 || l>19) field[k][l]=new Block(k,l);
					break;
				}
				case 6:{
					if(l<7 || l>17) field[k][l]=new Block(k,l);
					break;
				}
				case 9:{
					if(l<9 || l>15) field[k][l]=new Block(k,l);
					break;
				}
				case 12:{
					if((l>2 && l<11) || (l>13 && l<22)) field[k][l]=new Block(k,l);
					break;
				}
				default: break;
				}
			}
		}
	}

	@Override
	public void addLevelEnemies() {
		try {
			newEnemy((Enemy)EntityFactory.createEntity("Boris",2,2,this));
			newEnemy((Enemy)EntityFactory.createEntity("Boris",2,22,this));
			newEnemy((Enemy)EntityFactory.createEntity("Boris",5,4,this));
			newEnemy((Enemy)EntityFactory.createEntity("Boris",5,20,this));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
