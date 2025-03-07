package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.AudioManager;
import controller.Controller;

/**
 * class that represents the main frame of the software
 */
public class BubbleBobbleFrame extends JFrame{
	
	private static final long serialVersionUID = 262750286244013254L;
	
	/**
	 * enum that represents the possible panels to be displayed
	 */
	public enum PanelType{
		MAIN_MENU, GAME, WIN, GAME_OVER, RANKING, LOAD
	}
	
	private Controller controller;
	private JPanel initialPanel;
	private MainMenuView mainMenuView;
	private GameView gameView;
	private RankingsView rankingsView;
	private EndingView gameOverView;
	private EndingView winnerView;
	private PanelType currentView;
	private JPanel loadingView;

	/**
	 * constructor
	 */
	public BubbleBobbleFrame() {
		super("JBubbleBobble");
		mainMenuView = new MainMenuView();
		gameOverView = new EndingView(new BackgroundImage("../resources/gameover_screen.png"));
		gameView = new GameView();
		rankingsView = new RankingsView();
		winnerView = new EndingView(new BackgroundImage("../resources/win_screen.png"));
		loadingView = new JPanel(new BorderLayout());
		loadingView.setBackground(Color.black);
		loadingView.setPreferredSize(new Dimension(600,60));
		JTextField loadingText = new JTextField();
		loadingText.setText("Loading...");
		loadingView.add(loadingText, BorderLayout.CENTER);
		currentView = PanelType.MAIN_MENU;
		addFont("../resources/Retro Gaming.ttf");
		
		/**
		 * main panel in the frame, it uses the cardlayout to easily switch between the panels
		 */
		initialPanel = new JPanel(new CardLayout()) {

			private static final long serialVersionUID = 8156888942394690408L;

			{
				add(mainMenuView, PanelType.MAIN_MENU.name());
				add(gameOverView, PanelType.GAME_OVER.name());
				add(gameView, PanelType.GAME.name());
				add(loadingView, PanelType.LOAD.name());
				add(rankingsView, PanelType.RANKING.name());
				add(winnerView, PanelType.WIN.name());
				setFocusable(true);
				requestFocus();
			}
		};
		
		try {
			setIconImage(ImageIO.read(new File("../resources/icon.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		add(initialPanel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1400, 900);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
	}
	
	public void setController(Controller c) {
		controller = c;
	}
	
	public Controller getController() {
		return controller;
	}
	
	public JPanel getInitialPanel() {
		return initialPanel;
	}
	
	public MainMenuView getMainMenuView() {
		return mainMenuView;
	}
	
	public GameView getGameView() {
		return gameView;
	}
	
	public EndingView getGameOverView() {
		return gameOverView;
	}
	
	public RankingsView getRankingView() {
		return rankingsView;
	}
	
	public EndingView getWinnerView() {
		return winnerView;
	}

	public PanelType getCurrentView() {
		return currentView;
	}
	
	/**
	 * method that puts in the foreground the specified panel, represented by pt, and plays the correct audio sample
	 * @param pt
	 */
	public void showView(PanelType pt) {
		((CardLayout) initialPanel.getLayout()).show(initialPanel, pt.name());
		setCurrentViewAudio(pt);
	}
	
	/**
	 * method that given a panel plays the correct audio sample
	 * @param pt
	 */
	private void setCurrentViewAudio(PanelType pt) {
		switch(pt) {
		case MAIN_MENU -> {
			AudioManager.getInstance().stop();
			AudioManager.getInstance().play("../resources/Opening Theme.wav");
		}
		case GAME_OVER -> {
			AudioManager.getInstance().stop();
			AudioManager.getInstance().play("../resources/Ending.wav");
		}
		case RANKING ->{
			if(currentView != PanelType.LOAD) {
				AudioManager.getInstance().stop();
				AudioManager.getInstance().play("../resources/Name Register.wav");
				AudioManager.getInstance().loop(10);
			}
		}
		case WIN -> {
			AudioManager.getInstance().stop();
			AudioManager.getInstance().play("../resources/Ending.wav");
		}
		case GAME -> {
			if(currentView != PanelType.LOAD){
				AudioManager.getInstance().play("../resources/main.wav");
				AudioManager.getInstance().loop(30);
			}
		}
		case LOAD -> {}
		}
		currentView = pt;
	}
	
	/**
	 * method used to use a custom font for the retro-gaming
	 * @param path
	 */
	public void addFont(String path) {
		File fontFile = new File(path);
	    Font customFont = null;
		try {
			customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
		    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    ge.registerFont(customFont);
		    customFont = customFont.deriveFont(35f);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
	}
}
