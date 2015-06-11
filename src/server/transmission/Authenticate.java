package server.transmission;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import transfer.SetPacketData;
import transfer.Packet;

/**
 * @author Qiufan(Andy) Xu 
 * @date CreateTime: Jun 3, 2015 3:19:10 PM 
 * @version 1.0 
 * @param 
 */

class Authenticate extends Thread{
	
	Socket client;
	ObjectOutputStream out;
	ObjectInputStream in;
	SocketListener socketListener;
	String username;
	CheckInfo checkInfo = new CheckInfo();
	boolean isAuth = false;
	
	public Authenticate(Socket client, SocketListener socketListener) {
		this.client = client;
		this.socketListener = socketListener;
	}
	
	@Override
	public void run() {
		
		createStream();
		
		try {
			
			do {
				
				SetPacketData firstPacket = (SetPacketData) in.readObject();
				
				username = firstPacket.USERNAME;
				
				sort(firstPacket);
				
			} while (!isAuth);
			
		} catch (ClassNotFoundException | IOException e) {
			System.err.println("[Authenticate] Fail to read object(Packet).");
//			e.printStackTrace();
		}
		
	}
	
	private void sort(SetPacketData packet) {
		
		switch (packet.TYPE) {
		case Packet.REGISTER:
			
			if (!checkInfo.isUserExisted(packet.USERNAME)) {
				checkInfo.writeUserInfo(packet.USERNAME, packet.getPassword());
				authSuccess(packet.USERNAME);
			} else {
				authFail();
			}
			
			break;
		case Packet.LOGIN:
			
			if (!checkInfo.isInfoRight(packet.USERNAME, packet.getPassword())) {
				authSuccess(packet.USERNAME);
			} else {
				authFail();
			}
			
			break;
		default:
			
			System.out.println("[Auth_sort] received wrong type Packet");
			authFail();
			
			break;
		}
		
	}
	
	/**
	 * 
	 * @param 
	 * @return 
	 */
	private void authSuccess(String username) {
		
		socketListener.OOSList.add(out);
		isAuth = true;
		
		Packet back = new Packet(Packet.AUTH_SUCCESSFUL, username);
		
		sendBackPacket(back);
		
		new Thread(new HandleStream(username, socketListener, client, in, out)).start();
		
	}
	
	private void authFail() {
		
		Packet back = new Packet(Packet.AUTH_FAIL, username);
		
		sendBackPacket(back);
		
	}
	
	
	/**
	 * 
	 * @param 
	 * @return 
	 */
	private void sendBackPacket(Packet packet) {
		
		try {
			out.writeObject(packet);
			out.flush();
		} catch (IOException e) {
			System.err.println("[Auth_sendBackPacket] fail to send back Packet.");
//					e.printStackTrace();
		}
		
	}
	
	private void createStream() {
		
		try {
			
			OutputStream os = this.client.getOutputStream();
			out = new ObjectOutputStream(os);
			
			InputStream is = this.client.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			in = new ObjectInputStream(bis);
			
		} catch (IOException e) {
			System.err.println("[Authenticate_CreateStream] Fail to create Stream.");
//			e.printStackTrace();
		}
		
		
	}
	
}
