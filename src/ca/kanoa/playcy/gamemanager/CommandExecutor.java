package ca.kanoa.playcy.gamemanager;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class CommandExecutor implements org.bukkit.command.CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String label, String[] args) {
		if (args.length > 0 && args[0].equalsIgnoreCase("addloc")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "Player only command!");
				return false;
			}
			if (args.length != 4) {
				sender.sendMessage(ChatColor.RED + 
						"You did not enter enough arguments!");
				return false;
			}
			int x, y, z;
			try {
				x = Integer.parseInt(args[1]);
				y = Integer.parseInt(args[2]);
				z = Integer.parseInt(args[3]);
			} catch (NumberFormatException e) {
				sender.sendMessage(ChatColor.RED + 
						"One or more of your coordinates is not a number!");
				return false;
			}
			PlayCY.getInstance().locationConfig.set("locations." + 
					x + y + z + ".world", 
					((Player)sender).getWorld().getName());
			PlayCY.getInstance().locationConfig.set("locations." + 
					x + y + z + ".x", x);
			PlayCY.getInstance().locationConfig.set("locations." + 
					x + y + z + ".y", y);
			PlayCY.getInstance().locationConfig.set("locations." + 
					x + y + z + ".z", z);
			PlayCY.getInstance().locations.add(new Location(((Player)sender).
					getWorld(), x, y, z));
			try {
				PlayCY.getInstance().locationConfig.save(new File(
						PlayCY.getInstance().getDataFolder(), 
						"locations.yml"));
				sender.sendMessage(ChatColor.GREEN + 
						"Location: " + x + ", " + y + ", " + z + 
						" has been added!");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		} else if (args.length > 0 && args[0].equalsIgnoreCase("tp")) {
			if (args.length != 6) {
				sender.sendMessage(ChatColor.RED + 
						"You did not enter enough arguments!");
				return false;
			}

			int x, y, z;
			try {
				x = Integer.parseInt(args[3]);
				y = Integer.parseInt(args[4]);
				z = Integer.parseInt(args[5]);
			} catch (NumberFormatException e) {
				sender.sendMessage(ChatColor.RED + 
						"One or more of your coordinates is not a number!");
				return false;
			}
			World world = Bukkit.getWorld(args[2]);
			if (world == null) {
				sender.sendMessage(ChatColor.RED + "That world is not loaded!");
				return false;
			}
			Player player = Bukkit.getPlayerExact(args[1]);
			if (player == null || !player.isOnline()) {
				sender.sendMessage(ChatColor.RED + "That player is not online!");
				return false;
			}
			
			player.teleport(new Location(world, x, y, z));
			sender.sendMessage(ChatColor.GREEN + "Teleporting " + player.getName() + " to " + world.getName());
			
			return true;
		} else if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
			sender.sendMessage(ChatColor.YELLOW + "Reloading...");
			PlayCY.getInstance().locationConfig = YamlConfiguration.
					loadConfiguration(new File(PlayCY.getInstance().getDataFolder(),
							"locations.yml"));
			PlayCY.getInstance().reloadConfig();
			PlayCY.getInstance().config = PlayCY.getInstance().getConfig();
			PlayCY.getInstance().locations = Utils.parseLocationConfig(
					PlayCY.getInstance().locationConfig.getConfigurationSection(
					"locations"));
			PlayCY.getInstance().signs = Utils.parseSignConfig(
					PlayCY.getInstance().config.
					getConfigurationSection("signs"));
			sender.sendMessage(ChatColor.YELLOW + "Reload complete!");
			return true;
		} else if (args.length > 0 && args[0].equalsIgnoreCase("version")) {
			sender.sendMessage(ChatColor.GREEN + "[" + 
					PlayCY.getInstance().getDescription().getName() + 
					"] version: " + PlayCY.getInstance().getDescription().getVersion());
			return true;
		} else {
			return false;
		}
	}

}
