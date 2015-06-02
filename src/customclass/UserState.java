package customclass;

/**
 * @author Qiufan(Andy) Xu 
 * @date CreateTime: May 31, 2015 3:19:27 PM 
 * @version 1.0 
 * @param 
 */

public class UserState extends User{
	
	
	public final boolean IS_PAINTER;
	protected boolean isReady;
	
	public UserState(String username, boolean isPainter) {
		/**
		 * 
		 * @param 
		 */
		super(username);
		this.IS_PAINTER = isPainter;
		isReady = false;
	}

	public boolean isReady() {
		return isReady;
	}

	public void getReady() {
		this.isReady = true;
	}
	
	public void cancelReady() {
		this.isReady = false;
	}
	
	
}
