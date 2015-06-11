package client.guess;

import client.draw.DrawPanel;

public class StateListener extends Thread {
	
	DrawPanel drawPanel;
	GuessPanel guessPanel;
	
	public StateListener(DrawPanel drawPanel) {
		this.drawPanel = drawPanel;
	}
	
	public StateListener(GuessPanel guessPanel) {
		this.guessPanel = guessPanel;
	}
	
	@Override
	public void run() {
		
		if (drawPanel!=null) {
			
			while (drawPanel.client.getRoomID()!=0) {
				
				for(int i=0;i<drawPanel.client.getUserState().size();i++){
					String ready;
					if (drawPanel.client.getUserState().get(i).isReady()) {
						ready = ": Ready";
					} else {
						ready = ": Waiting";
					}
					
					drawPanel.member.appendText(drawPanel.client.getUserState().get(i).USERNAME + ready + "\n\n");
					
					/* 自动滚动到最后一行 */
					drawPanel.member.positionCaret(drawPanel.member.getText().length());
				}
				
				if(drawPanel.client.isGameStart()){
					drawPanel.startButton.setVisible(false);
				}
				
				try {
					sleep(500);
				} catch (InterruptedException e) {
					System.err.println("[Client_StateListener] Fail to sleep.");
//					e.printStackTrace();
				}
				
			}
			
			
		} else if (guessPanel!=null) {
			
			while (guessPanel.client.getRoomID()!=0) {
				
				for(int i=0;i<guessPanel.client.getUserState().size();i++){
					String ready;
					if (guessPanel.client.getUserState().get(i).isReady()) {
						ready = ": Ready";
					} else {
						ready = ": Waiting";
					}
					
					guessPanel.member.appendText(guessPanel.client.getUserState().get(i).USERNAME + ready + "\n\n");
					
					/* 自动滚动到最后一行 */
					guessPanel.member.positionCaret(guessPanel.member.getText().length());
				}
				
				if(guessPanel.client.isGameStart()){
					guessPanel.readyButton.setVisible(false);
					guessPanel.cancelReadyButton.setVisible(false);
				}
				
				try {
					sleep(500);
				} catch (InterruptedException e) {
					System.err.println("[Client_StateListener] Fail to sleep.");
//				e.printStackTrace();
				}
				
			}
			
		}
		
		
		
		
		
		
	}
	
}
