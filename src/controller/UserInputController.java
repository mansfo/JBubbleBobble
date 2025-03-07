package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;

import model.Direction;
import model.Player;

/**
 * class that handles the input from the keyboard
 */
public class UserInputController implements KeyListener{

	private boolean isPressed;
	private Player player;
	private HashSet<Integer> pressed;
	
	/**
	 * constructor
	 */
	public UserInputController() {
		player = Player.getInstance();
		//this.gc = gc;
		pressed = new HashSet<>();
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	/**
	 * method that handles the event of a key pressed, if it's the down arrow it's called the player's method attack, else it adds the key in a set
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_DOWN && !isPressed) {
			player.attack();
			isPressed = true;
		}
		else {
			pressed.add(key);
			movePlayer();
		}
	}

	/**
	 * method that removes the key from the set when it is released
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_DOWN) isPressed = false;
		else {
			pressed.remove(key);
			movePlayer();
		}
	}
	
	/**
	 * method that checks if the set contains one of the three arrows, if so the player moves in that direction
	 */
	private void movePlayer() {
		if(pressed.contains(KeyEvent.VK_UP)) player.move(Direction.UP);
		if(pressed.contains(KeyEvent.VK_LEFT)) player.move(Direction.LEFT);
		if(pressed.contains(KeyEvent.VK_RIGHT)) player.move(Direction.RIGHT);
	}

}
