package customclass;

import java.util.ArrayList;
import java.util.List;

import server.customclass.HandleUser;
import tool.Tool;

/**
 * @author Qiufan(Andy) Xu 
 * @date CreateTime: May 31, 2015 12:00:02 AM 
 * @version 1.0 
 * @param 
 */

public class Room {

	public final long ROOM_ID;
	public final static int MAX_NUM = 6;
	
	protected long serverRoomID;
	protected List<HandleUser> userList = new ArrayList<HandleUser>();
	
	public Room() {
		
		ROOM_ID = Tool.generateID();
		System.out.println("A new room("+ROOM_ID+") has been created");
		
	}

	public List<User> getUserList() {
		List<User> userList = new ArrayList<User>();
		for (int i = 0; i < this.userList.size(); i++) {
			userList.add( (User) this.userList.get(i) );
		}
		return userList;
	}
	
	/**
	 * 
	 * @param 
	 * @return 
	 */
	public long getServerRoomID() {
		return serverRoomID;
	}
	
}
