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
	protected boolean isPainter = false;
	protected boolean isReady = false;
	
	public User(String username) {
		/**
		 * 
		 * @param 
		 */
		this.USERNAME = username;
	}
	
	public boolean isReady() {
		return isReady;
	}
	
	public boolean isPainter() {
		return isReady;
	}
	
	public void userRecurrence(boolean isPainter, boolean isReady) {
		this.isPainter = isPainter;
		this.isReady = isReady;
	}
	
	/* ==================handleUser===================== */
	/**
	 * change user state to be in a room
	 * @param 
	 * @return If the user is in a room, return false, otherwise true.
	 */
	public boolean joinRoom(Room room) {
		if (whichRoom == null) {
			
			if (room.getUserList().isEmpty()) {
				isPainter = true;
			} else {
				isPainter = false;
			}
			
			this.whichRoom = room;
			
			System.out.println("[User] User("+USERNAME+") is joining Room("+room.ROOM_ID+")");
			return true;
			
		} else {
			
			System.err.println("[User] User("+USERNAME+") is in Room("+whichRoom.ROOM_ID+") now, can't join the new room("+room.ROOM_ID+")");
			return false;
			
		}
	}
	
	/**
	 * change user state to be out of a room
	 * @param 
	 * @return If the user is not in the right Room passed by param, return false, otherwise true.
	 */
	public boolean exitRoom(Room room) {
		if (whichRoom.equals(room)) { //TODO there may have problem
			
			isPainter = false;
			whichRoom = null;
			System.out.println("[User_exitRoom] User("+USERNAME+") is exiting room("+room.ROOM_ID+")");
			return true;
			
		} else if (whichRoom == null) {
			
			System.err.println("[User_exitRoom] User("+USERNAME+") is not in any Room now, no room to exit.");
			return true;
			
		} else {
			
			System.err.println("[User_exitRoom] User("+USERNAME+") is not in the Room("+room.ROOM_ID+") but Room("+whichRoom.ROOM_ID+")");
			return false;
		}
	}
	
	/**
	 * exit Room anyway
	 */
	public void exitRoom() {
		if (whichRoom == null) {
			isPainter = false;
			System.out.println("[User_exitRoom] User("+USERNAME+") is not in any Room now");
		} else {
			isPainter = false;
			whichRoom = null;
			System.out.println("[User_exitRoom] User("+USERNAME+") is exiting Room("+whichRoom.ROOM_ID+")");
		}
	}
	
	public void getReady() {
		if (whichRoom != null) {
			this.isReady = true;
		} else {
			System.err.println("[User_getReady] Fail to getReady, User("+USERNAME+") is not in any room.");
		}
	}
	
	public void cancelReady() {
		if (whichRoom != null) {
			System.err.println("[User_cancelReady] User("+USERNAME+") is not in any room");
		}
		this.isReady = false;
	}
	
	
}
