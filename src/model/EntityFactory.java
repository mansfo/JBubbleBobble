package model;

/**
 * class that implements the factory pattern for the entities in the model
 * It implements a static method with two overloadings to handle every possible case.
 * The entity that will be created is selected by an input string, if no entity matches the string, 
 * a custom exception will be thrown
 */
public class EntityFactory {
	public static Entity createEntity(String e, int k, int l) throws IllegalEntityNameException {
		Entity ent = null;
		switch(e.toLowerCase()) {
		case("player")->ent = Player.getInstance();
		default->throw new IllegalEntityNameException();
		}
		return ent;
	}
	
	public static Entity createEntity(String e, int k, int l, Level n) throws IllegalEntityNameException {
		Entity ent = null;
		switch(e.toLowerCase()) {
		case("benzo")->ent = new Benzo(k,l,n);
		case("boris")->ent = new Boris(k,l,n);
		case("blubba")->ent = new Blubba(k,l,n);
		case("item")->ent = new Item(k,l,n);
		case("normalbubble")->ent = new NormalBubble(k,l,n);
		case("playerbubble")->ent = new PlayerBubble(k,l,n);
		case("powerup")->ent = new PowerUp(k,l,n);
		default->ent = createEntity(e,k,l);
		}
		return ent;
	}
	
	public static Entity createEntity(String e, int k, int l, Level n, String enemy) throws IllegalEntityNameException {
		Entity ent = null;
		switch(e.toLowerCase()) {
		case("enemybubble")->ent = new EnemyBubble(k,l,n,enemy);
		default->ent = createEntity(e,k,l,n);
		}
		return ent;
	}
}
