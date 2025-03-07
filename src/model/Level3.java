package model;

/**
 * class that represents the third level of the game
 * it's implemented using the singleton pattern
 */
public class Level3 extends Level {
	private static Level3 instance;
	public static final int LEVEL_NUMBER_3 = 3;
	
	/**
	 * private constructor
	 */
	private Level3() {
		initLevel();
		initLevel(LEVEL_NUMBER_3);
	}
	
	public static Level3 getInstance() {
		if(instance==null) instance = new Level3();
		return instance;
	}

	@Override
	protected void initLevel(int number) {
		for(int k=3; k<HEIGHT-3;k++) {
			if(k==10 || k==11) continue;
			for(int l=1;l<WIDTH-1;l++) {
				switch(k) {
				case 3: {
					if((l>2 && l<9)||(l>15 && l<22)) field[k][l]=new Block(k,l);
					break;
				}
				case 4:
				case 5:
				case 7:
				case 8:{
					if(l==3 || l==21) field[k][l] = new Block(k,l);
					break;
				}
				case 6:{
					if((l>2 && l<10)||(l>14 && l<22)) field[k][l]=new Block(k,l);
					break;
				}
				case 9:{
					if((l>2 && l<11)||(l>13 && l<22)) field[k][l]=new Block(k,l);
					break;
				}
				case 12:{
					if(l<5||(l>6 && l<10)||(l>14 && l<18)||l>19) field[k][l] =new Block(k,l);
				}
				default: break;
				}
			}
		}
	}

	@Override
	public void addLevelEnemies() {
		try {
			newEnemy((Enemy)EntityFactory.createEntity("Benzo",2,7,this));
			newEnemy((Enemy)EntityFactory.createEntity("Benzo",2,17,this));
			newEnemy((Enemy)EntityFactory.createEntity("Benzo",5,4,this));
			newEnemy((Enemy)EntityFactory.createEntity("Benzo",5,20,this));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}