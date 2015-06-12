package client.transmission;

import java.io.IOException;
import java.io.ObjectOutputStream;

import transfer.Packet;
import transfer.SetPacketData;

/**
 * @author Qiufan(Andy) Xu 
 * @date CreateTime: Jun 10, 2015 10:07:22 PM 
 * @version 1.0 
 * @param 
 */

public class PointSender extends Thread{

	Client client;
	ObjectOutputStream out;
	
	public PointSender(Client client, ObjectOutputStream oos) {
		this.client = client;
		this.out = oos;
	}
	
	@Override
	public void run() {
		
		while (true) {
			
			while (!client.sendingPointList.isEmpty()) {
				
				try {
					
					Packet packet = new Packet(Packet.POINTS, client.username);
					packet.setPoint(client.sendingPointList.get(0));
					
					client.sendingPointList.remove(0);
					
					out.writeObject(packet);
					out.flush();
					
				} catch (IOException e) {
					System.err.println("[Client_PointSender]");
//					e.printStackTrace();
				}
			}
			
			try {
				sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
}
