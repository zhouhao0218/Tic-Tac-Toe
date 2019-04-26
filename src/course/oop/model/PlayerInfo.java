package course.oop.model;

import java.io.Serializable;

public class PlayerInfo implements Serializable{
	public String marker = "";
	public int win = 0;
	public int lose = 0;
	
	public PlayerInfo(String marker, int w, int l){
		this.marker = marker;
		this.win = w;
		this.lose = l;
	}
}
