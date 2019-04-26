package course.oop.model;

import java.util.*;

public class UltimateBoard {
	Map<Integer, Player> map = new HashMap<>();
	String[][] UCell = new String[3][3];
	String defaultMarker;
	int row;
	int col;
	int count = 0;
	int winner = 0;
	
	public UltimateBoard (int row, int col, Map<Integer, Player> map) {
		this.row = row;
		this.col = col;
		this.map = map;
		init("");
	}
	
	public void init(String defaultMar) {
		this.defaultMarker = defaultMar;
		for(int i=0; i<UCell.length; i++) {
			for(int j=0; j<UCell[0].length; j++) {
				UCell[i][j] = defaultMar;
			}
		}
	}
	
	public int determineWinner(int row, int col, int curPlayer) {
		//System.out.println(map);
		if(winner != 0) {
			return winner;
		}
		UCell[row][col] = map.get(curPlayer).marker;
		//System.out.println(UCell[row][col]);
		count++;
		if(winner != 0) {
			return winner;
		}
		boolean isWin = isWin(row, col);
		if(isWin) {
			winner = curPlayer;
		}
		if(count == 9 && winner == 0) {
			winner = 3;
		}
		return winner;
	}
	
	public boolean isWin(int row, int col) {
		String cur = UCell[row][col];
		boolean b = true;
		for(int i=0; i<UCell.length; i++) {
//			System.out.println(UCell[i][col]);
			if(UCell[i][col].compareTo(cur) != 0) {
				b = false;
			}
		}
		if(b) return b;
		b = true;
		for(int i=0; i<UCell[0].length; i++) {
			if(UCell[row][i].compareTo(cur) != 0) {
				b = false;
			}
		}
		if(b) return b;
		b = true;
		for(int i=0; i<UCell.length; i++) {
			if(UCell[i][i].compareTo(cur) != 0) {
				b = false;
			}
		}
		if(b) return b;
		b = true;
		for(int i=UCell.length-1; i>=0; i--) {
			if(UCell[UCell.length-1-i][i].compareTo(cur) != 0) {
				b = false;
			}
		}
		return b;
	}
}
