package ca.kanoa.playcy.gamemanager;

public class WorldUpdater implements Runnable {

	@Override
	public void run() {
		for (SignInfo info : PlayCY.getInstance().signs) {
			info.updateWorld();
		}
	}

}
