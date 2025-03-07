package model;

/**
 * class that represents the sixth level of the game
 * it's implemented using the singleton pattern
 */
public class Level6 extends Level {
	private static Level6 instance;
	private static final int LEVEL_NUMBER_6 = 6;
	
	/**
	 * private constructor
	 */
	private Level6() {
		initLevel();
		initLevel(LEVEL_NUMBER_6);
	}
	
	public static Level6 getInstance() {
		if(instance==null) instance = new Level6();
		return instance;
	}

	@Override
	protected void initLevel(int number) {
		for(int k=3; k<HEIGHT-3;k+=3) {
			for(int l=1;l<WIDTH-1;l++) {
				switch(k) {
				case 3:
				case 9:{
					if(l>4) field[k][l]=new Block(k,l);
					break;
				}
				case 6:{
					if(l<20) field[k][l]=new Block(k,l);
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
			newEnemy((Enemy)EntityFactory.createEntity("Boris",2,22,this));
			newEnemy((Enemy)EntityFactory.createEntity("Benzo",5,2,this));
			newEnemy((Enemy)EntityFactory.createEntity("Benzo",8,22,this));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}