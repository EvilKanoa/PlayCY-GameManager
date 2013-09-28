package ca.kanoa.playcy.gamemanager;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

public class SignInfo {

	private Command[] commands;
	private Command[] startGame;
	private String name;
	private String world;
	private String countdownMessage;
	private final int maxPlayers;
	private final int minPlayers;
	private final int lobbyTime;
	private boolean ingame;
	private Countdown countdown;
	
	/**
	 * Creates a new sign object
	 * @param commands The commands to be executed when the sign is right clicked
	 * @param name The name that will be inside the brackets at the top of the sign
	 * @param world The world the sign will check
	 * @param maxPlayers The maximum players that can be in the world that it checks
	 */
	public SignInfo(Command[] commands, Command[] startGame, String name,
			String countdownMessage, String world, int maxPlayers, 
			int minPlayers, int lobbyTime) {
		this.commands = commands;
		this.startGame = startGame;
		this.name = name;
		this.world = world;
		this.countdownMessage = countdownMessage;
		this.maxPlayers = maxPlayers;
		this.minPlayers = minPlayers;
		this.lobbyTime = lobbyTime;
		this.ingame = false;
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
		} else if (getOnlinePlayers(Bukkit.getWorld(world).getPlayers()) >= maxPlayers) {
			return SignState.FULL;
		} else if (ingame) {
			return SignState.INGAME;
		} else {
			return SignState.JOINABLE;
		}
	}
	
	public void execute(Player player) {
		for (Command command : commands) {
			command.execute(player);
		}
	}
	
	private void broadcastToWorld(String message) {
		World world = Bukkit.getWorld(this.world);
		if (world == null) {
			return;
		}
		for (Player p : world.getPlayers()) {
			p.sendMessage(message);
		}
	}
	
	private void broadcastToWorld(Sound sound) {
		World world = Bukkit.getWorld(this.world);
		if (world == null) {
			return;
		}
		for (Player p : world.getPlayers()) {
			p.playSound(p.getLocation().add(0, 1, 0), sound, 1, 1);
		}
	}
	
	public void startGame() {
		World world = Bukkit.getWorld(this.world);
		if (world == null) {
			return;
		}
		for (Player p : world.getPlayers()) {
			for (Command c : startGame) {
				c.execute(p);
			}
		}
	}
	
	public void updateSign(Sign sign) {
		sign.setLine(0, "[" + name + "]");
		sign.setLine(1, "Players:");
		sign.setLine(2, (Bukkit.getWorld(world) != null ? 
				getOnlinePlayers(Bukkit.getWorld(world).getPlayers()) : 
				0) + "/" + maxPlayers);
		if (Bukkit.getWorld(world) == null) {
			sign.setLine(3, ChatColor.RED + "Offline");
		} else if (getOnlinePlayers(Bukkit.getWorld(world).getPlayers()) >= maxPlayers) {
			sign.setLine(3, ChatColor.YELLOW + "Full");
		} else if (ingame == false){
			sign.setLine(3, ChatColor.GREEN + "Joinable");
		} else {
			sign.setLine(3, ChatColor.LIGHT_PURPLE + "In-game");
		}
		sign.update();
	}
	
	public void updateGame() {
		if (ingame == true) {
			return;
		}
		World world = Bukkit.getWorld(this.world);
		if (world == null) {
			return;
		} else if (getOnlinePlayers(world.getPlayers()) >= minPlayers && 
				countdown == null) {
			countdown = new Countdown(lobbyTime) {
				
				@Override
				public void second(long time, Time type) {
					if (type == Time.SECOND && time <= 5) {
						broadcastToWorld(countdownMessage.replace("%TIME%", Long.toString(time)));
						broadcastToWorld(Sound.CLICK);
					}
				}
				
				@Override
				public void done() {
					startGame();
					ingame = true;
				}
			};
		} else if (getOnlinePlayers(world.getPlayers()) < minPlayers) {
			if (countdown != null) {
				countdown.cancel();
				countdown = null;
			}
		}
	}
	
	public void updateWorld() {
		World worldObj = Bukkit.getWorld(this.world);
		if (worldObj != null && getOnlinePlayers(worldObj.getPlayers()) == 0 &&
				ingame) {
			WorldLoader.unload(world);
			WorldLoader.load(world);
			ingame = false;
		} else if (worldObj == null) {
			WorldLoader.load(this.world);
			ingame = false;
		}
	}
	
	public static int getOnlinePlayers(List<Player> players) {
		int onlinePlayers = 0;
		for (Player p : players) {
			if (p.isOnline()) {
				onlinePlayers++;
			}
		}
		return onlinePlayers;
	}
	
	public enum SignState {
		FULL,
		OFFLINE,
		INGAME,
		JOINABLE;
	}
	
}
