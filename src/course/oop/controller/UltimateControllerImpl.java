package course.oop.controller;

import java.util.*;
import course.oop.model.*;
import course.oop.view.MainView;
import course.oop.view.UltimateView;

public class UltimateControllerImpl {
	public Map<Integer, Player> map;
	public UltimateBoard[][] board;
	public int curPlayerNum = 1;
	public Location nextMove;
	String[][] UCell = new String[3][3];
	int timeout = 0;
	int winner = 0;
	int boardRow = -1;
	int boardCol = -1;
	UltimateView uView;
	MainView mView;
	TTTControllerImpl controller;
	
	
	public UltimateControllerImpl(MainView mView, UltimateView uView, TTTControllerImpl controller, Map<Integer, Player> map) {
		this.mView = mView;
		this.uView = uView;
		this.controller = controller;
		this.map = map;
	}
	
	public void startNewGame(int numPlayers, int timeoutSecs) {
		board = new UltimateBoard[3][3];
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				board[i][j] = new UltimateBoard(i, j, map);
			}
		}
		timeout = timeoutSecs;
		winner = 0;
	}
	
	public void createPlayer(String username, String marker, int playerNum) {
		if(playerNum<1 || playerNum >2) {
			return;
		}
		Player p = new HumanPlayer(username, marker);
		map.put(playerNum, p);
	}
	
	public boolean setSelection(int cellRow, int cellCol, int row, int col, int curPlayer) {
		if(boardRow != -1 && (boardRow != cellRow || boardCol != cellCol)) {
			return false;
		}
		int cellWinner = board[cellRow][cellCol].determineWinner(row, col, curPlayer);
		if(cellWinner != 0 && UCell[cellRow][cellCol] == null) {
			UCell[cellRow][cellCol] = map.get(curPlayerNum).marker;
			boolean isWin = isWin(cellRow, cellCol, UCell);
			if(isWin) {
				winner = curPlayer;
				mView.UpromptEndWindow(winner);
			}else {
				uView.ultimateBoard[cellRow][cellCol].indicateCellWin(map.get(curPlayerNum).marker);
			}
		}
		uView.ultimateBoard[cellRow][cellCol].removeBorder();
		uView.ultimateBoard[row][col].indicateNext();
		boardRow = row;
		boardCol = col;
		changeTurn();
		return true;
	}
	
	public void AIMove() {
//		System.out.println(boardRow);
//		System.out.println(boardCol);
		boolean isInsert = false;
		while(!isInsert) {
			int row = generateNum();
			int col = generateNum();
			int curRowTemp = boardRow;
			int curColTemp = boardCol;
			isInsert = setSelection(boardRow, boardCol, row, col, 2);
			if(isInsert) {
				uView.ultimateBoard[curRowTemp][curColTemp].CellArray[row][col].text.setText(map.get(2).marker);
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
	
	public boolean isWin(int row, int col, String[][] UCell) {
		String cur = UCell[row][col];
		boolean b = true;
		for(int i=0; i<UCell.length; i++) {
			if(UCell[i][col] != cur) {
				b = false;
			}
		}
		if(b) return b;
		b = true;
		for(int i=0; i<UCell[0].length; i++) {
			if(UCell[row][i] != cur) {
				b = false;
			}
		}
		if(b) return b;
		b = true;
		for(int i=0; i<UCell.length; i++) {
			if(UCell[i][i] != cur) {
				b = false;
			}
		}
		if(b) return b;
		b = true;
		for(int i=UCell.length-1; i>=0; i--) {
			if(UCell[UCell.length-1-i][i] != cur) {
				b = false;
			}
		}
		return b;
	}
}
