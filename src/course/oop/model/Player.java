package course.oop.model;

public abstract class Player {
	public String username;
	public String marker;
	
	public Player(String username, String marker) {
		this.username = username;
		this.marker = marker;
	}
}
