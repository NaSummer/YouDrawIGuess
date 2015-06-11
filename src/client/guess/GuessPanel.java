package client.guess;

import java.awt.Point;
import java.util.ArrayList;

import client.draw.MessageListener;
import client.transmission.Client;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class GuessPanel extends Application implements EventHandler<ActionEvent>{
Client client;
GraphicsContext gc;
TextField member;
TextField showMessage;
TextField sendMessage;
Label wrong;
Label showWinner;
Button sendButton;
Button readyButton;
Button cancelReadyButton;
Point poFrom;
Point poTo;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch();
	}

//	GuessPanel(Client client){
//		this.client=client;
//	}
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		poFrom = new Point();
		poTo = new Point();
		stage.setTitle("Guess");
		Group root = new Group();
		Canvas canvas = new Canvas(700, 500);
		gc = canvas.getGraphicsContext2D();
		
		
		wrong = new Label();
		wrong.setLayoutX(670);
	    wrong.setLayoutY(450);
		
        member = new TextField();
        member.setEditable(false);
        member.setPrefSize(200, 200);
        member.setLayoutX(620);
        member.setLayoutY(20);
        
        showMessage = new TextField();
        showMessage.setEditable(false);
        showMessage.setPrefSize(200, 260);
        showMessage.setLayoutX(620);
        showMessage.setLayoutY(230);
        
        sendMessage = new TextField();
//        sendMessage.setPrefSize(200, 260);
        sendMessage.setLayoutX(620);
        sendMessage.setLayoutY(500);
        sendMessage.setPrefWidth(200);
        
        sendButton = new Button();
        sendButton.setPrefSize(200, 50);
        sendButton.setLayoutX(620);
        sendButton.setLayoutY(530);
        sendButton.setText("SEND");
        
        readyButton = new Button("Get Ready");
        readyButton.setPrefSize(200, 150);
        readyButton.setLayoutX(300);
        readyButton.setLayoutY(300);
        
        cancelReadyButton = new Button("Cancel Ready");
        cancelReadyButton.setPrefSize(200, 150);
        cancelReadyButton.setLayoutX(300);
        cancelReadyButton.setLayoutY(300);
        cancelReadyButton.setVisible(false);
	    
	    showWinner = new Label();
	    showWinner.setPrefSize(400, 300);
	    showWinner.setVisible(false);
	    showWinner.setLayoutX(250);
	    showWinner.setLayoutY(75);
	    showWinner.setFont(javafx.scene.text.Font.font("Times New Roman", 40));
	    
		root.getChildren().add(canvas);
		root.getChildren().add(member);
		root.getChildren().add(showMessage);
		root.getChildren().add(sendMessage);
		root.getChildren().add(showWinner);
		root.getChildren().add(sendButton);
		root.getChildren().add(readyButton);
		root.getChildren().add(wrong);
		stage.setScene(new Scene(root));
		stage.show();
		receive();
		new Thread(new MessageListener(client, showMessage)).start();
	}
	@Override
	public void handle(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==sendButton){
			client.sendMessage(sendMessage.getText());
		}
		if(e.getSource()==readyButton){
			client.getReady();
			readyButton.setVisible(false);
			cancelReadyButton.setVisible(true);
		}
		if(e.getSource()==cancelReadyButton){
			client.cancelReady();
			cancelReadyButton.setVisible(false);
			readyButton.setVisible(true);
		}
	}
	
	/*------Receive thread--------*/
	public void receive() {
		// TODO Auto-generated method stub
		new Thread(){
			@Override
			public void run() {
				while (gc == null) {
					
				}
				while (gc != null) {
					ArrayList<Point> receivePoints = client.getPointList();
				Platform.runLater(new Runnable() {
						@Override
						public void run() {
							for (int i = 0; i < receivePoints.size() - 1; i++) {
								gc.strokeLine(receivePoints.get(i).getX(),receivePoints.get(i).getY(),receivePoints.get(i+1).getX(), receivePoints.get(i+1).getY());
								}
						}
				});
			}
			}
		}.start();
}

}
