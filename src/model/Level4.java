package model;

/**
 * class that represents the fourth level of the game
 * it's implemented using the singleton pattern
 */
public class Level4 extends Level {
	private static Level4 instance;
	private static final int LEVEL_NUMBER_4 = 4;
	
	/**
	 * private constructor
	 */
	private Level4() {
		initLevel();
		initLevel(LEVEL_NUMBER_4);
	}
	
	public static Level4 getInstance() {
		if(instance==null) instance = new Level4();
		return instance;
	}

	@Override
	protected void initLevel(int number) {
		for(int k=1; k<HEIGHT-3;k++) {
			if(k==3) continue;
			for(int l=1;l<WIDTH-1;l++) {
				switch(k) {
				case 1:{
					if(l==5 || l==19) field[k][l]=new Block(k,l);
					break;
				}
				case 2:{
					if((l>4 && l<8) || (l>16 && l<20)) field[k][l] = new Block(k,l);
					break;
				}
				case 4:{
					if((l>2 && l<11) || (l>13 && l<22)) field[k][l] = new Block(k,l);
					break;
				}
				case 5:{
					if(l==3 || l==9 || l==10 || l==14 || l==15 || l==21) field[k][l] =new Block(k,l);
					break;
				}
				case 6:{
					if((l>2 && l<7) || l==9 || l==10 || l==14 || l==15 || (l<22 && l>17)) field[k][l] = new Block(k,l);
					break;
				}
				case 7:
				case 8:{
					if(l==9 || l==10 || l==14 || l==15) field[k][l] = new Block(k,l);
					break;
				}
				case 9:{
					if((l>2 && l<7) || (l<22 && l>17)) field[k][l] = new Block(k,l);
					break;
				}
				case 10:
				case 11:{
					if(l==3 || l==21) field[k][l] = new Block(k,l);
					break;
				}
				case 12:{
					if((l>2 && l<9) || (l>10 && l<14) || (l>15 && l<22)) field[k][l] = new Block(k,l);
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
			newEnemy((Enemy)EntityFactory.createEntity("Benzo",1,6,this));
			newEnemy((Enemy)EntityFactory.createEntity("Benzo",1,18,this));
			newEnemy((Enemy)EntityFactory.createEntity("Benzo",5,4,this));
			newEnemy((Enemy)EntityFactory.createEntity("Benzo",5,20,this));
			newEnemy((Enemy)EntityFactory.createEntity("Benzo",11,6,this));
			newEnemy((Enemy)EntityFactory.createEntity("Benzo",11,18,this));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}