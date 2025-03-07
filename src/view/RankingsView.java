package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.util.List;
import java.util.ListIterator;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.RankingController.SortingParam;
import model.Profile;

/**
 * class that represents the ranking page of the software, from which it is possible to change the sorting parameter and go back to the main menu
 */
public class RankingsView extends JPanel {

	private static final long serialVersionUID = -669628878629867035L;

	private JButton sortingButton;
	private JButton backButton;
	private JPanel ranking;
	private JPanel panelNorth;
	private JLabel infos;
	private SortingParam param;
	
	/**
	 * constructor
	 * it also initializes all the components inside the main panel
	 */
	public RankingsView() {
		
		setLayout(new BorderLayout());
		setBackground(Color.black);
		
		param = SortingParam.NAME;
		
		sortingButton = new JButton("SORT");
		sortingButton.setPreferredSize(new Dimension(150,50));
		sortingButton.setBackground(Color.black);
		sortingButton.setForeground(Color.red);
		sortingButton.setBorder(BorderFactory.createEmptyBorder());
		sortingButton.setFont(new Font("Press Start 2P", Font.PLAIN, 20));
		
		backButton = new JButton("BACK");
		backButton.setPreferredSize(new Dimension(150,50));
		backButton.setBackground(Color.black);
		backButton.setForeground(Color.red);
		backButton.setBorder(BorderFactory.createEmptyBorder());
		backButton.setFont(new Font("Press Start 2P", Font.PLAIN, 20));
		
		panelNorth = new JPanel();
		panelNorth.setBackground(Color.BLACK);
		panelNorth.setPreferredSize(new Dimension(400,80));
		infos = new JLabel();
		infos.setBackground(Color.BLACK);
		infos.setForeground(Color.GREEN);
		infos.setFont(new Font("Press Start 2P", Font.PLAIN, 20));
		setParameters();
		panelNorth.add(sortingButton);
		panelNorth.add(infos);
		panelNorth.add(backButton);
		
		JPanel panelWest = new JPanel();
		panelWest.setPreferredSize(new Dimension(180, 500));
		panelWest.setBackground(Color.black);
		panelWest.setLayout(new BorderLayout());
		
		JPanel panelEast = new JPanel();
		panelEast.setPreferredSize(new Dimension(180, 500));
		panelEast.setBackground(Color.black);
		panelEast.setLayout(new BorderLayout());
	
		ranking = new JPanel();
		ranking.setBackground(Color.black);
		ranking.setLayout(new BoxLayout(ranking, BoxLayout.Y_AXIS));
		
		add(ranking, BorderLayout.CENTER);
		add(panelWest, BorderLayout.WEST);
		add(panelNorth, BorderLayout.NORTH);
		add(panelEast, BorderLayout.EAST);
	}
	
	public JButton getSortingButton() {
		return sortingButton;
	}
	
	public JButton getBackButton() {
		return backButton;
	}
	
	/**
	 * methods that displays the ranking, that is received as an input 
	 * @param rank
	 */
	public void loadRanking(List<Profile> rank) {
		ranking.removeAll();
		ListIterator<Profile> it = rank.listIterator();
		while(it.hasNext()) {
			Profile curr = it.next();
			JLabel labelProf = new JLabel();
			labelProf.setBackground(Color.BLACK);
			labelProf.setForeground(Color.WHITE);
			labelProf.setFont(new Font("Press Start 2P", Font.PLAIN, 20));
			labelProf.setIcon(new ImageIcon(new ImageIcon(curr.getAvatar()).getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
			labelProf.setText(curr.getStats());
			ranking.add(labelProf);
		}
	}
	
	/**
	 * method that sets the current parameter used to sort
	 * @param sp the sorting parameter
	 */
	public void setCurrentParam(SortingParam sp) {
		param = sp;
		setParameters();
	}	
	
	/**
	 * method that updates the view of the current parameter's name used in sorting
	 */
	private void setParameters() {
		switch(param) {
		case SortingParam.NAME->  		infos.setText("USERNAME  level  games  wins  score   record");
		case SortingParam.LEVEL-> 		infos.setText("username  LEVEL  games  wins  score   record");
		case SortingParam.GAMES-> 		infos.setText("username  level  GAMES  wins  score   record");
		case SortingParam.WINS-> 		infos.setText("username  level  games  WINS  score   record");
		case SortingParam.TOTAL_SCORE-> infos.setText("username  level  games  wins  SCORE   record");
		case SortingParam.MAX_SCORE-> 	infos.setText("username  level  games  wins  score   RECORD");
		}
	}
}
