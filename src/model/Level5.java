package model;

/**
 * class that represents the fifth level of the game
 * it's implemented using the singleton pattern
 */
public class Level5 extends Level {
	private static Level5 instance;
	private static final int LEVEL_NUMBER_5 = 5;
	
	/**
	 * private constructor
	 */
	private Level5() {
		initLevel();
		initLevel(LEVEL_NUMBER_5);
	}
	
	public static Level5 getInstance() {
		if(instance==null) instance = new Level5();
		return instance;
	}

	@Override
	protected void initLevel(int number) {
		for(int k=3; k<HEIGHT-3;k+=3) {
			for(int l=4;l<WIDTH-4;l++) {
				switch(k) {
				case 3:
				case 9:{
					if(l==5) continue;
					if(l==4 || l==WIDTH-5) field[k-1][l] = new Block(k-1,l);
					field[k][l] = new Block(k,l);
					break;
				}
				case 6:{
					if(l==WIDTH-6) continue;
					if(l==4 || l==WIDTH-5) field[k-1][l] = new Block(k-1,l);
					field[k][l] = new Block(k,l);
					break;
				}
				case 12:{
					if(l==4 || l==WIDTH-5) field[k-1][l] = new Block(k-1,l);
					if(l<11 || l>13) field[k][l] = new Block(k,l);
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
			newEnemy((Enemy)EntityFactory.createEntity("Benzo",2,18,this));
			newEnemy((Enemy)EntityFactory.createEntity("Boris",5,15,this));
			newEnemy((Enemy)EntityFactory.createEntity("Boris",8,12,this));
			newEnemy((Enemy)EntityFactory.createEntity("Benzo",11,18,this));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
