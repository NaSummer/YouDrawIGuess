package client.guess;

import java.awt.Point;
import java.util.ArrayList;

import com.sun.java.swing.plaf.motif.MotifBorders.MenuBarBorder;

import client.draw.MessageListener;
import client.draw.Win;
import client.lobby.Lobby;
import client.transmission.Client;
import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GuessPanel extends Application implements EventHandler<ActionEvent>{
	
	Lobby lobby;
	
	public Client client;
	
	GraphicsContext gc;
	public TextField member;
	public TextField showMessage;
	TextField sendMessage;
	Label wrong;
	public Label showWinner;
	Button sendButton;
	public Button readyButton;
	public Button cancelReadyButton;
	Point poFrom;
	Point poTo;
	
	public GuessPanel(Client client, Lobby lobby){
		this.client=client;
		this.lobby = lobby;
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		
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
		member.setAlignment(Pos.TOP_LEFT);
		
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
		
		sendMessage.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent arg0) {
				if (arg0.getCode()==KeyCode.ENTER) {
					client.sendMessage(sendMessage.getText());
					sendMessage.setText("");
				}
			}
		}); 
		
		sendButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				client.sendMessage(sendMessage.getText());
				sendMessage.setText("");
			}
		});
		
		readyButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				readyButton.setVisible(false);
				client.getReady();
			}
		});
		
		
		
		Task<Void> messageListener = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				
				System.out.println("2");
				StringBuilder sb = new StringBuilder();
				
				while (client.getRoomID()!=0) {
					
//					System.out.println("3");
					while (client.getMessage() != null) {
						
						sb.append("[" + client.getMessage().USERMANE + "] said:/n  -> " + client.getMessage().CONTENT + "\n\n");
						System.out.println("4");
						
						updateMessage(sb.toString());
//						/* 自动滚动到最后一行 */
//						showMessage.positionCaret(showMessage.getText().length());
					}
					
					try {
						Thread.sleep(500);
						System.out.println("5");
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
//					System.out.println("6");
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
		
		Task<Void> stateListener = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				while (client.getRoomID()!=0) {
					
					StringBuilder sb = new StringBuilder();
//					System.out.println("7");
					
					for(int i=0;i<client.getUserState().size();i++){
//						System.out.println("8");
						String ready;
						if (client.getUserState().get(i).isReady()) {
							ready = ": Ready";
						} else {
							ready = ": Waiting";
						}
						
						sb.append(client.getUserState().get(i).USERNAME + ready + "/n");
						
//						/* 自动滚动到最后一行 */
//						member.positionCaret(member.getText().length());
					}
					
					updateMessage(sb.toString());
					
					if(client.isGameStart()){
						readyButton.setVisible(false);
						cancelReadyButton.setVisible(false);
					}
					
					Thread.sleep(500);
					
				}
				return null;
			}
		};
		
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent arg0) {
				stage.close();
				client.exitRoom();
				System.out.println("1");
				lobby.stage.show();
				stateListener.cancel();
				messageListener.cancel();
				win.cancel();
			}
		});
		
		showMessage.textProperty().bind(messageListener.messageProperty());
		showWinner.textProperty().bind(win.messageProperty());
		member.textProperty().bind(stateListener.messageProperty());
		
		
		stage.setScene(new Scene(root));
		stage.show();
		receive();
		
		new Thread(messageListener).start();
		new Thread(stateListener).start();
		new Thread(win).start();
		
	}
	
	@Override
	public void handle(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("9");
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
		System.out.println("10");
		new Thread(){
			@Override
			public void run() {
				System.out.println("11");
				while (gc == null) {
					System.out.println("12");
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						System.err.println("[GuessPanel_receive] Fail to sleep");
//						e.printStackTrace();
					}
				}
				while (gc != null) {
//					System.out.println("13");
					ArrayList<Point> receivePoints = client.getPointList();
					
					if (receivePoints.size()==0) {
						
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							System.err.println("[GuessPanel_receive] Fail to sleep");
//						e.printStackTrace();
						}
						
					} else {
						
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								System.out.println("14");
								for (int i = 0; i < receivePoints.size() - 1; i++) {
									System.out.println("15");
									gc.strokeLine(receivePoints.get(i).getX(),receivePoints.get(i).getY(),receivePoints.get(i+1).getX(), receivePoints.get(i+1).getY());
								}
							}
						});
						
					}
				}
			}
		}.start();
	}
	
}
