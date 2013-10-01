package ca.kanoa.playcy.gamemanager;

import org.bukkit.Bukkit;

public class Countdown implements Runnable {

	private long length;
	private long elapsed;
	private boolean cancel;
	
	/**
	 * Creates and starts a new Countdown
	 * @param length The length of this countdown, in seconds.
	 */
	public Countdown(long length) {
		this.length = length;
		this.elapsed = 0;
		this.cancel = false;
		schedule();
	}
	
	@Override
	public void run() {
		elapsed++;
		if (elapsed < length && cancel == false) {
			second(getTimeLeft(), Time.getTime(getTimeLeft()));
			schedule();
		} else {
			done();
		}
	}
	
	/**
	 * Gets called every second
	 * @param time The time left in the units specified in type
	 * @param type The unit that time is in
	 */
	public void second(long time, Time type) {}
	
	/**
	 * Gets called when the countdown ends
	 */
	public void done() {}
	
	public void cancel() {
		this.cancel = true;
	}
	
	private void schedule() {
		Bukkit.getScheduler().scheduleSyncDelayedTask(PlayCY.getInstance(),
				this, 20);
	}
	
	public boolean isDone() {
		return elapsed >= length;
	}
	
	public long getTimeLeft() {
		return length - elapsed;
	}

	public long getLength() {
		return length;
	}

	public long getElapsed() {
		return elapsed;
	}
	
	public enum Time {
		DAY,
		HOUR,
		MINUTE,
		SECOND;
		
		public static Time getTime(long seconds) {
			if (seconds < 60) {
				return Time.SECOND;
			} else if (seconds < 3600) {
				return Time.MINUTE;
			} else if (seconds < 86400) {
				return Time.HOUR;
			} else {
				return Time.DAY;
			}
		}
		
		public static long convert(long seconds, Time toType) {
			switch (toType) {
			case DAY: return seconds / 86400;
			case HOUR: return seconds / 3600;
			case MINUTE: return seconds / 60;
			default: return seconds;
			}
		}
		
		@Override
		public String toString() {
			return super.toString().toLowerCase();
		}
		
	}

}
