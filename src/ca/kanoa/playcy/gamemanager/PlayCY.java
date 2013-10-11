package ca.kanoa.playcy.gamemanager;

import java.io.File;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayCY extends JavaPlugin {

	protected FileConfiguration config;
	public FileConfiguration locationConfig;
	public Set<Location> locations;
	public Set<SignInfo> signs;
	
	protected CommandExecutor commandExecutor;
	protected SignUpdater signUpdater;
	protected WorldUpdater worldUpdater;
	protected Listener listener;
	
	private static PlayCY instance = null;

	@Override
	public void onEnable() {
		/* Setup variables */
		if (instance == null) {
			instance = this;
		}

		/* Load data from files */
		if (!new File(getDataFolder(), "config.yml").exists()) {
			saveResource("config.yml", false);                                 //Save the config (including my comments)
		}
		if (!new File(getDataFolder(), "locations.yml").exists()) {
			saveResource("locations.yml", false);                              //Save the locations file (including my comments)
		}
		config = getConfig();
		locationConfig = YamlConfiguration.loadConfiguration(new File(getDataFolder(), 
				"locations.yml"));
		locations = Utils.parseLocationConfig(
				locationConfig.
				getConfigurationSection("locations"));
		signs = Utils.parseSignConfig(config.getConfigurationSection("signs"));

		/* Register listeners, executor, etc */
		commandExecutor = new CommandExecutor();
		getCommand("playcy").setExecutor(commandExecutor);
		signUpdater = new SignUpdater();
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, signUpdater, 0, 10);
		worldUpdater = new WorldUpdater();
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, worldUpdater, 0, 20);
		listener = new Listener();
		Bukkit.getPluginManager().registerEvents(listener, this);
	}
	
	public void loadFromOld(PlayCY playcy) {
		config = playcy.config;
		locationConfig = playcy.locationConfig;
		locations = playcy.locations;
		signs = playcy.signs;
		getCommand("playcy").setExecutor(playcy.commandExecutor);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, playcy.signUpdater, 0, 10);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, playcy.worldUpdater, 0, 20);
		Bukkit.getPluginManager().registerEvents(playcy.listener, this);
	}

	public SignInfo getSignInfo(String name) {
		for (SignInfo si : signs) {
			if (si.getName().equalsIgnoreCase(name))
				return si;
		}
		return null;
	}

	public SignInfo getSignInfo(Sign sign) {
		for (SignInfo info : signs) {
			if (info.checkSign(sign)) {
				return info;
			}
		}
		return null;
	}

	public static PlayCY getInstance() {
		return instance;
	}

}
