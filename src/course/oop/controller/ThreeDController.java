package course.oop.controller;

import java.util.HashMap;
import java.util.Map;

import course.oop.model.HumanPlayer;
import course.oop.model.Player;
import course.oop.view.*;

public class ThreeDController {
	String[][][] threeDBoard = new String[3][3][3];
	String[][] rowCheck = new String[3][3];
	String[][] colCheck = new String[3][3];
	String[][] heightCheck = new String[3][3];
	public Map<Integer, Player> map = new HashMap<>();
	public int curPlayerNum = 1;
	int timeout;
	int winner = 0;
	MainView mView;
	ThreeDView tView;
	
	
	public ThreeDController(MainView mView,ThreeDView tView, Map<Integer, Player> map) {
		this.mView = mView;
		this.tView = tView;
		this.map = map;
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				for(int k=0; k<3; k++) {
					threeDBoard[i][j][k] = "";
				}
			}
		}
	}
	
	public void startNewGame(int numPlayers, int timeoutSecs) {
		this.timeout = timeoutSecs;
	}
	
	public void createPlayer(String username, String marker, int playerNum) {
		if(playerNum<1 || playerNum >2) {
			return;
		}
		Player p = new HumanPlayer(username, marker);
		map.put(playerNum, p);
	}
	
	public boolean setSelection(int row, int col, int height, int curPlayer) {
		String curMark = map.get(curPlayerNum).marker;
		threeDBoard[row][col][height] = curMark;
		boolean isWin = isWin(row, col, height);
		if(isWin) {
			winner = curPlayer;
			mView.TpromptEndWindow(winner);
		}
		changeTurn();
		return true;
	}
	
	public int getWinner() {
		return winner;
	}
	
	public void changeTurn() {
		if(curPlayerNum == 1) {
			curPlayerNum++;
		}else {
			curPlayerNum--;
		}
		if(mView.isPvP == false && curPlayerNum == 2) {
			AIMove();
		}
	}
	
	public void AIMove() {
		boolean isInsert = false;
		while(!isInsert) {
			int row = generateNum();
			int col = generateNum();
			int height = generateNum();
			isInsert = setSelection(row, col, height, 2);
			if(isInsert) {
				tView.threeDBoard[row][col][height].text.setText(map.get(2).marker);
			}
		}
	}
	
	public int generateNum() {
		int max = 2;
		int min = 0;
		int range = max - min + 1;
		
		int rand = (int)(Math.random() * range) + min;
		
		return rand;
	}
	
	public boolean twoDCheck(int row, int col, String[][] board) {
		String cur = board[row][col];
		boolean b = true;
		for(int i=0; i<board.length; i++) {
			if(board[i][col] != cur) {
				b = false;
			}
		}
		if(b) return b;
		b = true;
		for(int i=0; i<board[0].length; i++) {
			if(board[row][i] != cur) {
				b = false;
			}
		}
		if(b) return b;
		b = true;
		for(int i=0; i<board.length; i++) {
			if(board[i][i] != cur) {
				b = false;
			}
		}
		if(b) return b;
		b = true;
		for(int i=board.length-1; i>=0; i--) {
			if(board[board.length-1-i][i] != cur) {
				b = false;
			}
		}
		return b;
	}
	
	public boolean isWin(int row, int col, int height) {
		rowCheck = checkRow(threeDBoard, row);
		boolean win = twoDCheck(col, height, rowCheck);
		if(win) {
			return win;
		}
		colCheck = checkCol(threeDBoard, col);
		win = twoDCheck(row, height, colCheck);
		if(win) {
			return win;
		}
		heightCheck = checkHeight(threeDBoard, height);
		win = twoDCheck(row, col , heightCheck);
		if(win) {
			return win;
		}
		
		win = checkDiagonal(row, col, height);
		
		return win;
	}
	
	public String[][] checkRow(String[][][] array, int row){
		String[][] ret = new String[3][3];
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				ret[i][j] = array[row][i][j];
			}
		}
		return ret;
	}
	
	public String[][] checkCol(String[][][] array, int col){
		String[][] ret = new String[3][3];
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				ret[i][j] = array[i][col][j];
			}
		}
		return ret;
	}
	
	public String[][] checkHeight(String[][][] array, int height){
		String[][] ret = new String[3][3];
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				ret[i][j] = array[i][j][height];
			}
		}
		return ret;
	}
	
	public boolean checkDiagonal(int row, int col, int height) {
		boolean ret = false;
		if(row != 1 && col != 1 && height != 1) {
			ret = true;
			String curMark = threeDBoard[row][col][height];
			for(int i=0; i<3; i++) {
				if(threeDBoard[i][i][i] != curMark) {
					ret = false;
					break;
				}
			}
			if(ret) {
				return ret;
			}
			//ret = true;
			for(int i=0; i<3; i++) {
				if(threeDBoard[2-i][i][i] != curMark) {
					ret = false;
					break;
				}
			}
			if(ret) {
				return ret;
			}
		}
		return ret;
	}
}
