package ca.kanoa.playcy.gamemanager;

import java.util.HashSet;
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
	
}
