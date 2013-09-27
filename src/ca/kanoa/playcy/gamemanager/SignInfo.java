package ca.kanoa.playcy.gamemanager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

public class SignInfo {

	private Command[] commands;
	private String name;
	private String world;
	private int maxPlayers;
	private boolean beenPlayed;
	
	/**
	 * Creates a new sign object
	 * @param commands The commands to be executed when the sign is right clicked
	 * @param name The name that will be inside the brackets at the top of the sign
	 * @param world The world the sign will check
	 * @param maxPlayers The maximum players that can be in the world that it checks
	 */
	public SignInfo(Command[] commands, String name, String world, int maxPlayers) {
		this.commands = commands;
		this.name = name;
		this.world = world;
		this.maxPlayers = maxPlayers;
		this.beenPlayed = false;
	}

	public Command[] getCommands() {
		return commands;
	}
	
	public String getName() {
		return name;
	}
	
	public int getMaxPlayers() {
		return maxPlayers;
	}
	
	public boolean checkSign(Sign sign) {
		return sign.getLine(0).contains("[" + name + "]");
	}
	
	public SignState getState() {
		if (Bukkit.getWorld(world) == null) {
			return SignState.OFFLINE;
		} else if (Bukkit.getWorld(world).getPlayers().size() >= maxPlayers) {
			return SignState.FULL;
		} else {
			return SignState.JOINABLE;
		}
	}
	
	public void execute(Player player) {
		for (Command command : commands) {
			command.execute(player);
		}
	}
	
	public void updateSign(Sign sign) {
		sign.setLine(0, "[" + name + "]");
		sign.setLine(1, "Players:");
		sign.setLine(2, (Bukkit.getWorld(world) != null ? 
				Bukkit.getWorld(world).getPlayers().size() : 
				0) + "/" + maxPlayers);
		if (Bukkit.getWorld(world) == null) {
			sign.setLine(3, ChatColor.RED + "Offline");
		} else if (Bukkit.getWorld(world).getPlayers().size() >= maxPlayers) {
			sign.setLine(3, ChatColor.YELLOW + "Full");
		} else {
			sign.setLine(3, ChatColor.GREEN + "Joinable");
		}
		sign.update();
	}
	
	public void updateWorld() {
		World worldObj = Bukkit.getWorld(this.world);
		if (worldObj != null && worldObj.getPlayers().size() > 0) {
			beenPlayed = true;
		} else if (worldObj != null && worldObj.getPlayers().size() == 0 && 
				beenPlayed) {
			WorldLoader.unload(world);
			WorldLoader.load(world);
			beenPlayed = false;
		} else if (worldObj == null) {
			WorldLoader.load(this.world);
			beenPlayed = false;
		}
	}
	
	public enum SignState {
		FULL,
		OFFLINE,
		INGAME,
		JOINABLE;
	}
	
}
