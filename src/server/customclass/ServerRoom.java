package server.customclass;

import customclass.Room;
import customclass.UserState;

/**
 * @author Qiufan(Andy) Xu 
 * @date CreateTime: May 31, 2015 12:00:34 AM 
 * @version 1.0 
 * @param 
 */

public class ServerRoom extends Room{

	public ServerRoom() {
		/**
		 * 
		 * @param 
		 */
		// TODO Auto-generated constructor stub
		super();
	}
	
	/**
	 * Add user to Room with not ready state. 
	 * If the user is the first one joined room, he will be the painter, 
	 * otherwise the guesser.
	 * @param String username
	 * @return If Room is full, return false, otherwise return true.
	 */
	public boolean addUser(String username) {
		if (userList.size()<MAX_NUM) {
			
			int indexOfUser = findUser(username);
			if (indexOfUser == -1) {
				
				/* Judge whether the user is the first one joining the room */
				boolean isPainter;
				if (userList.isEmpty()) {
					isPainter = true;
				} else {
					isPainter = false;
				}
				
				/* create */
				UserState userState = new UserState(username, isPainter);
				
				userList.add(userState);
				
				return true;
				
			} else {
				
				System.err.println("User() has been in the Room() already");
				
			}
			
		} else {
			
			System.err.println("[addUser] Room(" + ROOM_ID + ") is full.");
			return false;
			
		}
		
	}
	
	/**
	 * remove user from Room by username
	 * @param String username
	 * @return If user has not been in Room, return false, otherwise return true
	 */
	public boolean removeUser(String username) {
		int indexOfUser = findUser(username);
		if (indexOfUser != -1) {
			userList.remove(indexOfUser);
			return true;
		} else {
			System.err.println("[removeUser] User("+username+") isn't in the Room("+ROOM_ID+")");
			return false;
		}
	}
	
	/**
	 * get user ready by username
	 * @param String username
	 * @return If user isn't in Room, return false, otherwise return true
	 */
	public boolean getUserReady(String username) {
		int indexOfUser = findUser(username);
		if (indexOfUser != -1) {
			userList.get(indexOfUser).getReady();
			return true;
		} else {
			System.err.println("[getUserReady] User("+username+") isn't in the Room("+ROOM_ID+")");
			return false;
		}
	}
	
	/**
	 * cancel user ready by username
	 * @param String username
	 * @return If user isn't in Room, return false, otherwise return true
	 */
	public boolean cancelUserReady(String username) {
		int indexOfUser = findUser(username);
		if (indexOfUser != -1) {
			userList.get(indexOfUser).cancelReady();
			return true;
		} else {
			System.err.println("[cancelUserReady] User("+username+") isn't in the Room("+ROOM_ID+")");
			return false;
		}
	}
	
	
	/* ======== Private Method ======== */
	/**
	 * find user in userList by username
	 * @param String username
	 * @return If not find user, return -1, otherwise return index.
	 */
	private int findUser(String username) {
		for (int i = 0; i < userList.size(); i++) {
			if (userList.get(i).USERNAME.equals(username)) {
				return i;
			}
		}
		System.err.println("[findUser] Can't find User("+username+") in Room("+ROOM_ID+")");
		return -1;
	}
	
}
