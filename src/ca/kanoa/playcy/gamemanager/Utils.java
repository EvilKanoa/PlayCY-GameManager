package ca.kanoa.playcy.gamemanager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

/**
 * Manages static utility methods
 * @author Kanoa
 *
 */
public class Utils {

	public static Set<Location> parseLocationConfig(ConfigurationSection 
			config) {
		Set<Location> locations = new HashSet<Location>();
		for (String s : config.getKeys(false)) {
			locations.add(new Location(Bukkit.getWorld(
					config.getString(s + ".world")), config.getInt(s + ".x"),
					config.getInt(s + ".y"), config.getInt(s + ".z")));
		}
		return locations;
	}

	public static HashMap<String, SignInfo> parseSignConfig(
			ConfigurationSection config) {
		HashMap<String, SignInfo> signs = new HashMap<String, SignInfo>();
		for (String s : config.getKeys(false)) {
			signs.put(config.getString(s + ".name"), new SignInfo(
					getCommands(config.getStringList(s + ".commands")),
					config.getString(s + ".name"),
					config.getString(s + ".world"),
					config.getInt(s + ".maxPlayers")));
		}
		return null;
	}

	private static Command[] getCommands(List<String> list) {
		Command[] commands = new Command[list.size()];
		for (int i = 0; i < list.size(); i++) {
			commands[i] = new Command(list.get(i));
		}
		return commands;
	}
	
}
