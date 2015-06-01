package customclass;

/**
 * @author Qiufan(Andy) Xu 
 * @date CreateTime: May 31, 2015 4:56:20 PM 
 * @version 1.0 
 * @param 
 */

public class User {

	public final String USERNAME;
	protected Room whichRoom = null;
	
	public User(String username) {
		/**
		 * 
		 * @param 
		 */
		this.USERNAME = username;
	}
	
	/**
	 * change user state to a room
	 * @param Room room
	 * @return If the user is in a room, return false, otherwise true.
	 */
	public boolean joinRoom(Room room) {
		if (whichRoom == null) {
			
			this.whichRoom = room;
			System.out.println("[User] User("+USERNAME+") is joining Room("+room.ROOM_ID+")");
			return true;
			
		} else {
			
			System.err.println("[User] User("+USERNAME+") is in Room("+whichRoom.ROOM_ID+") now, can't join the new room("+room.ROOM_ID+")");
			return false;
			
		}
	}
	
}
