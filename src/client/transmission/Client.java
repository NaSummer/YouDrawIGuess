package client.transmission;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import tool.Tool;
import customclass.Message;
import customclass.Room;

/**
 * 
 * @author Qiufan(Andy) Xu 
 * @date CreateTime: May 30, 2015 11:34:55 PM 
 * @version 1.0 
 */

public class Client {
	
	public final static int PORT = 20488;
	public final long DEVICE_ID;
	
	private String username;

	public Client() {
		this.DEVICE_ID = Tool.generateID();
		// TODO Auto-generated constructor stub
	}
	
	
	
	/* ========login======== */
	/**
	 * login server
	 * @param 
	 * @return [boolean] whether login successful
	 */
	public boolean login(String serverAddress, String username, String password) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	/* ========register======== */
	/**
	 * register a new user in server
	 * @param 
	 * @return [boolean] whether register successful
	 */
	public boolean register(String serverAddress, String username, String password) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * check whether username has existed in server
	 * @param 
	 * @return [boolean] whether username has existed in server
	 */
	public boolean isUserExisted(String serverAddress, String username) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	/* ========select room======== */
	/**
	 * <p>get Room List</p>
	 * <p>If you want to create and join a new room, you need to do 
	 * (1)createRoom, 
	 * (2)getRoomList again, 
	 * (3)find a empty room, 
	 * (4)joinRoom, 
	 * or use createAndJoinRoom.</p>
	 * @return [ArrayList<Room>]
	 */
	public ArrayList<Room> getRoomList() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * create a Room
	 * @return [boolean] whether create a room successfully
	 */
	public boolean createRoom() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * joinRoom
	 * @param 
	 * @return [boolean] whether join a room successfully
	 */
	public boolean joinRoom(Room room) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * create a room and join it
	 * @return [boolean] whether successful
	 */
	public boolean createAndJoinRoom() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * exitRoom
	 * @return [boolean] is successful
	 */
	public boolean exitRoom() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	/* ========Pane======== */
	
	/* ====Message==== */
	/**
	 * sendMessage
	 * @param 
	 * @return is successful
	 */
	public boolean sendMessage(String str) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * <p>getMessage</p>
	 * <p>use Message.USERNAME Message.CONTENT</p>
	 * 
	 * @return [Message] If there is no new message, return null.
	 */
	public Message getMessage() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	/* ====State====*/
	/**
	 * getReady
	 * @return [boolean] is successful
	 */
	public boolean getReady() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * cancelReady
	 * @return [boolean] is successful
	 */
	public boolean cancelReady() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * who Win
	 * @return [String] If nobody win, return null, otherwise return winner username
	 */
	public String whoWin() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * isTimeOut
	 * @return [boolean] If time is out, return true.
	 */
	public boolean isTimeOut() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	/* ====Point====*/
	/**
	 * send Point List
	 * @param 
	 * @return [boolean] is successful
	 */
	public boolean sendPointList(ArrayList<Point> points) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * getPointList
	 * @return [ArrayList<Point>] If have no new points, return null
	 */
	public ArrayList<Point> getPointList() {
		// TODO Auto-generated method stub
		
	}
	
	
	/* ========private method======== */
	
	
}
