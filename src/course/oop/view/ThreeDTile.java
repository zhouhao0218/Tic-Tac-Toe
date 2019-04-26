package course.oop.view;

import course.oop.controller.ThreeDController;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ThreeDTile extends StackPane{
	private Rectangle tile;
	private final int length = 50;
	public Text text = new Text();
	boolean isAdded = false;
	ThreeDController TController;
	
	public ThreeDTile(int row, int col, int height, ThreeDController TController) {
		this.TController = TController;
		tile = new Rectangle(length, length);
		tile.setFill(null);
		tile.setStroke(Color.BLACK);
		setAlignment(Pos.CENTER);
		text.setFont(Font.font(36));
		getChildren().addAll(tile, text);
		
		setOnMouseClicked(event ->{
			if(isAdded) {
				return;
			}
			int temp = TController.curPlayerNum;
			boolean isInserted = TController.setSelection(row, col, height, TController.curPlayerNum);
			if(isInserted) {
				text.setText(TController.map.get(temp).marker);
				isAdded = true;
			}else {
				return;
			}
		});
	}
}
