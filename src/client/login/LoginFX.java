package client.login;

/**
 * @author Qiufan(Andy) Xu 
 * @date CreateTime: Jun 11, 2015 8:27:22 PM 
 * @version 1.0 
 * @param 
 */

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
	
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
    	
        primaryStage.setTitle("You Draw I Guess");
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        
        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);
        
        
        Label serverAddress = new Label("Server Address:");
        grid.add(serverAddress, 0, 1);
        
        TextField serverAddressBox = new TextField();
        grid.add(serverAddressBox, 1, 1);
        

        Label userName = new Label("User Name:");
        grid.add(userName, 0, 2);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 2);
        

        Label pw = new Label("Password:");
        grid.add(pw, 0, 3);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 3);
        
        
        GridPane btnGrid = new GridPane();
        btnGrid.setAlignment(Pos.BOTTOM_RIGHT);
        btnGrid.setHgap(10);
        grid.add(btnGrid, 1, 5);
        
        Button btnSignUp = new Button("Sign up");
        HBox hbBtn1 = new HBox(10);
        hbBtn1.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn1.getChildren().add(btnSignUp);
        btnGrid.add(hbBtn1, 0, 0);
        
        Button btnSignIn = new Button("Sign in");
        HBox hbBtn2 = new HBox(10);
        hbBtn2.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn2.getChildren().add(btnSignIn);
        btnGrid.add(hbBtn2, 1, 0);
        
        
        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);
        
        
        btnSignIn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	
            	if ( (client==null) || (!client.SERVER_ADDRESS.equals(serverAddressBox.getText())) ) {
					client = new Client(serverAddressBox.getText());
				}
            	
            	if (client.login(userTextField.getText(), pwBox.getText())) {
					// TODO open Lobby
            		openLobby();
				} else {
					actiontarget.setFill(Color.FIREBRICK);
					actiontarget.setText("Fail to sign in");
				}
            	
            }
        });
        
        btnSignUp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	
            	if ( (client==null) || (!client.SERVER_ADDRESS.equals(serverAddressBox.getText())) ) {
					client = new Client(serverAddressBox.getText());
				}
            	
            	if (client.register(userTextField.getText(), pwBox.getText())) {
					// TODO open Lobby
            		openLobby();
				} else {
					actiontarget.setFill(Color.FIREBRICK);
					actiontarget.setText("Fail to sign up");
				}
            	
            }
        });

        Scene scene = new Scene(grid, 300, 275);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void openLobby() {
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
