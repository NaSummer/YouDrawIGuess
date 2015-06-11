package client.draw;

import client.guess.GuessPanel;


public class MessageListener extends Thread{
	
	DrawPanel drawPanel;
	GuessPanel guessPanel;
	
	public MessageListener(DrawPanel drawPanel) {
		this.drawPanel = drawPanel;
	}
	
	public MessageListener(GuessPanel guessPanel) {
		this.guessPanel = guessPanel;
	}
	
	
	@Override
	public void run() {
		
		if (drawPanel != null) {
			
			while (drawPanel.client.getRoomID()!=0) {
				
				while (drawPanel.client.getMessage() != null) {
					drawPanel.showMessage.appendText("[" + drawPanel.client.getMessage().USERMANE + "] said:\n  -> " + drawPanel.client.getMessage().CONTENT + "\n\n");
					/* 自动滚动到最后一行 */
					drawPanel.showMessage.positionCaret(drawPanel.showMessage.getText().length());
				}
				
				try {
					MessageListener.sleep(500);
				} catch (InterruptedException e) {
					System.err.println("[MessagesListener] Fail to sleep MessagesListener");
//					e.printStackTrace();
				}
			}
			
		} else if (guessPanel != null) {
			
			while (guessPanel.client.getRoomID()!=0) {
				
				while (guessPanel.client.getMessage() != null) {
					guessPanel.showMessage.appendText("[" + guessPanel.client.getMessage().USERMANE + "] said:\n  -> " + guessPanel.client.getMessage().CONTENT + "\n\n");
					/* 自动滚动到最后一行 */
					guessPanel.showMessage.positionCaret(guessPanel.showMessage.getText().length());
				}
				
				try {
					MessageListener.sleep(500);
				} catch (InterruptedException e) {
					System.err.println("[MessagesListener] Fail to sleep MessagesListener");
//					e.printStackTrace();
				}
			}
			
		}
	}
	
}
