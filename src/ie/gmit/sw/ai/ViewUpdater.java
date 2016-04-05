package ie.gmit.sw.ai;

public class ViewUpdater implements Runnable {
	private GameView view;
	private long fps;
	private Player player;
	
	public ViewUpdater(GameView view, long fps, Player player) {
		this.view = view;
		this.fps = 1000/fps;
		this.player = player;
	}

	public void run() {
		do {
			updateView();
			try {
				Thread.sleep(fps);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}while(player.getStamina() > 0);
	}
	
	public void updateView() {
		view.setCurrentRow(player.getNode().getRow());
		view.setCurrentCol(player.getNode().getCol());
		view.setStats(player.getStamina(), player.getInventory().getWeaponState());
	}
}
