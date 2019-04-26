package course.oop.view;

import course.oop.controller.UltimateControllerImpl;
import javafx.animation.RotateTransition;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

public class UltimateView {
	UltimateControllerImpl UController;
	public UltimateTileView[][] ultimateBoard = new UltimateTileView[3][3];
	MainView mv;
	
	public UltimateView() {
		mv = MainView.getInstance();
	}
	
	public void controllerSetup(UltimateControllerImpl UController) {
		this.UController = UController;
	}
	
	public GridPane getUltimateBoardDisplay() {
		Button rotate = new Button("Rotate");
		GridPane gridPane = new GridPane();
		
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				ultimateBoard[i][j] = new UltimateTileView(i,j, UController);
				gridPane.add(getInnerDisplay(ultimateBoard[i][j]), i, j);
			}
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
		gridPane.add(rotate, 1, 5);
		
		return gridPane;
	}
	
	public GridPane getInnerDisplay(UltimateTileView tile) {
		GridPane gridPane = new GridPane();
		
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				UltimateTile t = tile.CellArray[i][j];
				gridPane.add(t, i, j);
			}
		}
		
		tile.setGridPane(gridPane);
		
		return gridPane;
	}
}
