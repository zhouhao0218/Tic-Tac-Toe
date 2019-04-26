package course.oop.view;

import course.oop.model.*;
import course.oop.controller.TTTControllerImpl;
import course.oop.controller.ThreeDController;
import course.oop.controller.UltimateControllerImpl;

import java.util.*;
import java.io.*;

import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.media.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.stage.*;
import javafx.util.Duration;


public class MainView {
	private BorderPane root;
	private Scene scene;
	private TTTControllerImpl controller = new TTTControllerImpl();
	private UltimateControllerImpl UController;
	private ThreeDController TController;
	private UltimateView uView;
	private ThreeDView tView;
	private Tile[][] board = new Tile[3][3];
	private final int windowWidth = 1400;
	private final int windowHeight = 1000;
	private int curPlayerNum = 1;
	private int timeout = 0;
	private Map<String, PlayerInfo> map;
	public boolean isPvP = true;
	private static MainView mv;


	public MainView() {
		this.root = new BorderPane();
		this.scene = new Scene(root, windowWidth, windowHeight);
		this.root.setTop(this.buildPlayerInfoPane());
		this.root.setRight(this.displayPlayerInfo());
		this.scene.getStylesheets().add(getClass().getResource("main.css").toExternalForm());
	}
	
	public static MainView getInstance() {
		if(mv == null) {
			mv = new MainView();
		}
		return mv;
	}

	public Scene getMainScene() {
		return this.scene;
	}

	public GridPane buildPlayerInfoPane() {
		map = new HashMap<>();
		Text usernameLable = new Text("Please enter your username: ");
		Text markerLable = new Text("Please specify your prefered marker: ");
		TextField usernameField = new TextField();
		TextField markerField = new TextField();
		Button submitInfo = new Button("Submit");
		Button chooseMode = new Button("Confirm");
		ChoiceBox<String> cb = new ChoiceBox<>();
		cb.getItems().addAll("PvP","PvE");
		cb.setValue("PvP");
		Line line = new Line();
		line.setStartX(0.0f); 
		line.setStartY(0.0f);         
		line.setEndX((float) windowWidth); 
		line.setEndY(0.0f);

		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				String username = usernameField.getText();
				String marker = markerField.getText();
				PlayerInfo playerInfo = new PlayerInfo(marker, 0, 0);
				map.put(username, playerInfo);
				try {
					FileOutputStream fos = new FileOutputStream("playerInfo.ser");
					ObjectOutputStream oos = new ObjectOutputStream(fos);
					oos.writeObject(map);
					oos.close();
					fos.close();
					System.out.println("Player info stored");
				}catch(IOException ioe) {
					ioe.printStackTrace();
				}
				root.setRight(displayPlayerInfo());
			}     

		};

		EventHandler<MouseEvent> modeHandler = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				String choice = cb.getValue();
				if(choice.compareTo("PvP") == 0) {
					isPvP = true;
				}else {
					isPvP = false;
				}
				SelectPlayer();
			}

		};

		submitInfo.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
		chooseMode.addEventHandler(MouseEvent.MOUSE_CLICKED, modeHandler);

		GridPane gridPane = new GridPane();
		gridPane.setMinSize(windowWidth, (int) windowHeight/4);
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		gridPane.setVgap(5);
		gridPane.setHgap(5);
		gridPane.setAlignment(Pos.CENTER);
		gridPane.add(usernameLable, 0, 0);
		gridPane.add(markerLable, 1, 0);
		gridPane.add(usernameField, 0, 1);
		gridPane.add(markerField, 1, 1);
		gridPane.add(submitInfo, 2, 1);
		gridPane.add(cb, 3, 1);
		gridPane.add(chooseMode, 4, 1);
		gridPane.add(line, 0, 2, 3, 1);
		return gridPane;
	}

	public GridPane displayPlayerInfo() {
		Text usernameLable = new Text("Username");
		Text markerLable = new Text("Marker");
		Text wlLable = new Text("Win - Lose");
		Button exit = new Button("Exit");
		GridPane gridPane = new GridPane();
		gridPane.setMinSize((int)windowWidth/4, windowHeight);
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		gridPane.setVgap(5);
		gridPane.setHgap(5);
		gridPane.setAlignment(Pos.TOP_CENTER);
		gridPane.add(usernameLable, 0, 0);
		gridPane.add(markerLable, 1, 0);
		gridPane.add(wlLable, 2, 0);

		map = new HashMap<>();
		try {
			FileInputStream fis = new FileInputStream("PlayerInfo.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			map = (HashMap<String, PlayerInfo>) ois.readObject();
			ois.close();
			fis.close();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}catch(ClassNotFoundException c) {
			System.out.println("Class not found");
			c.printStackTrace();
		}
		int i = 1;
		int j = 1;
		for(String key : map.keySet()) {
			PlayerInfo playerInfo = map.get(key);
			Text username = new Text(key);
			Text marker = new Text(playerInfo.marker);
			Text win = new Text(Integer.toString(map.get(key).win));
			Text lose = new Text(Integer.toString(map.get(key).lose));
			gridPane.add(username, 0, i);
			gridPane.add(marker, 1, j);
			gridPane.add(win, 2, j);
			gridPane.add(lose, 3, j);
			i++;
			j++;
		}
		
		EventHandler<MouseEvent> exitHandler = new EventHandler<MouseEvent>() {

			public void handle(MouseEvent arg0) {
				System.exit(0);
			}
			
		};
		exit.addEventHandler(MouseEvent.MOUSE_CLICKED, exitHandler);
		gridPane.add(exit, 0, 75);

		return gridPane;
	}
	
	public void createPlayer(ComboBox<Object> cb, int playerNum) {
		String playerInfo = cb.getSelectionModel().getSelectedItem().toString();
		String info[] = playerInfo.split(" ");
		String username = info[1];
		String marker = info[3];
		controller.createPlayer(username, marker, playerNum);
		UController.createPlayer(username, marker, playerNum);
		TController.createPlayer(username, marker, playerNum);
	}

	public void SelectPlayer() {
		Stage stage = new Stage();
		
		InitUController();
		InitTController();
		Text player1 = new Text("Select Player 1");
		Text player2 = new Text("Select Player 2");
		Text message = new Text("Player 1 will start first.");
		Button start = new Button("Start");
		GridPane.setConstraints(player1, 0, 0);
		GridPane.setConstraints(player2, 0, 1);
		GridPane.setConstraints(message, 0, 3);
		GridPane.setConstraints(start, 0, 2);
		
		ComboBox<Object> playerCb = new ComboBox<Object>();
		ComboBox<Object> playerCb2 = new ComboBox<Object>();
		Iterator<String> iterator = this.map.keySet().iterator();
		while(iterator.hasNext()) {
			String name = iterator.next();
			String marker = this.map.get(name).marker;
			String playerInfo = "Username: " + name + " Marker: " + marker;
			playerCb.getItems().add(playerInfo);
		}
		playerCb.setPromptText("Select existing player");
		playerCb.setOnAction((ActionEvent ev) -> {
			createPlayer(playerCb, 1);
		});
		GridPane.setConstraints(playerCb, 1, 0);
		
		if(isPvP) {
			Iterator<String> iterator2 = this.map.keySet().iterator();
			while(iterator2.hasNext()) {
				String name = iterator2.next();
				String marker = this.map.get(name).marker;
				String playerInfo = "Username: " + name + " Marker: " + marker;
				playerCb2.getItems().add(playerInfo);
			}
			playerCb2.setPromptText("Select existing player");
			playerCb2.setOnAction((ActionEvent ev) -> {
				createPlayer(playerCb2, 2);
			});
			GridPane.setConstraints(playerCb2, 1, 1);
		}else {
			controller.createPlayer("AI", "$", 2);
			UController.createPlayer("AI", "$", 2);
			TController.createPlayer("AI", "$", 2);
		}
		
		ChoiceBox<String> mode = new ChoiceBox<String>();
		mode.getItems().addAll("Traditional TTT","Ultimate TTT","3D TTT");
		mode.setValue("Traditional TTT");
		GridPane.setConstraints(mode, 1, 2);
		
		EventHandler<MouseEvent> startHandler = new EventHandler<MouseEvent>() {


			public void handle(MouseEvent arg0) {
				String modeChoice = mode.getValue();
				if(isPvP) {
					if(modeChoice.equals("Traditional TTT")) {
						controller.startNewGame(2, timeout);
						stage.close();
						root.setCenter(getBoardDisplay());
					}else if(modeChoice.equals("Ultimate TTT")) {
						UController.startNewGame(2, timeout);
						stage.close();
						root.setCenter(uView.getUltimateBoardDisplay());
					}else {
						TController.startNewGame(2, timeout);
						stage.close();
						root.setCenter(tView.getThreeDDisplay());
					}
					
				}else {
					if(modeChoice.equals("Traditional TTT")) {
						controller.startNewGame(1, timeout);
						stage.close();
						root.setCenter(getBoardDisplay());
					}else if(modeChoice.equals("Ultimate TTT")) {
						UController.startNewGame(1, timeout);
						stage.close();
						root.setCenter(uView.getUltimateBoardDisplay());
					}else {
						TController.startNewGame(1, timeout);
						stage.close();
						root.setCenter(tView.getThreeDDisplay());
					}
				}
			}			
		};
		start.addEventHandler(MouseEvent.MOUSE_CLICKED, startHandler);
		
		GridPane gridPane = new GridPane();
		gridPane.setMinSize(500, 250);
		if(isPvP) {
			gridPane.getChildren().addAll(player1, playerCb, player2, playerCb2, start, message, mode);
		}else {
			gridPane.getChildren().addAll(player1, playerCb, start, message, mode);
		}
		
		stage.setScene(new Scene(gridPane));
		stage.setTitle("Choose player");
		stage.sizeToScene();
		stage.show();
	}
	
	public void initBoard(GridPane gridPane) {
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				board[i][j] = new Tile(i, j);
				board[i][j].setMinSize(100, 100);
				gridPane.add(board[i][j], j, i);
			}
		}
	}
	
	public GridPane getBoardDisplay() {
		Button rotate = new Button("Rotate");
		GridPane gridPane = new GridPane();
		initBoard(gridPane);
		
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
		
		gridPane.setMinSize((int)windowWidth/2, (int)windowHeight/4);
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		gridPane.setVgap(5);
		gridPane.setHgap(5);
		gridPane.setAlignment(Pos.TOP_CENTER);
		gridPane.add(rotate, 1, 3);
		return gridPane;
	}
	
	public class Tile extends StackPane{
		private Rectangle tile;
		private final int length = 100;
		Text text = new Text();
		int row;
		int col;
		
		Tile (int row, int col){
			tile = new Rectangle(length, length);
			tile.setFill(null);
			tile.setStroke(Color.BLACK);
			setAlignment(Pos.CENTER);
			text.setFont(Font.font(36));
			getChildren().addAll(tile, text);
			
			setOnMouseClicked(event ->{
				boolean isInsert = controller.setSelection(row, col, curPlayerNum);
				if(isInsert) {
					text.setText(controller.getMap().get(curPlayerNum).marker);
					checkWin();
					changeTurn();
					if(!isPvP) {
						AIMove();
					}
				}
			});
		}
	}
	
	public void checkWin() {
		int winnerNum = controller.determineWinner();
		if(winnerNum != 0) {
			promptEndWindow(winnerNum);
		}
	}
	
	public void promptEndWindow(int winnerNum) {
		Stage stage = new Stage();
		Text endMessage = new Text("");
		Button end = new Button("Exit");
		
		EventHandler<MouseEvent> endHandler = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				Platform.exit();
			}
			
		};
		end.addEventHandler(MouseEvent.MOUSE_CLICKED, endHandler);
		GridPane.setConstraints(endMessage, 0, 0);
		GridPane.setConstraints(end, 0, 1);
		
		GridPane gridPane = new GridPane();
		if(winnerNum != 3) {
			String winUser = controller.getMap().get(controller.determineWinner()).username;
			String loseUser = controller.getMap().get(controller.determineWinner() % 2 + 1).username;
			if(map.get(winUser) != null) {
				map.get(winUser).win++;
			}
			if(map.get(loseUser) != null) {
				map.get(loseUser).lose++;
			}
			try {
				FileOutputStream fos = new FileOutputStream("playerInfo.ser");
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(map);
				oos.close();
				fos.close();
				System.out.println("Player info stored");
			}catch(IOException ioe) {
				ioe.printStackTrace();
			}
			endMessage = new Text("Game End, winner is " + controller.getMap().get(controller.determineWinner()).username);
			gridPane.getChildren().addAll(endMessage, end);
			String winMusic = "Ta Da-Win.mp3";
			Media sound = new Media(new File(winMusic).toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(sound);
			mediaPlayer.play();
		}else {
			endMessage = new Text("Game Draw, No Winner!");
			gridPane.getChildren().addAll(endMessage, end);
			String drawMusic = "draw.wav";
			Media sound = new Media(new File(drawMusic).toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(sound);
			mediaPlayer.play();
		}
		gridPane.setMinSize(250, 250);
		stage.setScene(new Scene(gridPane));
		stage.sizeToScene();
		stage.show();
	}
	
	public void UpromptEndWindow(int winnerNum) {
		Stage stage = new Stage();
		Text endMessage = new Text("");
		Button end = new Button("Exit");
		
		EventHandler<MouseEvent> endHandler = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				Platform.exit();
			}
			
		};
		end.addEventHandler(MouseEvent.MOUSE_CLICKED, endHandler);
		GridPane.setConstraints(endMessage, 0, 0);
		GridPane.setConstraints(end, 0, 1);
		
		GridPane gridPane = new GridPane();
		if(winnerNum != 3) {
			String winUser = UController.map.get(UController.getWinner()).username;
			String loseUser = UController.map.get(UController.getWinner() % 2 + 1).username;
			if(map.get(winUser) != null) {
				map.get(winUser).win++;
			}
			if(map.get(loseUser) != null) {
				map.get(loseUser).lose++;
			}
			try {
				FileOutputStream fos = new FileOutputStream("playerInfo.ser");
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(map);
				oos.close();
				fos.close();
				System.out.println("Player info stored");
			}catch(IOException ioe) {
				ioe.printStackTrace();
			}
			endMessage = new Text("Game End, winner is " + UController.map.get(UController.getWinner()).username);
			gridPane.getChildren().addAll(endMessage, end);
			String winMusic = "Ta Da-Win.mp3";
			Media sound = new Media(new File(winMusic).toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(sound);
			mediaPlayer.play();
		}else {
			endMessage = new Text("Game Draw, No Winner!");
			gridPane.getChildren().addAll(endMessage, end);
			String drawMusic = "draw.wav";
			Media sound = new Media(new File(drawMusic).toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(sound);
			mediaPlayer.play();
		}
		gridPane.setMinSize(250, 250);
		stage.setScene(new Scene(gridPane));
		stage.sizeToScene();
		stage.show();
	}
	
	public void TpromptEndWindow(int winnerNum) {
		Stage stage = new Stage();
		Text endMessage = new Text("");
		Button end = new Button("Exit");
		
		EventHandler<MouseEvent> endHandler = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				Platform.exit();
			}
			
		};
		end.addEventHandler(MouseEvent.MOUSE_CLICKED, endHandler);
		GridPane.setConstraints(endMessage, 0, 0);
		GridPane.setConstraints(end, 0, 1);
		
		GridPane gridPane = new GridPane();
		if(winnerNum != 3) {
			String winUser = TController.map.get(TController.getWinner()).username;
			String loseUser = TController.map.get(TController.getWinner() % 2 + 1).username;
			if(map.get(winUser) != null) {
				map.get(winUser).win++;
			}
			if(map.get(loseUser) != null) {
				map.get(loseUser).lose++;
			}
			try {
				FileOutputStream fos = new FileOutputStream("playerInfo.ser");
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(map);
				oos.close();
				fos.close();
				System.out.println("Player info stored");
			}catch(IOException ioe) {
				ioe.printStackTrace();
			}
			endMessage = new Text("Game End, winner is " + TController.map.get(TController.getWinner()).username);
			gridPane.getChildren().addAll(endMessage, end);
			String winMusic = "Ta Da-Win.mp3";
			Media sound = new Media(new File(winMusic).toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(sound);
			mediaPlayer.play();
		}else {
			endMessage = new Text("Game Draw, No Winner!");
			gridPane.getChildren().addAll(endMessage, end);
			String drawMusic = "draw.wav";
			Media sound = new Media(new File(drawMusic).toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(sound);
			mediaPlayer.play();
		}
		gridPane.setMinSize(250, 250);
		stage.setScene(new Scene(gridPane));
		stage.sizeToScene();
		stage.show();
	}
	
	public void InitUController() {
		uView = new UltimateView();
		UController =  new UltimateControllerImpl(this, uView, controller, controller.getMap());
		uView.controllerSetup(UController);
	}
	
	public void InitTController() {
		tView = new ThreeDView();
		TController = new ThreeDController(this, tView, controller.getMap());
		tView.controllerSetup(TController);
	}

	public void changeTurn() {
		if(curPlayerNum == 1) {
			curPlayerNum++;
		}else {
			curPlayerNum--;
		}
	}
	
	public void AIMove() {
		boolean isInsert = false;
		while(!isInsert) {
			int row = generateNum();
			int col = generateNum();
			isInsert = controller.setSelection(row, col, curPlayerNum);
			if(isInsert) {
				board[row][col].text.setText(controller.getMap().get(curPlayerNum).marker);
				changeTurn();
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

}

