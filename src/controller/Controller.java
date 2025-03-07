package controller;

import view.BubbleBobbleFrame;
import view.BubbleBobbleFrame.PanelType;

/**
 * class that represents the main controller in the application
 */
public class Controller {
	
	private BubbleBobbleFrame view;
	private GameController game;
	private RankingController rank;
	private ButtonController buttons;
	
	/**
	 * constructor
	 * @param view
	 */
	public Controller(BubbleBobbleFrame view) {
		this.view = view;
		changeView(PanelType.MAIN_MENU);
		rank = new RankingController();
		game = new GameController(this);
		buttons = new ButtonController(this);
		buttons.initButtons();
		view.setController(this);
	}
	
	/**
	 * methods that select the panel to be showed in the user interface 
	 * @param p
	 */
	protected void changeView(PanelType p) {
		view.showView(p);
		view.repaint();
	}
	
	public void changeLevelView() {
		changeView(PanelType.LOAD);
		if(view.getGameView().getLevelPanel() != null && view.getGameView() != null) view.getGameView().remove(view.getGameView().getLevelPanel());
		view.getGameView().setField(game.getCurrentLevel().getLevelField(), game.getCurrentLevelNumber());
		view.getGameView().repaint();
		changeView(PanelType.GAME);
	}
	
//	public void setLevelView() {
//		view.getGameView().setField(game.getCurrentLevel().getLevelField(), game.getCurrentLevelNumber());
//		view.getGameView().repaint();
//	}
	
	/**
	 * method that returns the main frame of the user interface
	 * @return
	 */
	public BubbleBobbleFrame getView() {
		return view;
	}
	
	/**
	 * method that returns the game controller
	 * @return
	 */
	public GameController getGameController() {
		return game;
	}
	
	/**
	 * method that resets the game controller, it's used to reset everything in the game when a new game is started
	 */
	public void resetGame() {
		game.reset();
	}
	
	/**
	 * method that returns the controller of the ranking
	 * @return
	 */
	public RankingController getRankingController() {
		return rank;
	}
}
