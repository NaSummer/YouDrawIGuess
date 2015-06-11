package client.draw;

import javafx.scene.control.TextField;
import client.transmission.Client;


public class MessageListener extends Thread{
	Client client;
	TextField showMessage;
	public MessageListener(Client client,TextField showMessage) {
		// TODO Auto-generated constructor stub
		this.client=client;
		this.showMessage=showMessage;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			while (client.getMessage() != null) {
				showMessage.appendText("[" + client.getMessage().USERMANE + "] said:\n  -> " + client.getMessage().CONTENT + "\n\n");
				/* 自动滚动到最后一行 */
				showMessage.positionCaret(showMessage.getText().length());
			}
			try {
				MessageListener.sleep(500);
			} catch (InterruptedException e) {
				System.err.println("[MessagesListener] Fail to sleep MessagesListener");
				e.printStackTrace();
			}
		}
	}
}
