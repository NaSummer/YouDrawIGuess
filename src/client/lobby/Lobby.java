package client.lobby;

import client.draw.DrawPanel;
import client.transmission.Client;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Lobby extends Application implements EventHandler<ActionEvent>{
Button createNewRoom;
Button refreshRoom;
Client client;
	public Lobby(Client client) {
		this.client=client;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		stage.setTitle("Game Lobby");
        Group root = new Group();  
        Canvas canvas = new Canvas(850, 600);
        createNewRoom = new Button("Create A New Room");
        createNewRoom.setLayoutX(350);
        createNewRoom.setLayoutY(600);
        createNewRoom.setOnAction(this);
        
        refreshRoom = new Button("Refresh Game lobby");
        createNewRoom.setLayoutX(350);
        createNewRoom.setLayoutY(600);
        createNewRoom.setOnAction(this);
        
        root.getChildren().add(canvas);
        root.getChildren().add(createNewRoom);
        root.getChildren().add(refreshRoom);
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
	}

	@Override
	public void handle(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==createNewRoom){
			client.createRoom();
			DrawPanel dp = new DrawPanel(client);
		}
	}

}
