package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import model.Profile;

/**
 * class that represents the controller for the ranking section of the application
 */
public class RankingController {
	
	/**
	 * enum that describes possible sorting parameters for the ranking
	 */
	public enum SortingParam{
		NAME, LEVEL, GAMES, WINS, MAX_SCORE, TOTAL_SCORE;
		
		public static SortingParam getParam(int n) {
			SortingParam result = null;
			switch(n) {
			case 1 -> result = LEVEL;
			case 2 -> result = GAMES;
			case 3 -> result = WINS;
			case 4 -> result = TOTAL_SCORE;
			case 5 -> result = MAX_SCORE;
			default -> result = NAME;
			}
			return result;
		}
	}
	
	private LinkedList<Profile> ranking;
	private static final String PATH = "../Ranking.txt";
	
	/**
	 * constructor
	 */
	public RankingController() {
		ranking = new LinkedList<>();
		initRanking();
	}
	
	/**
	 * auxiliary method that reads the file containing the rankings and stores it in a list of profiles 
	 */
	private void initRanking() {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(PATH));
			br.readLine();
			while(br.ready()) {
				String[] profile = br.readLine().split(" ");
				ranking.add(new Profile(profile[0], profile[1], Integer.parseInt(profile[2]), Integer.parseInt(profile[3]), Integer.parseInt(profile[4]), 
						Integer.parseInt(profile[5]), Integer.parseInt(profile[6])));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(br!=null) {
				try { br.close(); } catch(Exception e) { e.printStackTrace();}
			}
		}
	}
	
	/**
	 * method that returns the ranking as a list of profiles
	 * @param ranking
	 */
	public List<Profile> getRanking(){
		return getSortedRanking(SortingParam.NAME);
	}
	
	/**
	 * method that returns the list of the profiles in the ranking sorted according to the parameter sp
	 * @param sp: it can be one of the alphabetical order, the number of games, the number of wins, the level (every time a player reaches
	 * 100000 points the level is incremented), the total number of points gained in every game, the maximum score in a single game 
	 * @return
	 */
	public List<Profile> getSortedRanking(SortingParam sp){
		List<Profile> result = new LinkedList<>();
		switch(sp) {
		case SortingParam.LEVEL -> {
			result = ranking.stream()
					.sorted((y,x)->x.getPlayerLevel().compareTo(y.getPlayerLevel())).limit(10)
					.toList();
		}
		case SortingParam.GAMES -> {
			result = ranking.stream()
					.sorted((y,x)->x.getGamesPlayed().compareTo(y.getGamesPlayed())).limit(10)
					.toList();
		}
		case SortingParam.WINS -> {
			result = ranking.stream()
					.sorted((y,x)->x.getGamesWon().compareTo(y.getGamesWon())).limit(10)
					.toList();
		}
		case SortingParam.MAX_SCORE -> {
			result = ranking.stream()
					.sorted((y,x)->x.getMaxScore().compareTo(y.getMaxScore())).limit(10)
					.toList();
		}
		case SortingParam.TOTAL_SCORE -> {
			result = ranking.stream()
					.sorted((y,x)->x.getUserScore().compareTo(y.getUserScore())).limit(10)
					.toList();
		}
		case SortingParam.NAME -> {
			result = ranking.stream()
					.sorted((x,y)->x.getUsername().compareTo(y.getUsername())).limit(10)
					.toList();
		}
		}
		return result;
	}
}
