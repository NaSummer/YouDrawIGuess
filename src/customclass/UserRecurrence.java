package customclass;

/**
 * @author Qiufan(Andy) Xu 
 * @date CreateTime: Jun 10, 2015 11:38:09 PM 
 * @version 1.0 
 * @param 
 */

public class UserRecurrence extends User{

	public UserRecurrence(String username, boolean isPainter, boolean isReady) {
		super(username);
		super.isPainter = isPainter;
		super.isReady = isReady;
	}
	
}
