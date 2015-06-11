package client.guess;

import customclass.Room;
import javafx.scene.control.TextField;
import client.transmission.Client;

public class StateListener extends Thread {
	Client client;
	TextField member;
	Room room;
	public StateListener(Client client,TextField member){
		this.client=client;
		this.member=member;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			while (client.joinRoom(client.getRoomID())) {
				for(int i=0;i<client.getUserState().size();i++){
				member.appendText(client.getUserState().get(i).USERNAME + client.getUserState().get(i).isReady() + "\n\n");
				/* 自动滚动到最后一行 */
				member.positionCaret(member.getText().length());
				try {
					sleep(150);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			}
		}
	}
}
