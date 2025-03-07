package controller;

import java.util.List;

import controller.RankingController.SortingParam;
import model.Profile;
import view.BubbleBobbleFrame.PanelType;

/**
 * class that manages the actions performed by the buttons in the user interface
 */
public class ButtonController {
	
	private Controller controller;
	private int sortButtonClicked;
	private int avatarButtonClicked;
	
	/**
	 * Constructor
	 * @param controller
	 */
	public ButtonController(Controller controller) {
		this.controller = controller;
	}
	
	/**
	 * method that assigns the actions to the buttons
	 */
	public void initButtons() {
		/**
		 * buttons in the main menu
		 */
		controller.getView().getMainMenuView().getStartButton().addActionListener(e->{
			controller.resetGame();
			controller.changeLevelView();
			controller.changeView(PanelType.GAME);
			controller.getView().getInitialPanel().addKeyListener(controller.getGameController().getInputController());
			controller.getGameController().getLoop().initLoop();
		});
		
		controller.getView().getMainMenuView().getRankingButton().addActionListener(e->{
			sortButtonClicked = 0;
			List<Profile> ranking = controller.getRankingController().getRanking();
			controller.getView().getRankingView().loadRanking(ranking);
			controller.changeView(PanelType.RANKING);
		});
		
		controller.getView().getMainMenuView().getAvatarButton().addActionListener(e->{
			controller.getView().getMainMenuView().setAvatar(++avatarButtonClicked % 5);
			controller.getGameController().setAvatar("../resources/avatars/"+(avatarButtonClicked % 5)+".png");
		});
		
		controller.getView().getMainMenuView().getUsernameButton().addActionListener(e->{
			controller.getGameController().setUsername(controller.getView().getMainMenuView().getUsernameField().getText().strip().length()>4?
					controller.getView().getMainMenuView().getUsernameField().getText().strip().substring(0,4):controller.getView().getMainMenuView().getUsernameField().getText().strip());
		});
		
		/**
		 * buttons in the ranking page
		 */
		
		controller.getView().getRankingView().getSortingButton().addActionListener(e->{
			List<Profile> ranking = controller.getRankingController().getSortedRanking(SortingParam.getParam(++sortButtonClicked % 6));
			controller.getView().getRankingView().loadRanking(ranking);
			controller.getView().getRankingView().setCurrentParam(SortingParam.getParam(sortButtonClicked));
			controller.changeView(PanelType.LOAD);
			controller.changeView(PanelType.RANKING);
		});
		
		controller.getView().getRankingView().getBackButton().addActionListener(e->{
			controller.changeView(PanelType.MAIN_MENU);
		});
		
		/**
		 * button to stop a game
		 */
		controller.getView().getGameView().getBackButton().addActionListener(e->{
			controller.getGameController().getLoop().stopLoop();
			controller.changeView(PanelType.MAIN_MENU);
		});
		
		/**
		 * buttons in the game over page
		 */
		controller.getView().getGameOverView().getBackButton().addActionListener(e->{
			controller.changeView(PanelType.MAIN_MENU);
		});
		
		controller.getView().getGameOverView().getPlayButton().addActionListener(e->{
			controller.getGameController().getLoop().stopLoop();
			controller.resetGame();
			controller.changeLevelView();
			controller.changeView(PanelType.GAME);
			controller.getView().getInitialPanel().addKeyListener(controller.getGameController().getInputController());
			controller.getGameController().getLoop().initLoop();
		});
		
		
		/**
		 * buttons in the win page
		 */
		controller.getView().getWinnerView().getBackButton().addActionListener(e->{
			controller.changeView(PanelType.MAIN_MENU);
		});
		
		controller.getView().getWinnerView().getPlayButton().addActionListener(e->{
			controller.getGameController().getLoop().stopLoop();
			controller.resetGame();
			controller.changeLevelView();
			controller.changeView(PanelType.GAME);
			controller.getView().getInitialPanel().addKeyListener(controller.getGameController().getInputController());
			controller.getGameController().getLoop().initLoop();
		});
	}
}

