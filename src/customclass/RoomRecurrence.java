package customclass;

import server.customclass.HandleUser;

/**
 * @author Qiufan(Andy) Xu 
 * @date CreateTime: Jun 10, 2015 11:28:46 PM 
 * @version 1.0 
 * @param 
 */

public class RoomRecurrence extends Room{
	
	public RoomRecurrence(long roomID) {
		serverRoomID = roomID;
	}
	
	public void addUser(User user) {
		userList.add( (HandleUser) user );
	}
	
}
