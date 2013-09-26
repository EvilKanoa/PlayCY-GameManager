package ca.kanoa.playcy.gamemanager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Command {

	private String command;
	
	public Command(String command) {
		this.command = command;
	}
	
	public void execute(Player player) {
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%PLAYER%", player.getName()));
	}
	
}
