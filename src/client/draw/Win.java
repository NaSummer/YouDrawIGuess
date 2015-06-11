package client.draw;


import client.guess.GuessPanel;

public class Win extends Thread {
	
	DrawPanel drawPanel;
	GuessPanel guessPanel;
	
	public Win(DrawPanel drawPanel) {
		this.drawPanel = drawPanel;
	}
	
	public Win(GuessPanel guessPanel) {
		this.guessPanel = guessPanel;
	}
	
	@Override
	public void run() {
		
		if (drawPanel!=null) {
			
			while (!drawPanel.client.isTimeOut()) {
				if(drawPanel.client.whoWin()!=null){
					drawPanel.winner.setText(drawPanel.client.whoWin());
				}
				
				try {
					sleep(500);
				} catch (InterruptedException e) {
					System.err.println("[Client_Win] Fail to sleep");
//				e.printStackTrace();
				}
			}
			
			if(drawPanel.client.isTimeOut()){
				drawPanel.winner.setText("No one wins");
			}
			
		} else if (guessPanel!=null) {
			
			while (!guessPanel.client.isTimeOut()) {
				if(guessPanel.client.whoWin()!=null){
					guessPanel.showWinner.setText(guessPanel.client.whoWin());
				}
				
				try {
					sleep(500);
				} catch (InterruptedException e) {
					System.err.println("[Client_Win] Fail to sleep");
//				e.printStackTrace();
				}
			}
			
			if(drawPanel.client.isTimeOut()){
				guessPanel.showWinner.setText("No one wins");
			}
			
		}
		
	}
}
