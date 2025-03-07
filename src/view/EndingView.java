package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * class that represents the panel that is put in the foreground when the game ends
 * it will display as a background the image given by the constructor
 */
public class EndingView extends JPanel {

	private static final long serialVersionUID = -1651808148616034936L;

	private BackgroundImage background;
	private JButton homeButton;
	private JButton playAgain;
	
	/**
	 * constructor
	 * @param b: the image that will be in the background
	 */
	public EndingView(BackgroundImage b) {
		
		setLayout(new BorderLayout());
		
		background = b;
		background.draw();
		
		homeButton = new JButton("MAIN MENU");
		homeButton.setPreferredSize(new Dimension(250,80));
		homeButton.setBackground(Color.black);
		homeButton.setForeground(Color.red);
		homeButton.setBorder(BorderFactory.createEmptyBorder());
		homeButton.setFont(new Font("Press Start 2P", Font.PLAIN, 16));
		
		playAgain = new JButton("PLAY AGAIN");
		playAgain.setPreferredSize(new Dimension(250,80));
		playAgain.setBackground(Color.black);
		playAgain.setForeground(Color.red);
		playAgain.setBorder(BorderFactory.createEmptyBorder());
		playAgain.setFont(new Font("Press Start 2P", Font.PLAIN, 16));
		
		JPanel panelSouth = new JPanel();
		panelSouth.setBackground(Color.black);
		panelSouth.setPreferredSize(new Dimension(300,100));
		panelSouth.add(homeButton);
		panelSouth.add(playAgain);
		
		JPanel panelNorth = new JPanel();
		panelNorth.setBackground(Color.black);
		panelNorth.setPreferredSize(new Dimension(300,80));
		
		JPanel eastPanel = new JPanel();
		eastPanel.setBackground(Color.black);
		eastPanel.setPreferredSize(new Dimension(200,100));
		
		JPanel westPanel = new JPanel();
		westPanel.setBackground(Color.black);
		westPanel.setPreferredSize(new Dimension(140,100));
		
		add(westPanel, BorderLayout.WEST);
		add(eastPanel, BorderLayout.EAST);
		add(panelNorth, BorderLayout.NORTH);
		add(background, BorderLayout.CENTER);
		add(panelSouth, BorderLayout.SOUTH);
	}
	
	/**
	 * getter for the buttons
	 * @return
	 */
	public JButton getBackButton() {
		return homeButton;
	}
	
	public JButton getPlayButton() {
		return playAgain;
	}
}
