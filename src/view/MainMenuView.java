package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * class that represents the starting view of the software, from which it's possible to start a game and to look at the ranking
 */
public class MainMenuView extends JPanel {
		
	private static final long serialVersionUID = -1515634416131227165L;
	
	private JButton startButton;
	private JButton rankingButton;
	private BackgroundImage background;
	private JTextField usernameField;
	private JButton usernameButton;
	private JButton avatarButton;
	private JLabel avatarImg;
	
	/**
	 * constructor
	 * it initializes also all the components inside the objects that are instance of this class
	 */
	public MainMenuView() {
		
		setLayout(new BorderLayout());
		
		background = new BackgroundImage("../resources/background.jpeg");
		background.draw();
		
		startButton = new JButton("PLAY!");
		startButton.setPreferredSize(new Dimension(250,50));
		startButton.setBackground(Color.black);
		startButton.setForeground(Color.red);
		startButton.setBorder(BorderFactory.createEmptyBorder());
		startButton.setFont(new Font("Press Start 2P", Font.PLAIN, 25));
		
		rankingButton = new JButton("RANKINGS");
		rankingButton.setPreferredSize(new Dimension(250,50));
		rankingButton.setBackground(Color.black);
		rankingButton.setForeground(Color.red);
		rankingButton.setBorder(BorderFactory.createEmptyBorder());
		rankingButton.setFont(new Font("Press Start 2P", Font.PLAIN, 25));
		
		JLabel username = new JLabel();
		username.setBackground(Color.black);
		username.setForeground(Color.green);
		username.setBorder(BorderFactory.createEmptyBorder(12,0,0,0));
		username.setText("YOUR NAME:");
		username.setFont(new Font("Press Start 2P", Font.PLAIN, 25));
		
		usernameField = new JTextField(6);
		usernameField.setBackground(Color.black);
		usernameField.setForeground(Color.green);
		usernameField.setFont(new Font("Press Start 2P", Font.PLAIN, 16));
		usernameField.setBorder(BorderFactory.createEmptyBorder(12,0,0,0));
		
		usernameButton = new JButton("SET");
		usernameButton.setBackground(Color.black);
		usernameButton.setForeground(Color.green);
		usernameButton.setBorder(BorderFactory.createEmptyBorder(12,0,0,0));
		usernameButton.setFont(new Font("Press Start 2P", Font.PLAIN, 16));
		
		JLabel avatar = new JLabel();
		avatar.setBackground(Color.black);
		avatar.setForeground(Color.red);
		avatar.setText("YOUR AVATAR:");
		avatar.setFont(new Font("Press Start 2P", Font.PLAIN, 25));
		
		avatarImg = new JLabel();
		avatarImg.setIcon(new ImageIcon(new ImageIcon("../resources/avatars/0.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
		
		avatarButton = new JButton("CHANGE");
		avatarButton.setBackground(Color.black);
		avatarButton.setForeground(Color.red);
		avatarButton.setBorder(BorderFactory.createEmptyBorder());
		avatarButton.setFont(new Font("Press Start 2P", Font.PLAIN, 16));
		
		JPanel panelSouth = new JPanel();
		panelSouth.setBackground(Color.black);
		panelSouth.setPreferredSize(new Dimension(300,80));
		panelSouth.add(startButton);
		panelSouth.add(rankingButton);
		
		JPanel panelNorthWest = new JPanel();
		panelNorthWest.setPreferredSize(new Dimension(500,60));
		panelNorthWest.setBackground(Color.black);
		panelNorthWest.setLayout(new FlowLayout());
		panelNorthWest.add(username);
		panelNorthWest.add(usernameField);
		panelNorthWest.add(usernameButton);
		
		JPanel panelNorthEast = new JPanel();
		panelNorthEast.setPreferredSize(new Dimension(500,60));
		panelNorthEast.setBackground(Color.black);
		panelNorthEast.setLayout(new FlowLayout());
		panelNorthEast.add(avatar);
		panelNorthEast.add(avatarImg);
		panelNorthEast.add(avatarButton);
		
		JPanel panelNorth = new JPanel();
		panelNorth.setPreferredSize(new Dimension(1000,60));
		panelNorth.setBackground(Color.black);
		panelNorth.setLayout(new FlowLayout());
		panelNorth.add(panelNorthWest);
		panelNorth.add(panelNorthEast);
		
		JPanel eastPanel = new JPanel();
		eastPanel.setBackground(Color.black);
		eastPanel.setPreferredSize(new Dimension(200,100));
		
		JPanel westPanel = new JPanel();
		westPanel.setBackground(Color.black);
		westPanel.setPreferredSize(new Dimension(140,100));
		
		this.add(panelNorth, BorderLayout.NORTH);
		this.add(eastPanel, BorderLayout.EAST);
		this.add(westPanel, BorderLayout.WEST);
		this.add(background, BorderLayout.CENTER);
		this.add(panelSouth, BorderLayout.SOUTH);
	}
	
	public JButton getStartButton() {
		return startButton;
	}
	
	public JButton getRankingButton() {
		return rankingButton;
	}
	
	public JTextField getUsernameField() {
		return usernameField;
	}
	
	public JButton getAvatarButton() {
		return avatarButton;
	}
	
	public JButton getUsernameButton() {
		return usernameButton;
	}
	
	public void setAvatar(int id) {
		avatarImg.removeAll();
		avatarImg.setIcon(new ImageIcon(new ImageIcon("../resources/avatars/"+id+".png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
	}
}
