package ca.kanoa.playcy.gamemanager;

import java.io.File;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayCY extends JavaPlugin {

	protected FileConfiguration config;
	protected Set<Location> locations;
	
	@Override
	public void onEnable() {
		/* Load data from files */
		saveResource("config.yml", false); //Save the config (including my comments)
		saveResource("locations.yml", false); //Save the locations file (including my comments)
		config = getConfig();
		locations = Utils.parseLocationConfig(YamlConfiguration.loadConfiguration(new File(getDataFolder(), "locations.yml")).getConfigurationSection("locations"));
		
		/* Register listeners, executor, etc */
		getCommand("playcy").setExecutor(new CommandExecutor());
	}
	
}
