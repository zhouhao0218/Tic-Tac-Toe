package course.oop.model;

public class GameBoard {
	public int curRow;
	public int curCol;
	public String defaultMarker;
	
	public String[][] board;
	
	
	public GameBoard() {
		board = new String[3][3];
	}
	
	public void init(String defaultMar) {
		this.defaultMarker = defaultMar;
		for(int i=0; i<board.length; i++) {
			for(int j=0; j<board[0].length; j++) {
				board[i][j] = defaultMar;
			}
		}
	}
	
	public boolean insertMarker(int row, int col, String mar) {
		if(mar.compareTo(defaultMarker) == 0) {
			return false;
		}else if(board[row][col].compareTo(defaultMarker) != 0) {
			return false;
		}else if(board[row][col].compareTo(defaultMarker) == 0){
			board[row][col] = mar;
		}
		return true;
	}
	
	public boolean isWin(int row, int col) {
		String cur = board[row][col];
		boolean b = true;
		for(int i=0; i<board.length; i++) {
			if(board[i][col].compareTo(cur) != 0) {
				b = false;
			}
		}
		if(b) return b;
		b = true;
		for(int i=0; i<board[0].length; i++) {
			if(board[row][i].compareTo(cur) != 0) {
				b = false;
			}
		}
		if(b) return b;
		b = true;
		for(int i=0; i<board.length; i++) {
			if(board[i][i].compareTo(cur) != 0) {
				b = false;
			}
		}
		if(b) return b;
		b = true;
		for(int i=board.length-1; i>=0; i--) {
			if(board[board.length-1-i][i].compareTo(cur) != 0) {
				b = false;
			}
		}
		return b;
	}
	
	public boolean isDraw() {
		boolean isDraw = false;
		
		for(int i=0; i<board.length; i++) {
			for(int j=0; j<board[0].length; j++) {
				if(board[i][j].compareTo(defaultMarker) != 0) {
					isDraw = true;
				}
			}
		}
		return isDraw;
	}
	
	public String printBoard() {
		StringBuilder boardDisplay = new StringBuilder();
		String result = "";
		boardDisplay.append("-------------");
		boardDisplay.append("\n");
		for(int i=0; i<board.length; i++) {
			for(int j=0; j<board[0].length; j++) {
				boardDisplay.append("|");
				if(board[i][j] != null) {
					boardDisplay.append(" ");
					boardDisplay.append(board[i][j]);
				}else {
					boardDisplay.append(" ");
				}
				boardDisplay.append(" ");
			}
			boardDisplay.append("|");
			boardDisplay.append("\n");
			boardDisplay.append("\n");
		}
		boardDisplay.append("-------------");
		result = boardDisplay.toString();
		return result;
	}
}
