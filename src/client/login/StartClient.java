package client.login;

import client.transmission.Client;

/**
 * @author Qiufan(Andy) Xu 
 * @date CreateTime: May 30, 2015 11:34:55 PM 
 * @version 1.0 
 */

public class StartClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Client client = new Client();
		client.login(serverAddress, username, password);
	}

}
