package client.draw;


import client.guess.*;
import client.lobby.Lobby;
import client.login.LoginFX;

import java.awt.Point;
import java.util.ArrayList;

import customclass.Message;
import client.transmission.Client;
import javafx.application.Application;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class DrawPanel extends Application implements EventHandler<ActionEvent>{
	
	Lobby lobby;
	
	Point ptFrom;
	Point ptTo;
	GraphicsContext gc;
	Button sendButton;
	public Button startButton;
	public TextField member;
	public TextField showMessage;
	TextField sendMessage;
	Label question;
	public Client client;
	public Label winner;
	ArrayList<Point> sendArrayListPoints;
	
	public DrawPanel(Client client, Lobby lobby){
		this.client=client;
		this.lobby = lobby;
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
//		Client client = new Client(serverAddress);
		ptFrom = new Point();
		ptTo = new Point();
		primaryStage.setTitle("Drawing Operations Test");  
		Group root = new Group();  
		Canvas canvas = new Canvas(850, 600);  
		gc = canvas.getGraphicsContext2D();
		member = new TextField();
		member.setEditable(false);
		member.setPrefSize(200, 200);
		member.setLayoutX(620);
		member.setLayoutY(20);
		member.setAlignment(Pos.TOP_LEFT);
		
		showMessage = new TextField();
		showMessage.setEditable(false);
		showMessage.setPrefSize(200, 260);
		showMessage.setLayoutX(620);
		showMessage.setLayoutY(230);
		showMessage.setAlignment(Pos.BOTTOM_LEFT);
		
		sendMessage = new TextField();
//      sendMessage.setPrefSize(200, 260);
		sendMessage.setLayoutX(620);
		sendMessage.setLayoutY(500);
		sendMessage.setPrefWidth(200);
		
		sendButton = new Button();
		sendButton.setPrefSize(200, 50);
		sendButton.setLayoutX(620);
		sendButton.setLayoutY(530);
		sendButton.setText("SEND");
		sendButton.setOnAction(this);
		
		startButton = new Button("START");
		startButton.setPrefSize(200, 150);
		startButton.setLayoutX(300);
		startButton.setLayoutY(300);
		
		question = new Label(client.getRequirement());
		question.setPrefSize(150, 100);
		question.setLayoutX(10);
		question.setLayoutY(0);
		
		winner = new Label();
		winner.setPrefSize(300, 200);
		winner.setLayoutX(300);
		winner.setLayoutY(300);
		winner.setFont(javafx.scene.text.Font.font("Times New Roman", 40));
		
		sendButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				client.sendMessage(sendMessage.getText());
				sendMessage.setText("");
			}
		});
		
		startButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				startButton.setVisible(false);
				client.startGame();
			}
		});
		
		canvas.setOnMousePressed(new EventHandler<MouseEvent>() {            
			public void handle(MouseEvent me) {                 
				ptFrom.x = (int) me.getSceneX();
				ptFrom.y = (int) me.getSceneY(); 
				sendArrayListPoints.add(ptFrom);
			}         
		}); 
		canvas.setOnMouseReleased(new EventHandler<MouseEvent>() {            
			public void handle(MouseEvent me) {                 
				ptTo.x=(int) me.getSceneX(); 
				ptTo.y=(int) me.getSceneY();
				sendArrayListPoints.add(ptTo);
			}         
		}); 
		canvas.setOnMouseDragged(new EventHandler<MouseEvent>() {            
			public void handle(MouseEvent me) {                 
				ptTo.x=(int) me.getSceneX(); 
				ptTo.y=(int) me.getSceneY();
				sendArrayListPoints.add(ptTo);
				drawShapesAndSendPoints();
				ptFrom.x = ptTo.x;  
				ptFrom.y = ptTo.y;
				sendArrayListPoints.add(ptFrom);
			}         
		});
		
		root.getChildren().add(canvas); 
		root.getChildren().add(member);
		root.getChildren().add(showMessage);
		root.getChildren().add(sendMessage);
		root.getChildren().add(sendButton);
		root.getChildren().add(startButton);
		root.getChildren().add(question);
		root.getChildren().add(winner);
		
		
		sendMessage.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent arg0) {
				if (arg0.getCode()==KeyCode.ENTER) {
					client.sendMessage(sendMessage.getText());
					sendMessage.setText("");
				}
			}
		}); 
		
		
		Task<Void> stateListener = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				while (client.getRoomID()!=0) {
					
					StringBuilder sb = new StringBuilder();
					
					for(int i=0;i<client.getUserState().size();i++){
						String ready;
						if (client.getUserState().get(i).isReady()) {
							ready = ": Ready";
						} else {
							ready = ": Waiting";
						}
						
						sb.append(client.getUserState().get(i).USERNAME + ready + "\n");
						
//						/* 自动滚动到最后一行 */
//						member.positionCaret(member.getText().length());
					}
					
					updateMessage(sb.toString());
					
					if(client.isGameStart()){
						startButton.setVisible(false);
					}
					
					Thread.sleep(500);
					
				}
				return null;
			}
		};
		
		Task<Void> messageListener = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				
				StringBuilder sb = new StringBuilder();
				
				while (client.getRoomID()!=0) {
					
//					System.out.println("77777777777777777777777777777");
					
					Message message = client.getMessage();
					
					while (message != null) {
						
//						System.out.println("888888888888888");
						
						sb.append("[" + message.USERMANE + "] said:\n  -> " + message.CONTENT + "\n\n");
						
						updateMessage(sb.toString());
						
//						/* 自动滚动到最后一行 */
//						showMessage.positionCaret(showMessage.getText().length());
					}
					
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						System.err.println("[MessagesListener] Fail to sleep MessagesListener");
//						e.printStackTrace();
					}
				}
				return null;
			}
			
		};
		
		Task<Void> win = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				while (!client.isTimeOut()) {
					if(client.whoWin()!=null){
						updateMessage(client.whoWin());
					}
					
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						System.err.println("[Client_Win] Fail to sleep");
//						e.printStackTrace();
					}
				}
				
				if(client.isTimeOut()){
					updateMessage("Time Out & No Winner");
				}
				return null;
			}
			
		};
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent arg0) {
				client.exitRoom();
				primaryStage.close();
				lobby.stage.show();
				stateListener.cancel();
				messageListener.cancel();
				win.cancel();
			}
		});
		

		member.textProperty().bind(stateListener.messageProperty());
		showMessage.textProperty().bind(messageListener.messageProperty());
		winner.textProperty().bind(win.messageProperty());
		
		

		Scene scene = new Scene(root);
		scene.getStylesheets().add(DrawPanel.class.getResource("drawPanel.css").toExternalForm());
		primaryStage.setScene(scene);

		primaryStage.setResizable(false);
		primaryStage.show(); 
		new Thread(messageListener).start();
		new Thread(stateListener).start();
		new Thread(win).start();
	}
	
	public void drawShapesAndSendPoints() {
		//use client API to send points;
		client.sendPointList(sendArrayListPoints);
		sendArrayListPoints.clear();
		gc.strokeLine(ptFrom.x, ptFrom.y, ptTo.x, ptTo.y);
	}
	
	@Override
	public void handle(ActionEvent e) {
		
		if(e.getSource()==sendButton){
			client.sendMessage(sendMessage.getText());
		}
		if(e.getSource()==startButton){
			startButton.setVisible(false);
		}
	}
	
}
