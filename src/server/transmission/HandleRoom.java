package server.transmission;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import server.customclass.HandleUser;
import tool.Questions;
import transfer.Packet;
import transfer.SetPacketData;
import customclass.Message;
import customclass.Room;


/**
 * @author Qiufan(Andy) Xu 
 * @date CreateTime: May 31, 2015 12:00:34 AM 
 * @version 1.0 
 * @param 
 */

public class HandleRoom extends Room implements Runnable{

	List<HandleStream> handleStreamList = new ArrayList<HandleStream>();
	List<Point> pointsList = new ArrayList<Point>();
	List<Message> messagesList = new ArrayList<Message>();
	Questions q = new Questions();
	String question = null;
	String winner = null;
	long startTime = 0;
	final long GAME_TIME = 90*1000;
	boolean isGameStrat = false;
	boolean isTimeout = false;
	
	public HandleRoom() {
		super();
	}
	
	@Override
	public void run() {
		
		while (!handleStreamList.isEmpty()) {
			
			while (!messagesList.isEmpty()) {
				SetPacketData packet = new SetPacketData(Packet.ROOM_MESSAGE, messagesList.get(0).USERMANE);
				packet.setMessage(messagesList.get(0).CONTENT);
				messagesList.remove(0);
				for (int i = 0; i < handleStreamList.size(); i++) {
					handleStreamList.get(i).sendPacket(packet);
				}
				
				if (isWin(packet.getMessage())) {
					SetPacketData packet2 = new SetPacketData(Packet.WINNER, packet.getMessage().USERMANE);
					for (int i = 0; i < handleStreamList.size(); i++) {
						handleStreamList.get(i).sendPacket(packet2);
					}
				}
			}
			
			while (System.currentTimeMillis()-startTime>GAME_TIME) {
				Packet packet = new Packet(Packet.TIME_OUT, "SYSTEM");
				for (int i = 0; i < handleStreamList.size(); i++) {
					handleStreamList.get(i).sendPacket(packet);
				}
				
				resetGame();
				
			}
			
			while (winner!=null) {
				SetPacketData packet = new SetPacketData(Packet.WINNER, winner);
				for (int i = 0; i < handleStreamList.size(); i++) {
					handleStreamList.get(i).sendPacket(packet);
				}
				
				resetGame();
			}
			
		}
		
	}
	
	/**
	 * Add user to Room with not ready state. 
	 * If the user is the first one joined room, he will be the painter, 
	 * otherwise the guesser.
	 * @param 
	 * @return If Room is full, return false, otherwise return true.
	 */
	public boolean addUser(HandleStream handleStream) {
		if (userList.size()<MAX_NUM) {
			
			int indexOfUser = findUser(handleStream.username);
			if (indexOfUser == -1) {
				
//				/* Judge whether the user is the first one joining the room */
//				boolean isPainter;
//				if (userList.isEmpty()) {
//					isPainter = true;
//				} else {
//					isPainter = false;
//				}
				
				/* create */
				HandleUser user = new HandleUser(handleStream.username);
				user.joinRoom(this);
				userList.add(user);
				handleStreamList.add(handleStream);
				
				return true;
				
			} else {
				
				System.err.println("User("+handleStream.username+") has been in the Room() already");
				return false;
				
			}
			
		} else {
			
			System.err.println("[addUser] Room(" + ROOM_ID + ") is full.");
			return false;
			
		}
		
	}
	
	/**
	 * remove user from Room by username
	 * @param 
	 * @return If user has not been in Room, return false, otherwise return true
	 */
	public boolean removeUser(String username) {
		int indexOfUser = findUser(username);
		if (indexOfUser != -1) {
			userList.remove(indexOfUser);
			removeUserStream(username);
			return true;
		} else {
			System.err.println("[removeUser] User("+username+") isn't in the Room("+ROOM_ID+")");
			removeUserStream(username);
			return false;
		}
	}
	
	private boolean removeUserStream(String username) {
		int indexOfUser = findUserStream(username);
		if (indexOfUser != -1) {
			handleStreamList.remove(indexOfUser);
			return true;
		} else {
			System.err.println("[removeUser] User("+username+") isn't in the Room("+ROOM_ID+")");
			return false;
		}
	}
	
	/**
	 * get user ready by username
	 * @param 
	 * @return If user isn't in Room, return false, otherwise return true
	 */
	public boolean getUserReady(String username) {
		int indexOfUser = findUser(username);
		if (indexOfUser != -1) {
			userList.get(indexOfUser).getReady();
			return true;
		} else {
			System.err.println("[getUserReady] User("+username+") isn't in the Room("+ROOM_ID+")");
			return false;
		}
	}
	
	/**
	 * cancel user ready by username
	 * @param 
	 * @return If user isn't in Room, return false, otherwise return true
	 */
	public boolean cancelUserReady(String username) {
		int indexOfUser = findUser(username);
		if (indexOfUser != -1) {
			userList.get(indexOfUser).cancelReady();
			return true;
		} else {
			System.err.println("[cancelUserReady] User("+username+") isn't in the Room("+ROOM_ID+")");
			return false;
		}
	}
	
	public void startGame() {
		if ( (userList.size()>1) && (isAllReady()) ) {
			isGameStrat = true;
			question = q.getQuestion();
			winner = null;
			startTime = System.currentTimeMillis();
			new Thread(new HandlePoints()).start();
		}
	}
	
	public void sendMessage(Message message) {
		messagesList.add(message);
	}
	
	/* ======== Private Method ======== */
	/**
	 * find user in userList by username
	 * @param 
	 * @return If not find user, return -1, otherwise return index.
	 */
	private int findUser(String username) {
		for (int i = 0; i < userList.size(); i++) {
			if (userList.get(i).USERNAME.equals(username)) {
				return i;
			}
		}
		System.err.println("[findUser] Can't find User("+username+") in Room("+ROOM_ID+")");
		return -1;
	}
	
	private int findUserStream(String username) {
		for (int i = 0; i < handleStreamList.size(); i++) {
			if (handleStreamList.get(i).username.equals(username)) {
				return i;
			}
		}
		return -1;
	}
	
	private void resetGame() {
		startTime = 0;
		isGameStrat = false;
		winner = null;
		question = null;
	}
	
	private boolean isAllReady() {
		for (int i = 1; i < userList.size(); i++) {
			if (!userList.get(i).isReady()) {
				return false;
			}
		}
		return true;
	}
	
	private boolean isWin(Message message) {
		if (message.CONTENT.toLowerCase().contains(question.toLowerCase())) {
			return true;
		}
		return false;
	}
	
	class HandlePoints extends Thread {
		
		@Override
		public void run() {
			while (isGameStrat) {
				
				while (!pointsList.isEmpty()) {
					
					SetPacketData packet = new SetPacketData(Packet.POINTS, userList.get(0).USERNAME);
					packet.setPoint(pointsList.get(0));
					pointsList.remove(0);
					for (int i = 1; i < handleStreamList.size(); i++) {
						handleStreamList.get(i).sendPacket(packet);
					}
					
				}
			}
		}
	}
	
}
