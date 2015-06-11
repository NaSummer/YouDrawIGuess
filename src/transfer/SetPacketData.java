package transfer;

import java.awt.Point;
import java.util.List;

import server.transmission.HandleRoom;
import customclass.Room;

/**
 * @author Qiufan(Andy) Xu 
 * @date CreateTime: Jun 4, 2015 9:35:20 PM 
 * @version 1.0 
 * @param 
 */

public class SetPacketData extends GetPacketData{

	public SetPacketData(int type, String username) {
		
		super(type, username);
		
	}
	
	/**
	 * login and register
	 * @param 
	 * @return 
	 */
	public void setPassword(String password) {
		super.str1 = password;
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
			if (room.getUserList().get(i).isPainter()) {
				str1 = room.getUserList().get(i).USERNAME;
			} 
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
