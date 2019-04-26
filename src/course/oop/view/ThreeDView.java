package course.oop.view;

import course.oop.controller.ThreeDController;
import javafx.animation.RotateTransition;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

public class ThreeDView {
	ThreeDController TController;
	MainView mv;
	public ThreeDTile[][][] threeDBoard;
	
	public ThreeDView() {
		mv = MainView.getInstance();
		threeDBoard = new ThreeDTile[3][3][3];
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				for(int k=0; k<3; k++) {
					threeDBoard[i][j][k] = new ThreeDTile(i, j, k, TController);
				}
			}
		}
	}
	
	public void controllerSetup(ThreeDController TController) {
		this.TController = TController;
	}
	
	public GridPane getThreeDDisplay() {
		Button rotate = new Button("Rotate");
		GridPane gridPane = new GridPane();
		for(int i=0; i<3; i++) {
			threeDBoard[i] = getTwoDDisplay(i);
			GridPane innerPane = getInnerDisplay(threeDBoard[i]);
			gridPane.add(innerPane, 0, i);
		}
		
		EventHandler<MouseEvent> rotateHandler = new EventHandler<MouseEvent>() {

			public void handle(MouseEvent arg0) {
				RotateTransition rotateTransition = new RotateTransition();
				rotateTransition.setDuration(Duration.millis(1000));
				rotateTransition.setNode(gridPane);
				rotateTransition.setByAngle(90);
				rotateTransition.play();
			}
			
		};
		rotate.addEventHandler(MouseEvent.MOUSE_CLICKED, rotateHandler);
		
		gridPane.setMinSize((int)1000/2, (int)800/4);
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		gridPane.setVgap(5);
		gridPane.setHgap(5);
		gridPane.setAlignment(Pos.TOP_CENTER);
		gridPane.add(rotate, 0, 5);
		return gridPane;
	}
	
	public GridPane getInnerDisplay(ThreeDTile[][] board) {
		GridPane innerPane = new GridPane();
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				innerPane.add(board[i][j], j, i);
			}
		}
		return innerPane;
	}
	
	public ThreeDTile[][] getTwoDDisplay(int height){
		ThreeDTile[][] board = new ThreeDTile[3][3];
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				board[i][j] = new ThreeDTile(i, j, height, TController);
			}
		}
		return board;
	}
}
