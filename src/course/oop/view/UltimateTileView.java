package course.oop.view;

import course.oop.controller.UltimateControllerImpl;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class UltimateTileView {
	public UltimateTile[][] CellArray;
	int row;
	int col;
	GridPane gridPane;
	UltimateControllerImpl controller;
	int cellWinner = 0;
	
	public UltimateTileView(int row, int col, UltimateControllerImpl controller) {
		CellArray = new UltimateTile[3][3];
		this.row = row;
		this.col = col;
		this.controller = controller;
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				CellArray[i][j] = new UltimateTile(i, j, this);
			}
		}
	}
	
	public void setGridPane(GridPane gridPane) {
		this.gridPane = gridPane;
	}

	public void indicateNext() {
		BorderWidths bWidth = new BorderWidths(3);
		BorderStroke bStroke = new BorderStroke(Color.AQUA, BorderStrokeStyle.SOLID, null, bWidth);
		Border border = new Border(bStroke);
		gridPane.setBorder(border);
	}
	
	public void removeBorder() {
		gridPane.setBorder(null);
	}
	
	public void indicateCellWin(String s) {
		if(cellWinner == 3) {
			BackgroundFill bf = new BackgroundFill(Color.BLACK, null, null);
			Background b = new Background(bf);
			gridPane.setBackground(b);
			return;
		}
		if(s.compareTo(controller.map.get(1).marker) == 0){
			BackgroundFill bf = new BackgroundFill(Color.RED, null, null);
			Background b = new Background(bf);
			gridPane.setBackground(b);
		}else {
			BackgroundFill bf = new BackgroundFill(Color.CORNFLOWERBLUE, null, null);
			Background b = new Background(bf);
			gridPane.setBackground(b);
		}
		
	}
}
