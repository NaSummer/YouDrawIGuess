package client.lobby;

import java.util.ArrayList;

import customclass.RoomState;
import client.draw.DrawPanel;
import client.guess.GuessPanel;
import client.login.LoginFX;
import client.transmission.Client;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Lobby extends Application implements EventHandler<ActionEvent>{
	
	Lobby lobby = this;
	public Stage stage;
	
	Group root;
	Canvas canvas;
	Button createNewRoom;
	Button refreshRoom;
	Client client;
	ArrayList<Button> buttons = new ArrayList<Button>();
	
	public Lobby(Client client) {
		this.client=client;
	}
	
	@Override
	public void start(Stage stage0) throws Exception {
		
		this.stage = stage0;
		
		stage.setTitle("Game Lobby");
		root = new Group();  
		canvas = new Canvas(850, 600);
		createNewRoom = new Button("Create A New Room");
		createNewRoom.setLayoutX(270);
		createNewRoom.setLayoutY(570);
		createNewRoom.setOnAction(this);
		
		refreshRoom = new Button("Refresh Game lobby");
		refreshRoom.setLayoutX(430);
		refreshRoom.setLayoutY(570);
		refreshRoom.setOnAction(this);
		
		root.getChildren().add(canvas);
		root.getChildren().add(createNewRoom);
		root.getChildren().add(refreshRoom);

		
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent arg0) {
				stage.close();
			}
		});
		
		Scene scene = new Scene(root);
		scene.getStylesheets().add(Lobby.class.getResource("lobbyStyle.css").toExternalForm());
		stage.setScene(scene);

		stage.setResizable(false);
		stage.show();
	}
	
	@Override
	public void handle(ActionEvent e) {
		
		if(e.getSource()==createNewRoom){
			client.createRoom();
			stage.hide();
			Platform.runLater(new Runnable() {
				public void run() {             
					try {
						new DrawPanel(client, lobby).start(new Stage());
					} catch (Exception e) {
						System.err.println("[Lobby] Fail to open DrawPanel");
//						e.printStackTrace();
					}
				}
			});
		}
		
		if(e.getSource()==refreshRoom){
			ArrayList<RoomState> roomList = client.getRoomList();
			if (roomList!=null) {
				for(int i=0;i<roomList.size();i++){
					Button room = new Button("Room ID: "+roomList.get(i).ROOM_ID+"\n"
							+"Player number: "+roomList.get(i).USER_NUM+"/"+roomList.get(i).MAX_NUM);
					buttons.add(room);
				}
				for(int i=0;i<buttons.size();i++){
					buttons.get(i).setPrefSize(200, 100);
					buttons.get(i).setLayoutX(300*(i%3)+25);
					buttons.get(i).setLayoutY((int)(i/3)*200);
					buttons.get(i).setOnAction(this);
					root.getChildren().add(buttons.get(i));
				}
			}
		}
		
		for(int i=0;i<buttons.size();i++){
			if(e.getSource()==buttons.get(i)){
				client.joinRoom(client.getRoomList().get(i).ROOM_ID);
				stage.hide();
				Platform.runLater(new Runnable() {
					public void run() {             
						try {
							new GuessPanel(client, lobby).start(new Stage());
						} catch (Exception e) {
							System.err.println("[Lobby] Fail to open GuessPanel");
//							e.printStackTrace();
						}
					}
				});
			}
		}
		
	}
	
}
