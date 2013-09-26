package ca.kanoa.playcy.gamemanager;

import java.io.File;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayCY extends JavaPlugin {

	protected FileConfiguration config;
	public Set<Location> locations;
	public HashMap<String, SignInfo> signs;
	private static PlayCY instance;
	
	@Override
	public void onEnable() {
		/* Setup variables */
		instance = this;
		
		/* Load data from files */
		saveResource("config.yml", false); //Save the config (including my comments)
		saveResource("locations.yml", false); //Save the locations file (including my comments)
		config = getConfig();
		locations = Utils.parseLocationConfig(YamlConfiguration.
				loadConfiguration(new File(getDataFolder(), "locations.yml")).
				getConfigurationSection("locations"));
		signs = Utils.parseSignConfig(config.getConfigurationSection("signs"));
		
		/* Register listeners, executor, etc */
		getCommand("playcy").setExecutor(new CommandExecutor());
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Updater(), 0, 10);
		Bukkit.getPluginManager().registerEvents(new Listener(), this);
	}
	
	@Override
	public void onDisable() {
		instance = null;
	}
	
	public SignInfo getSign(String name) {
		return signs.get(name);
	}

	public static PlayCY getInstance() {
		return instance;
	}
	
}
