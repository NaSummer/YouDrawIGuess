package server.transmission;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Qiufan(Andy) Xu 
 * @date CreateTime: Jun 3, 2015 2:15:44 PM 
 * @version 1.0 
 * @param 
 */

public class SocketListener extends Thread{
	
	ServerSocket serverSocket;
	
	public List<Socket> clientConnection = new ArrayList<Socket>();
	public List<ObjectOutputStream> OOSList = new ArrayList<ObjectOutputStream>();
	
	protected List<HandleRoom> roomList = new ArrayList<HandleRoom>();
	protected List<Thread> roomThreadList = new ArrayList<Thread>();
	
	public SocketListener(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
		new Thread(new ConnectionChecker()).start();
	}
	
	
	@Override
	public void run() {
		
		try {
			
			while (true) {
				
				Socket client = serverSocket.accept();
				
				/* add client in to list */
				clientConnection.add(client);
				System.out.println("The no." + clientConnection.size() + " client connected to the server.");
				
				new Thread(new Authenticate(client, this)).start();
				
			}
			
		} catch (IOException e) {
			System.err.println("[SocketListener] Fail to accept client Socket.");
//			e.printStackTrace();
		}
		
		
	}
	
	
	
	class ConnectionChecker extends Thread {
		
		final static int INTERVAL = 1000;
		
		@Override
		public void run() {
			while (true) {
				
				int i = 0;
				
				while (i < clientConnection.size()) {
					
					if (!clientConnection.get(i).isConnected()) {
						clientConnection.remove(i);
					} else {
						i++;
					}
					
				}
				
				try {
					ConnectionChecker.sleep(INTERVAL);
				} catch (InterruptedException e) {
					System.err.println("[ConnectionChecker] Fail to sleep.");
//					e.printStackTrace();
				}
				
			}
		
		}
		
	}
	
	
	
}
