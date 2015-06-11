package client.draw;


import javafx.scene.control.Label;
import client.transmission.Client;

public class Win extends Thread {
	Client client;
	Label winner;
	public Win(Client client,Label winner){
		this.client=client;
		this.winner=winner;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (!client.isTimeOut()) {
			if(client.whoWin()!=null){
				winner.setText(client.whoWin());
			}
		}
		if(client.isTimeOut()){
			winner.setText("No one wins");
		}
	}
}
