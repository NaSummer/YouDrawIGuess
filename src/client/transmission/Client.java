package client.transmission;

import java.awt.Point;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import tool.Tool;
import transfer.Packet;
import transfer.SetPacketData;
import customclass.Message;
import customclass.Room;
import customclass.RoomState;
import customclass.User;

/**
 * 
 * @author Qiufan(Andy) Xu 
 * @date CreateTime: May 30, 2015 11:34:55 PM 
 * @version 1.0 
 */

public class Client {
	
	protected final static int PORT = 20488;
	protected final static int TIMEOUT = 5*1000;
	
	public final long DEVICE_ID;
	public final String SERVER_ADDRESS;
	
	protected Socket socket;
	protected ObjectOutputStream out;
	protected ObjectInputStream in;
	
	protected HandleStream handleStream;
	protected PulseListener pulseListener;
	protected Thread handleStreamThread;
	protected Thread pulseListenerThread;
	protected Thread pulseSender;
	
	protected String username;
	protected boolean isAuth = false;
	
	protected List<RoomState> roomList = new ArrayList<RoomState>();
	protected Room room;
	
	protected String requirement;
	protected boolean isGameStart;
	
	protected List<Message> messagesList = new ArrayList<Message>();
	
	protected List<Point> sendingPointList = new ArrayList<Point>();
	protected List<Point> receivedPointList = new ArrayList<Point>();
	
	protected String winner;
	protected boolean isTimeout;

	public Client(String serverAddress) {
		this.DEVICE_ID = Tool.generateID();
		this.SERVER_ADDRESS = serverAddress;
		
		connectServer();
	}
	
	
	
	/* ========login======== */
	/**
	 * login server
	 * @param 
	 * @return [boolean] whether login successful
	 */
	public boolean login(String username, String password) {
		try {
			SetPacketData packet = new SetPacketData(Packet.LOGIN, username);
			packet.setPassword(password);
			sendPacket(packet);
			Packet receivedPacket;
			receivedPacket = (Packet) in.readObject();
			if (receivedPacket.TYPE==Packet.AUTH_SUCCESSFUL) {
				authSuccess();
			}
		} catch (ClassNotFoundException | IOException e) {
			System.err.println("[Client_login]");
//			e.printStackTrace();
		}
		return isAuth;
	}
	
	
	
	/* ========register======== */
	/**
	 * register a new user in server
	 * @param 
	 * @return [boolean] whether register successful
	 */
	public boolean register(String username, String password) {
		try {
			SetPacketData packet = new SetPacketData(Packet.REGISTER, username);
			packet.setPassword(password);
			sendPacket(packet);
			Packet receivedPacket;
			receivedPacket = (Packet) in.readObject();
			if (receivedPacket.TYPE==Packet.AUTH_SUCCESSFUL) {
				authSuccess();
			}
		} catch (ClassNotFoundException | IOException e) {
			System.err.println("[Client_register]");
//			e.printStackTrace();
		}
		return isAuth;		
	}
	
//	/**
//	 * check whether username has existed in server
//	 * @param 
//	 * @return [boolean] whether username has existed in server
//	 */
//	public boolean isUserExisted(String username) {
//		// TODO Auto-generated method stub
//		
//	}
	
	
	
	/* ========select room======== */
//	* <p>get Room List</p>
//	* <p>If you want to create and join a new room, you need to do 
//		* (1)createRoom, 
//		* (2)getRoomList again, 
//		* (3)find a empty room, 
//		* (4)joinRoom, 
//		* or use createAndJoinRoom.</p>
	/**
	 * @return [ArrayList<Room>]
	 */
	public ArrayList<RoomState> getRoomList() {
//		System.out.println("[Client_getRoomList]");
		Packet packet = new Packet(Packet.ASK_ROOM_LIST, username);
		sendPacket(packet);
		return (ArrayList<RoomState>) roomList;
	}
	
	/**
	 * create a Room
	 * @return [boolean] whether create a room successfully
	 */
	public boolean createRoom() {
//		System.out.println("[Client_createRoom]");
		Packet packet = new Packet(Packet.CREARE_ROOM, username);
		sendPacket(packet);
		
		long time = System.currentTimeMillis();
		while ((System.currentTimeMillis()-time<TIMEOUT)&&(room==null)) {
			
		}
		
		if (room!=null) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * joinRoom
	 * @param 
	 * @return [boolean] whether join a room successfully
	 */
	public boolean joinRoom(long roomID) {
		System.out.println("[Client_joinRoom]");
		SetPacketData packet = new SetPacketData(Packet.JOIN_ROOM, username);
		packet.setRoomID(roomID);
		sendPacket(packet);

		long time = System.currentTimeMillis();
		while ((System.currentTimeMillis()-time<TIMEOUT)&&(room==null)) {
			
		}
		
		if (room!=null) {
			return true;
		} else {
			return false;
		}
	}
	
//	/**
//	 * create a room and join it
//	 * @return [boolean] whether successful
//	 */
//	public boolean createAndJoinRoom() {
//		// TODO Auto-generated method stub
//		
//	}
	
	/**
	 * exitRoom
	 * @return [boolean] is successful
	 */
	public boolean exitRoom() {
//		System.out.println("[Client_exitRoom]");
		SetPacketData packet = new SetPacketData(Packet.EXIT_ROOM, username);
		sendPacket(packet);
		
		long time = System.currentTimeMillis();
		while ((System.currentTimeMillis()-time<TIMEOUT)&&(room!=null)) {
			
		}
		
		if (room==null) {
			return true;
		} else {
			return false;
		}
	}
	
	
	
	/* ========Pane======== */
	
	/* ====Message==== */
	/**
	 * sendMessage
	 * @param 
	 * @return is successful
	 */
	public void sendMessage(String str) {
		System.out.println("=========================================[Client_sendMessage]");
		SetPacketData packet = new SetPacketData(Packet.ROOM_MESSAGE, username);
		packet.setMessage(str);
		sendPacket(packet);
	}
	
	/**
	 * <p>getMessage</p>
	 * <p>use Message.USERNAME Message.CONTENT</p>
	 * 
	 * @return [Message] If there is no new message, return null.
	 */
	public Message getMessage() {
		if (!messagesList.isEmpty()) {
			System.out.println("[Client_getMessage]");
			Message message = messagesList.get(0);
			messagesList.remove(0);
			return message;
		} else {
			return null;
		}
		
	}
	
	/* ====State====*/
	/**
	 * getReady
	 * @return [boolean] is successful
	 */
	public void getReady() {
		System.out.println("=============================[Client_getReady]");
		SetPacketData packet = new SetPacketData(Packet.GET_READY, username);
		sendPacket(packet);
	}
	
	/**
	 * cancelReady
	 * @return [boolean] is successful
	 */
	public void cancelReady() {
		System.out.println("========================[Client_cancelReady]");
		SetPacketData packet = new SetPacketData(Packet.CANCEL_READY, username);
		sendPacket(packet);
	}
	
	public void startGame() {
		System.out.println("==================[Client_startGame]");
		if (isPainter()) {
			Packet packet = new Packet(Packet.START_GAME, username);
			sendPacket(packet);
		}
	}
	
	public boolean isGameStart() {
//		System.out.println("==================[Client_isGameStart]");
		return isGameStart;
	}
	
	public String getRequirement() {
		System.out.println("=====================[Client_getRequirement]");
		if (isPainter()) {
			return requirement;
		} else {
			return null;
		}
	}
	
	/**
	 * who Win
	 * @return [String] If nobody win, return null, otherwise return winner username
	 */
	public String whoWin() {
//		System.out.println("[Client_whoWin]");
		if (winner!=null) {
			String winner = this.winner;
			isTimeout = false;
			return winner;
		} else {
			return null;
		}
	}
	
	/**
	 * isTimeOut
	 * @return [boolean] If time is out, return true.
	 */
	public boolean isTimeOut() {
//		System.out.println("[Client_isTimeOut]");
		if (isTimeout) {
			winner = null;
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * getRoom
	 * @return Room
	 */
	public long getRoomID() {
//		System.out.println("[Client_getRoomID]");
		if (room!=null) {
			return this.room.getServerRoomID();
		} else {
			return -1;
		}
	}
	
	/**
	 * 
	 * @param 
	 * @return 
	 */
	public List<User> getUserState() {
//		System.out.println("[Client_getUserState]");
		return room.getUserList();
	}
	
	/* ====Point====*/
	/**
	 * send Point List
	 * @param 
	 * @return [boolean] is successful
	 */
	public boolean sendPointList(ArrayList<Point> points) {
		this.sendingPointList.addAll(points);
		System.out.println("[Client_sendPointList]");
		return true;
	}
	
	/**
	 * getPointList
	 * @return [ArrayList<Point>] If have no new points, return null
	 */
	public ArrayList<Point> getPointList() {
		ArrayList<Point> list = new ArrayList<Point>();
		list.addAll(receivedPointList);
		receivedPointList.clear();
//		System.out.println("[Client_getPointList]");
		return list;
	}
	
	
	/* ========private method======== */
	
	/**
	 * 
	 * @param 
	 * @return 
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	private void connectServer() {
		
		try {
			
			socket = new Socket(SERVER_ADDRESS, PORT);
			
			OutputStream os = socket.getOutputStream();
			out = new ObjectOutputStream(os);
			
			InputStream is = socket.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			in = new ObjectInputStream(bis);
			
		} catch (IOException e) {
			System.err.println("[Client_connectServer] Fail to connect server or disconnect.");
//			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * 
	 * @param 
	 * @return 
	 */
	private void sendPacket(Packet packet) {
		try {
			out.writeObject(packet);
			out.flush();
			System.out.println("[Client] sendPacket");
		} catch (IOException e) {
			System.err.println("[Client_sendPacket]");
//			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param 
	 * @return 
	 */
	private void authSuccess() {
		isAuth = true;
		
		pulseListener = new PulseListener(this);
		pulseListenerThread = new Thread(pulseListener);
		pulseListenerThread.start();
		
		pulseSender = new Thread(new PulseSender(out, username));
		pulseSender.start();
		
		handleStream = new HandleStream(this, in);
		handleStreamThread = new Thread(handleStream);
		handleStreamThread.start();
		
	}
	
	/**
	 * 
	 * @param 
	 * @return 
	 */
	private boolean isPainter() {
		if (room != null) {
			if (room.getUserList().get(0).USERNAME.equals(username)) {
				return true;
			}
//			for (int i = 0; i < room.getUserList().size(); i++) {
//				if (room.getUserList().get(i).isPainter()) {
//					if (room.getUserList().get(i).USERNAME.equals(username)) {
//						return true;
//					}
//				}
//			}
			
		} 
		return false;
	}
	
	
	
}

