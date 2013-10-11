package ca.kanoa.playcy.gamemanager;

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

	public static Set<SignInfo> parseSignConfig(
			ConfigurationSection config) {
		Set<SignInfo> signs = new HashSet<SignInfo>();
		for (String s : config.getKeys(false)) {
			signs.add(new SignInfo(
					getCommands(config.getStringList(s + ".commands")),
					getCommands(config.getStringList(s + ".game-start-commands")),
					config.getString(s + ".name"),
					colors(config.getString(s + ".countdown-message")),
					config.getString(s + ".world"),
					config.getInt(s + ".max-players"),
					config.getInt(s + ".minimum-players"),
					config.getInt(s + ".lobby-time")));
		}
		return signs;
	}
	
	public static String colors(String str) {
		if (str != null) {
		return str.replace('&', '\u00A7');
		} else {
			return "";
		}
	}

	private static Command[] getCommands(List<String> list) {
		Command[] commands = new Command[list.size()];
		for (int i = 0; i < list.size(); i++) {
			commands[i] = new Command(colors(list.get(i)));
		}
		return commands;
	}
	
}
