package course.oop.controller;

public interface UltimateControllerInterface {
	
	void startNewGame(int numPlayers, int timeoutInSecs);
	void createPlayer(String username, String marker, int playerNum);
	boolean setSelection(int cellRow, int cellCol, int row, int col, int curPlayer);
	
}
