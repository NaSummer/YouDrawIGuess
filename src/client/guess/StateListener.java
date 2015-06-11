package client.guess;

import customclass.Room;
import javafx.scene.control.TextField;
import client.transmission.Client;

public class StateListener extends Thread {
	Client client;
	TextField member;
	Room room;
	StateListener(Client client,TextField member){
		this.client=client;
		this.member=member;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			while (client.joinRoom(room)) {
				
			}
		}
	}
}
