package server.pulse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import server.transmission.SocketListener;
import transfer.Packet;

/**
 * @author Qiufan(Andy) Xu 
 * @date CreateTime: Jun 4, 2015 10:47:38 PM 
 * @version 1.0 
 * @param 
 */

public class PulseSender extends Thread {

	String username;
	ObjectOutputStream out;
	SocketListener socketListener;
	final int INTERVAL_TIME = 20*1000;
	
	public PulseSender(ObjectOutputStream oos, String username) {
		this.out = oos;
		this.username = username;
	}
	
	@Override
	public void run() {
		while (true) {
			
			try {
				
				Packet back = new Packet(Packet.PULSE, username);
				out.writeObject(back);
				out.flush();
				
			} catch (IOException e) {
				System.err.println("[PulseSender] Fail to send back pulse.");
//				e.printStackTrace();
			}
			
			
			
		}
	}
	
}
