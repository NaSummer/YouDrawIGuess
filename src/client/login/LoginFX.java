package client.login;

/**
 * @author Qiufan(Andy) Xu 
 * @date CreateTime: Jun 11, 2015 8:27:22 PM 
 * @version 1.0 
 * @param 
 */

import com.sun.glass.events.KeyEvent;
import com.sun.javafx.scene.KeyboardShortcutsHandler;

import client.lobby.Lobby;
import client.transmission.Client;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
 
public class LoginFX extends Application {
	
	Client client;
	
	Scene scene;
	Stage primaryStage;
	
	GridPane grid;
	Text scenetitle;
	Label serverAddress;
	TextField serverAddressBox;
	Label userName;
	TextField userTextField;
	Label pw;
	PasswordField pwBox;
	GridPane btnGrid;
	Button btnSignUp;
	HBox hbBtn1;
	Button btnSignIn;
	HBox hbBtn2;
	Text actiontarget;
	
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
    	
    	this.primaryStage = primaryStage;
    	
        primaryStage.setTitle("You Draw I Guess");
        
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        
        scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);
        
        
        serverAddress = new Label("Server Address:");
        grid.add(serverAddress, 0, 1);
        
        serverAddressBox = new TextField();
        grid.add(serverAddressBox, 1, 1);
        

        userName = new Label("User Name:");
        grid.add(userName, 0, 2);

        userTextField = new TextField();
        grid.add(userTextField, 1, 2);
        

        pw = new Label("Password:");
        grid.add(pw, 0, 3);

        pwBox = new PasswordField();
        grid.add(pwBox, 1, 3);
        
        
        btnGrid = new GridPane();
        btnGrid.setAlignment(Pos.BOTTOM_RIGHT);
        btnGrid.setHgap(10);
        grid.add(btnGrid, 1, 5);
        
        btnSignUp = new Button("Sign up");
        hbBtn1 = new HBox(10);
        hbBtn1.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn1.getChildren().add(btnSignUp);
        btnGrid.add(hbBtn1, 0, 0);
        
        btnSignIn = new Button("Sign in");
        hbBtn2 = new HBox(10);
        hbBtn2.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn2.getChildren().add(btnSignIn);
        btnGrid.add(hbBtn2, 1, 0);
        
        
        actiontarget = new Text();
        grid.add(actiontarget, 1, 6);
        
//        pwBox.setOnAction(new ());
        
        btnSignIn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	trySignIn(serverAddressBox.getText(), userTextField.getText(), pwBox.getText());
            }
        });
        
        btnSignUp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	trySignUp(serverAddressBox.getText(), userTextField.getText(), pwBox.getText());
            }
        });

        scene = new Scene(grid, 300, 275);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    
    
    
    private void trySignIn(String serverAddress, String username, String password) {
    	
    	if ( (client==null) || (!client.SERVER_ADDRESS.equals(serverAddress)) ) {
			client = new Client(serverAddress);
		}
    	
    	if (client.login(username, password)) {
    		openLobby();
		} else {
			actiontarget.setFill(Color.FIREBRICK);
			actiontarget.setText("Fail to sign in");
		}
    }
    
    private void trySignUp(String serverAddress, String username, String password) {
    	
    	if ( (client==null) || (!client.SERVER_ADDRESS.equals(serverAddress)) ) {
			client = new Client(serverAddress);
		}
    	
    	if (client.register(username, password)) {
    		openLobby();
		} else {
			this.actiontarget.setFill(Color.FIREBRICK);
			this.actiontarget.setText("Fail to sign up");
		}
    }
    
    private void openLobby() {
    	this.primaryStage.close();
    	Platform.runLater(new Runnable() {
		    public void run() {             
		        try {
					new Lobby(client).start(new Stage());
				} catch (Exception e) {
					System.err.println("[LoginFX_openLobby] Fail to open Lobby");
//					e.printStackTrace();
				}
		    }
    	});
    }
    
}
