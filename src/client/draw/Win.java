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
		
		while (!client.isTimeOut()) {
			if(client.whoWin()!=null){
				winner.setText(client.whoWin());
			}
			
			try {
				sleep(500);
			} catch (InterruptedException e) {
				System.err.println("[Client_Win] Fail to sleep");
//				e.printStackTrace();
			}
			
		}
		if(client.isTimeOut()){
			winner.setText("No one wins");
		}
	}
}
