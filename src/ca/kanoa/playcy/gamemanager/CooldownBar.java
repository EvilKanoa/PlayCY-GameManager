package ca.kanoa.playcy.gamemanager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CooldownBar extends BukkitRunnable {

	private final Player player;
	private int percent;
	private final Countdown countdown;

	public CooldownBar(Player player, Countdown countdown) {
		this.player = player;
		this.countdown = countdown;
		this.percent = 100;
		render();
		Bukkit.getScheduler().scheduleSyncDelayedTask(PlayCY.getInstance(), this, 2l);
	}

	public void render() {
		player.setExp((float) ((double) this.percent / (double) 100));
	}

	private int getPercent() {
		double rawPercent = (double) countdown.getTimeLeft() / 
				(double) countdown.getLength();
		return (int) (rawPercent * 100);
	}

	@Override
	public void run() {
		this.percent = getPercent();
		if (this.percent >= 1) {
			render();
			Bukkit.getScheduler().scheduleSyncDelayedTask(PlayCY.getInstance(), this, 2l);
		} 
		else
			player.setExp(0.00f);
	}

}
