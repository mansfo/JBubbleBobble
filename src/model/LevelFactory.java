package model;

/**
 * class that implements the factory pattern for the levels of the game,
 * the level is selected by a number in input, if no level matches the number a custom exception is thrown
 */
public class LevelFactory {
	
	public static Level createLevel(int number) throws IllegalLevelNumberException {
		Level level = null;
		switch(number) {
		case(1)->level = Level1.getInstance();
		case(2)->level = Level2.getInstance();
		case(3)->level = Level3.getInstance();
		case(4)->level = Level4.getInstance();
		case(5)->level = Level5.getInstance();
		case(6)->level = Level6.getInstance();
		case(7)->level = Level7.getInstance();
		case(8)->level = Level8.getInstance();
		default->throw new IllegalLevelNumberException();
		}
		return level;
	}
}
