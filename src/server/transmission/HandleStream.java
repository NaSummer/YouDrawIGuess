package server.transmission;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import server.pulse.PulseListener;
import server.pulse.PulseSender;
import transfer.GetPacketData;
import transfer.SetPacketData;
import transfer.Packet;

/**
 * @author Qiufan(Andy) Xu 
 * @date CreateTime: Jun 4, 2015 10:17:38 PM 
 * @version 1.0 
 * @param 
 */

class HandleStream extends Thread{
	
	String username;
	Socket client;
	ObjectOutputStream out;
	ObjectInputStream in;
	SocketListener socketListener;
	HandleRoom room;
	boolean isWaiting = false;
	
	public HandleStream(String username, SocketListener socketListener, Socket client, ObjectInputStream in, ObjectOutputStream out) {
		this.username = username;
		this.client = client;
		this.socketListener = socketListener;
		this.in = in;
		this.out = out;
	}
	
	@Override
	public void run() {
		
		PulseListener pulseListener = new PulseListener(socketListener, client, in, out);
		pulseListener.start();
		
		new Thread(new PulseSender(out, username)).start();
		
		try {
			
			while (true) {
				
				try {
					
					while (isWaiting) {
						HandleStream.sleep(1000L);
					}
					
				} catch (InterruptedException e) {
					System.err.println("[Server_HandleStream_run] Fail to sleep");
//					e.printStackTrace();
				}
				
				GetPacketData packet = (GetPacketData) in.readObject();
				pulseListener.setLastPacketTime(System.currentTimeMillis());
				
				
				switch (packet.TYPE) {
				case Packet.ASK_ROOM_LIST:
					sendRoomList();
					break;
					
				case Packet.CREARE_ROOM:
					createRoom();
					break;
					
				case Packet.JOIN_ROOM:
					joinRoom(packet.getRoomID());
					break;
					
				case Packet.ROOM_MESSAGE:
					this.room.sendMessage(packet.getMessage());
					break;
					
				case Packet.GET_READY:
					this.room.getUserReady(username);
					break;
					
				case Packet.CANCEL_READY:
					this.room.cancelUserReady(username);
					break;
					
				case Packet.EXIT_ROOM:
					exitRoom(username);
					break;
					
				case Packet.POINTS:
					this.room.pointsList.add(packet.getPoint());
					break;
					
				case Packet.START_GAME:
					startGame();
					break;
					
//				case Packet.value:
//					//TODO
//					break;
//					
				default:
					break;
				}
				
			}
			
		} catch (ClassNotFoundException | IOException e) {
			System.err.println("[Server_HandleStream_run] Fail to read Packet.");
//			e.printStackTrace();
		}
		
	}
	
	private void sendRoomList() {
		SetPacketData backPacket = new SetPacketData(Packet.ROOM_LIST, username);
		backPacket.setRoomList(socketListener.roomList);
		sendPacket(backPacket);
	}
	
	private void createRoom() {
		
		HandleRoom room = new HandleRoom();
		this.room = room;
		socketListener.roomList.add(room);
		
		Thread roomThread = new Thread(room);
		socketListener.roomThreadList.add(roomThread);
		
		room.addUser(this);
		
		SetPacketData backPacket = new SetPacketData(Packet.ROOM_STATE, username);
		backPacket.setRoom(room);
		sendPacket(backPacket);
		
//		isWaiting = true;TODO
		
	}
	
	private void joinRoom(long roomID) {
		for (int i = 0; i < socketListener.roomList.size(); i++) {
			if (socketListener.roomList.get(i).ROOM_ID==roomID) {
				this.room = socketListener.roomList.get(i);
				this.room.addUser(this);
				
				SetPacketData backPacket = new SetPacketData(Packet.ROOM_STATE, username);
				backPacket.setRoom(this.room);
				sendPacket(backPacket);
			}
		}
	}
	
	private void startGame() {
		if (room.getUserList().get(0).USERNAME.equals(username)) {
			room.startGame();
			SetPacketData packet = new SetPacketData(Packet.START_GAME, username);
			packet.setQuestion(room.question);
			sendPacket(packet);
		}
	}
	
	private void exitRoom(String username) {
		this.room.removeUser(username);
		Packet packet = new Packet(Packet.EXIT_ROOM_SUCCESS, username);
		sendPacket(packet);
	}
	
	protected void sendPacket(Packet packet) {
		try {
			out.writeObject(packet);
			out.flush();
		} catch (IOException e) {
			System.err.println("[Server_HandleStream_sendPacket]");
//			e.printStackTrace();
		}
	}
	
}
