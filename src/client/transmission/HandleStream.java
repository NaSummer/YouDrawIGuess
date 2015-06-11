package client.transmission;

import java.io.IOException;
import java.io.ObjectInputStream;

import transfer.GetPacketData;
import transfer.Packet;

/**
 * @author Qiufan(Andy) Xu 
 * @date CreateTime: Jun 10, 2015 9:28:25 PM 
 * @version 1.0 
 * @param 
 */

public class HandleStream extends Thread{

	Client client;
	ObjectInputStream in;
	
	public HandleStream(Client client, ObjectInputStream ois) {
		this.client = client;
		this.in = ois;
	}
	
	@Override
	public void run() {
		
		try {
			
			while (true) {
				
				GetPacketData packet = (GetPacketData) in.readObject();
				client.pulseListener.setLastPacketTime(System.currentTimeMillis());
				
				switch (packet.TYPE) {
//				case Packet.PULSE:
//					client.pulseListener.setLastPacketTime(System.currentTimeMillis());
//					break;

				case Packet.ROOM_LIST:
					client.roomList = packet.getRoomList();
					break;
					
				case Packet.ROOM_STATE:
					client.room = packet.getRoom();
					break;
					
				case Packet.ROOM_MESSAGE:
					client.messagesList.add(packet.getMessage());
					break;
					
				case Packet.POINTS:
					client.receivedPointList.add(packet.getPoint());
					break;
					
				case Packet.START_GAME:
					startGame(packet);
					break;
					
				case Packet.EXIT_ROOM_SUCCESS:
					exitRoom();
					break;
					
				case Packet.TIME_OUT:
					client.isTimeout = true;
					break;
					
				case Packet.WINNER:
					client.winner = packet.USERNAME;
					break;
					
//				case Packet.value:
//					//TODO
//					break;
					
				default:
					break;
				}
				
			}
			
		} catch (ClassNotFoundException | IOException e) {
			System.err.println("[HandleStream_run]");
//			e.printStackTrace();
		}
	}
	
	private void startGame(GetPacketData packet) {
		client.requirement = packet.getQuestion();
		client.isGameStart = true;
		client.isTimeout = false;
		client.winner = null;
	}
	
	private void exitRoom() {
		client.room = null;
		client.requirement = null;
		client.isGameStart = false;
		client.isTimeout = false;
		client.winner = null;
	}
	
}
