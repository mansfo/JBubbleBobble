package controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import constants.Constants;
import controller.GameController.GameState;

/**
 * class that implements the main loop of the application
 */
public class LoopController implements Runnable {

	private GameController gc;
	private boolean isRunning = false;
	private ExecutorService executorService;
	
	/**
	 * constructor
	 * @param gc
	 */
	public LoopController(GameController gc) {
		this.gc = gc;
	}
	
	/**
	 * method that implements the loop
	 */
	@Override
	public void run() {
		
		long currentTime = System.nanoTime();
		long updateTimeNanos = System.nanoTime();
		while(isRunning && !Thread.interrupted()) {
			currentTime = System.nanoTime();
			if(currentTime-updateTimeNanos > Constants.UPDATE_TIME) {
				gc.updateGame();
				updateTimeNanos = currentTime;
			}
			if (gc.getGameState() != GameState.PLAYING) stopLoop();
		}
	}
	
	/**
	 * starts the loop
	 */
	public void initLoop() {
		isRunning = true;
		executorService = Executors.newSingleThreadExecutor();
		executorService.execute(this);
	}
	
	/**
	 * stops the loop
	 */
	public void stopLoop() {
		if(executorService != null) {
			isRunning = false;
			executorService.shutdown();
		}
	}

}
