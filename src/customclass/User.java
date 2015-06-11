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
	
}
