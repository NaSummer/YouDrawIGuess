package transfer;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import server.transmission.HandleRoom;
import customclass.Message;
import customclass.Room;
import customclass.RoomRecurrence;
import customclass.RoomState;
import customclass.User;
import customclass.UserRecurrence;

/**
 * @author Qiufan(Andy) Xu 
 * @date CreateTime: Jun 2, 2015 12:54:34 PM 
 * @version 1.0 
 * @param 
 */

public class Packet implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5700862865311793811L;
	
	public final static int PULSE = 0;
	public final static int PULSE_BACK = 1;
	
	public final static int LOGIN = 2;
	public final static int REGISTER = 6;
	public final static int AUTH_SUCCESSFUL = 3;
	public final static int AUTH_FAIL = 7;
	
	public final static int LOGOUT = 4;
	public final static int LOGOUT_BACK = 5;
	
	public final static int ASK_ROOM_LIST = 11;
	public final static int ROOM_LIST = 10;
	
	public final static int CREARE_ROOM = 12;
	public final static int JOIN_ROOM = 13;
	public final static int EXIT_ROOM = 21;
	public final static int EXIT_ROOM_SUCCESS = 22;
	
	public final static int GET_READY = 18;
	public final static int CANCEL_READY = 19;
	
	public final static int START_GAME = 20;
	
	public final static int ROOM_STATE = 14;

	public final static int ROOM_MESSAGE = 8;
	public final static int ROOM_MESSAGE_BACK = 9;

	public final static int POINTS = 17;
	
	public final static int TIME_OUT = 15;
	public final static int WINNER = 16;
	
	public final int TYPE;
	public final String USERNAME;
	
	/* Variable */
	protected long[] longArrat;
	protected String[] stringArray;
	protected int[] intArray;
	protected double[] doubleArray;
	protected long long1;
	protected String str1; // login, register
	
	public Packet(int type, String username) {
		/**
		 * 
		 * @param 
		 */
		this.TYPE = type;
		this.USERNAME = username;
	}
	
	
	/* =====================get================== */
	/**
	 * getPassword
	 * @param 
	 * @return 
	 */
	public String getPassword() {
		return str1;
	}
	
	/**
	 * 
	 * @param 
	 * @return 
	 */
	public long getRoomID() {
		return long1;
	}
	
	/**
	 * 
	 * @param 
	 * @return 
	 */
	public Point getPoint() {
		if (TYPE==Packet.POINTS) {
			return new Point(intArray[0], intArray[1]);
		} else {
			return null;
		}
	}
	
	/**
	 * 
	 * @param 
	 * @return 
	 */
	public Room getRoom() {
		
		RoomRecurrence room = new RoomRecurrence(long1);
		
		for (int i = 0; i < stringArray.length; i++) {
			
			boolean isPainter = false;
//			if (str1.equals(stringArray[i])) {
//				isPainter = true;
//			}
			
			boolean isReady = false;
			if (intArray[i]==1) {
				isReady = true;
			}
			
			UserRecurrence user = new UserRecurrence(stringArray[i], isPainter, isReady);
			
			room.addUser((User)user);
		}
		
		return (Room) room;
	}
	
	public Message getMessage() {
		if (TYPE == Packet.ROOM_MESSAGE) {
			return new Message(USERNAME, str1);
		}
		return null;
	}
	
	public List<RoomState> getRoomList() {
		List<RoomState> list = new ArrayList<RoomState>();
		for (int i = 0; i < longArrat.length; i++) {
			list.add( new RoomState(longArrat[i], intArray[i]) );
		}
		return list;
	}
	
	public String getQuestion() {
		return str1;
	}
	
	
	
	
	/* =====================set======================= */
	/**
	 * login and register
	 * @param 
	 * @return 
	 */
	public void setPassword(String password) {
		str1 = password;
	}

	public void setRoomID(long roomID) {
		long1 = roomID;
	}
	
	public void setPoint(Point point) {
		intArray = new int[]{(int)point.getX(), (int)point.getY()};
	}
	
	public void setRoom(Room room) {
		long1 = room.ROOM_ID;
		stringArray = new String[room.getUserList().size()];
		intArray = new int[room.getUserList().size()];
		
		for (int i = 0; i < room.getUserList().size(); i++) {
			stringArray[i] = room.getUserList().get(i).USERNAME;
			if (room.getUserList().get(i).isReady()) {
				intArray[i] = 1;
			} else {
				intArray[i] = 0;
			}
//			if (room.getUserList().get(i).isPainter()) {
//				str1 = room.getUserList().get(i).USERNAME;
//			} 
		}
	}

	public void setMessage(String str) {
		str1 = str;
	}
	
	public void setRoomList(List<HandleRoom> roomList) {
		longArrat = new long[roomList.size()];
		intArray = new int[roomList.size()];
		
		for (int i = 0; i < longArrat.length; i++) {
			longArrat[i] = roomList.get(i).ROOM_ID;
			intArray[i] = roomList.get(i).getUserList().size();
		}
	}

	public void setQuestion(String string) {
		str1 = string;
	}
	
}
