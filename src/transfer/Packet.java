package transfer;

import java.io.Serializable;

/**
 * @author Qiufan(Andy) Xu 
 * @date CreateTime: Jun 2, 2015 12:54:34 PM 
 * @version 1.0 
 * @param 
 */

public class Packet implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5700862865311793811L;
	
	public final static int PULSE = 0;
	public final static int PULSE_BACK = 1;
	
	public final static int LOGIN = 2;
	public final static int REGISTER = 6;
	public final static int AUTH_SUCCESSFUL = 3;
	public final static int AUTH_FAIL = 7;
	
	public final static int LOGOUT = 4;
	public final static int LOGOUT_BACK = 5;
	
	public final static int ASK_ROOM_LIST = 11;
	public final static int ROOM_LIST = 10;
	
	public final static int CREARE_ROOM = 12;
	public final static int JOIN_ROOM = 13;
	public final static int EXIT_ROOM = 21;
	public final static int EXIT_ROOM_SUCCESS = 22;
	
	public final static int GET_READY = 18;
	public final static int CANCEL_READY = 19;
	
	public final static int START_GAME = 20;
	
	public final static int ROOM_STATE = 14;

	public final static int ROOM_MESSAGE = 8;
	public final static int ROOM_MESSAGE_BACK = 9;

	public final static int POINTS = 17;
	
	public final static int TIME_OUT = 15;
	public final static int WINNER = 16;
	
	public final int TYPE;
	public final String USERNAME;
	
	/* Variable */
	protected long[] longArrat;
	protected String[] stringArray;
	protected int[] intArray;
	protected double[] doubleArray;
	protected long long1;
	protected String str1; // login, register
	
	public Packet(int type, String username) {
		/**
		 * 
		 * @param 
		 */
		this.TYPE = type;
		this.USERNAME = username;
	}
	
}
