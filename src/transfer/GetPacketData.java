package transfer;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import server.customclass.HandleUser;
import customclass.Message;
import customclass.Room;
import customclass.RoomRecurrence;
import customclass.RoomState;
import customclass.User;
import customclass.UserRecurrence;

/**
 * @author Qiufan(Andy) Xu 
 * @date CreateTime: Jun 5, 2015 12:44:07 PM 
 * @version 1.0 
 * @param 
 */

public class GetPacketData extends Packet{

	public GetPacketData(int type, String username) {
		super(type, username);
	}

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
			if (str1.equals(stringArray[i])) {
				isPainter = true;
			}
			
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
	
	
	
}
