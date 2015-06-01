package customclass;

import java.util.ArrayList;
import java.util.List;

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
	
	protected List<UserState> userList = new ArrayList<UserState>();
	
	public Room() {
		/**
		 * 
		 * @param 
		 */
		ROOM_ID = Tool.generateID();
		System.out.println("A new room("+ROOM_ID+") has been created");
	}

	public List<UserState> getUserList() {
		return this.userList;
	}
	
}
