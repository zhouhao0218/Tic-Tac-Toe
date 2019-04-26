package course.oop.controller;

import java.util.*;
import course.oop.model.*;

public class TTTControllerImpl implements TTTControllerInterface{
	
	private GameBoard board;
	private Player player;
	private int winnerNum = 0;
	private int time;
	private int numOfInsertion = 0;
	private Map<Integer, Player> map = new HashMap<>();
	
	/**
	 * Initialize or reset game board. Set each entry back to a default value.
	 * 
	 * @param numPlayers Must be valid. 2 = two human players, 1 = human plays against computer
	 * @param timeoutInSecs Allow for a user's turn to time out. Any
	 * 						int <=0 means no timeout.  Any int > 0 means to time out
	 * 						in the given number of seconds.
	 */
	@Override
	public void startNewGame(int numPlayers, int timeoutInSecs) {
		// TODO Auto-generated method stub
		board = new GameBoard();
		board.init(" ");
		time = timeoutInSecs;
		winnerNum = 0;
		numOfInsertion = 0;
		
	}
	
	/**
	 * Create a player with specified username, marker, 
	 * and player number (either 1 or 2) 
	 * 
	 * @param username
	 * @param marker
	 * @param playerNum
	 */
	@Override
	public void createPlayer(String username, String marker, int playerNum) {
		// TODO Auto-generated method stub
		if(playerNum<1 || playerNum >2) {
			return;
		}
		player = new HumanPlayer(username, marker);
		map.put(playerNum, player);
	}

	/**
	 * Allow user to specify location for marker.  
	 * Return true if the location is valid and available.
	 * 
	 * @param row Must be valid. 0,1,2
	 * @param col Must be valid. 0,1,2
	 * @param currentPlayer Must be valid. 1 = player1; 2 = player2
	 * @return
	 */
	@Override
	public boolean setSelection(int row, int col, int currentPlayer) {
		// TODO Auto-generated method stub
		if(currentPlayer != 1 && currentPlayer != 2) {
			return false;
		}
		if(row < 0 || row > 3 || col < 0 || col > 3) {
			return false;
		}
		
//		player = new ComputerPlayer("Computer", "$");
//		if(!map.containsKey(currentPlayer)) {
//				map.put(currentPlayer, player);
//		}
		
		boolean isInsert = board.insertMarker(row, col, map.get(currentPlayer).marker);
		if(isInsert) {
			numOfInsertion++;
		}
		
		if(numOfInsertion == 9 && winnerNum == 0) {
			winnerNum = 3;
		}
		
		boolean isWin = board.isWin(row, col);
		if(isWin) {
			winnerNum = currentPlayer;
		}
		
		return isInsert;
	}

	/**
	 * Determines if there is a winner and returns the following:
	 * 
	 * 0=no winner / game in progress / not all spaces have been selected; 
	 * 1=player1; 
	 * 2=player2; 
	 * 3=tie/no more available locations
	 * 
	 * @return
	 */
	@Override
	public int determineWinner() {
		// TODO Auto-generated method stub
		return winnerNum;
	}

	/**
	 * Return a printable display of current selections.
	 * Shows 3x3 (or nxn) board with each players marker.
	 * 
	 * @return
	 */
	@Override
	public String getGameDisplay() {
		// TODO Auto-generated method stub
		String print = board.printBoard();
		return print;
	}
	
	public Map<Integer, Player> getMap(){
		return map;
	}

}
