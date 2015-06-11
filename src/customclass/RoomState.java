package customclass;

/**
 * @author Qiufan(Andy) Xu 
 * @date CreateTime: Jun 10, 2015 10:33:28 PM 
 * @version 1.0 
 * @param 
 */

public class RoomState {

	public final long ROOM_ID;
	public final int USER_NUM;
	public final static int MAX_NUM = 6;
	
	public RoomState(long roomID, int userNum) {
		this.ROOM_ID = roomID;
		this.USER_NUM = userNum;
	}
}
