package ca.kanoa.playcy.gamemanager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;

public class SignInfo {

	private Command command;
	private String name;
	private String world;
	private int maxPlayers;
	
	public SignInfo(Command command, String name, String world, int maxPlayers) {
		this.command = command;
		this.name = name;
		this.world = world;
		this.maxPlayers = maxPlayers;
	}

	public Command getCommand() {
		return command;
	}
	
	public boolean checkSign(Sign sign) {
		return sign.getLine(0).contains("[" + name + "]");
	}
	
	public void updateSign(Sign sign) {
		sign.setLine(0, "[" + name + "]");
		sign.setLine(1, "Players:");
		sign.setLine(2, (Bukkit.getWorld(world) != null ? Bukkit.getWorld(world).getPlayers().size() : 0) + "/" + maxPlayers);
		if (Bukkit.getWorld(world) == null) {
			sign.setLine(3, ChatColor.RED + "Offline");
		} else if (Bukkit.getWorld(world).getPlayers().size() >= maxPlayers) {
			sign.setLine(3, ChatColor.YELLOW + "Full");
		} else {
			sign.setLine(3, ChatColor.GREEN + "Joinable");
		}
	}
	
}
