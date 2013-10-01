package ca.kanoa.playcy.gamemanager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import ca.kanoa.playcy.gamemanager.Countdown.Time;

public class Command {

	private String command;
	
	/**
	 * Creates a new Command object
	 * @param command The command that will be ran when this is executed
	 */
	public Command(String command) {
		if (command.startsWith("/")) {
			this.command = command.substring(1);
		} else {
			this.command = command;
		}
	}
	
	public void execute(Player player) {
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), 
				command.replace("%PLAYER%", player.getName()));
	}
	
	public void execute(Player player, long time, Time type) {
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), 
				command.replace("%PLAYER%", player.getName()).
				replace("%TIME%", Long.toString(time)).
				replace("%TIMETYPE%", type.toString()));
	}
	
}
