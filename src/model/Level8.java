package model;

/**
 * class that represents the eighth level of the game
 * it's implemented using the singleton pattern
 */
public class Level8 extends Level {
	private static Level8 instance;
	private static final int LEVEL_NUMBER_8 = 8;
	
	/**
	 * private constructor
	 */
	private Level8() {
		initLevel();
		initLevel(LEVEL_NUMBER_8);
	}
	
	public static Level8 getInstance() {
		if(instance==null) instance = new Level8();
		return instance;
	}

	@Override
	protected void initLevel(int number) {
		for(int k=2; k<HEIGHT-3;k++) {
			if(k==11 || k==9) continue;
			for(int l=1;l<WIDTH-1;l++) {
				switch(k) {
				case 2:
				case 3:
				case 5:
				case 6:
				case 8:{
					if(l==4 || l==20) field[k][l] = new Block(k,l);
					break;
				}
				case 4:
				case 7:{
					if(l==4 || l==6 || l==7 || l==17 || l==18 || l==20) field[k][l] =new Block(k,l);
					break;
				}
				case 10:{
					if(l>5 && l<19) field[k][l] =new Block(k,l);
					break;
				}
				case 12:{
					if(l!=5 && l!=6 && l!=9 && l!=10 && l!=14 && l!=15 && l!=18 && l!=19) field[k][l] =new Block(k,l);
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
			newEnemy((Enemy)EntityFactory.createEntity("Blubba",3,6,this));
			newEnemy((Enemy)EntityFactory.createEntity("Blubba",3,18,this));
			newEnemy((Enemy)EntityFactory.createEntity("Blubba",6,7,this));
			newEnemy((Enemy)EntityFactory.createEntity("Blubba",6,17,this));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
