package model;

/**
 * class that represents the profile of a user
 */
public class Profile {
	private String username;
	private int gamesPlayed;
	private String avatar;
	private int gamesWon;
	private int playerLevel;
	private int userScore;
	private int maxScore;
	
	/**
	 * constructor
	 * @param username
	 * @param avatar
	 * @param playerLevel
	 * @param gamesPlayed
	 * @param gamesWon
	 * @param userScore
	 * @param maxScore
	 */
	public Profile(String username, String avatar, int playerLevel, int gamesPlayed, int gamesWon, int userScore, int maxScore) {
		this.username = username;
		this.avatar = avatar;
		this.playerLevel = playerLevel;
		this.gamesPlayed = gamesPlayed;
		this.gamesWon = gamesWon;
		this.userScore = userScore;
		this.maxScore = maxScore;
	}
	
	public String getUsername() {
		return username;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null || !o.getClass().equals(this.getClass())) return false;
		Profile p = (Profile)o;
		return p.getUsername() == this.getUsername();
	}
	
	@Override
	public int hashCode() {
		int hash = 17;
		hash = 31*hash + username.hashCode();
		return hash;
	}
	
	public void setAvatar(String path) {
		avatar = path;
	}
	
	public String getAvatar() {
		return avatar;
	}
	
	public Integer getGamesPlayed() {
		return gamesPlayed;
	}
	
	public Integer getGamesWon() {
		return gamesWon;
	}
	
	public void updateGamesPlayed() {
		gamesPlayed++;
	}
	
	public void updateGamesWon() {
		gamesWon++;
	}
	
	public void updateUserLevel() {
		playerLevel = 1 + ((int) userScore / 100000);
	}
	
	public Integer getPlayerLevel() {
		return playerLevel;
	}
	
	public Integer getUserScore() {
		return userScore;
	}
	
	public void addPoints(int points) {
		userScore += points;
	}
	
	public Integer getMaxScore() {
		return maxScore;
	}
	
	public void setMaxScore(int newmax) {
		maxScore = newmax;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(username);
		sb.append(" ");
		sb.append(avatar);
		sb.append(" ");
		sb.append(playerLevel);
		sb.append(" ");
		sb.append(gamesPlayed);
		sb.append(" ");
		sb.append(gamesWon);
		sb.append(" ");
		sb.append(userScore);
		sb.append(" ");
		sb.append(maxScore);
		sb.append("\n");
		return sb.toString();
	}
	
	/**
	 * method called by the ranking controller to get all the stats of a certain user
	 */
	public String getStats() {
		StringBuilder sb = new StringBuilder();
		sb.append("  ");
		sb.append(username);
		if(username.length()==4) sb.append("    ");
		if(username.length()==3) sb.append("     ");
		if(username.length()==2) sb.append("      ");
		if(username.length()==1) sb.append("       ");
		sb.append(playerLevel);
		if(playerLevel > 9) sb.append("      ");
		else sb.append("       ");
		sb.append(gamesPlayed);
		if(gamesPlayed > 9) sb.append("    ");
		else sb.append("     ");
		sb.append(gamesWon);
		if(gamesWon > 9) sb.append("   ");
		else sb.append("    ");
		sb.append(userScore);
		if(userScore >= 100000) sb.append("  ");
		else sb.append("   ");
		sb.append(maxScore);
		sb.append("\n");
		return sb.toString();
	}
}
