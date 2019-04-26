package course.oop.view;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class UltimateTile extends StackPane{
	private Rectangle tile;
	private final int length = 50;
	public Text text = new Text();
	UltimateTileView UTV;
	boolean isAdded = false;
	
	public UltimateTile(int row, int col, UltimateTileView ultimateTileView) {
		tile = new Rectangle(length, length);
		tile.setFill(null);
		tile.setStroke(Color.BLACK);
		setAlignment(Pos.CENTER);
		text.setFont(Font.font(28));
		getChildren().addAll(tile, text);
		UTV = ultimateTileView;
		
		setOnMouseClicked(event -> {
			if(isAdded) {
				return;
			}
			if(UTV.controller.getWinner() != 0) {
				return;
			}
			int temp = UTV.controller.curPlayerNum;
			boolean isInserted = UTV.controller.setSelection(UTV.row, UTV.col, row, col, UTV.controller.curPlayerNum);
			if(isInserted) {
				text.setText(UTV.controller.map.get(temp).marker);
				isAdded = true;
			}else {
				return;
			}
		});
	}
	
}
