package ca.kanoa.playcy.gamemanager;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayCY extends JavaPlugin {

	protected FileConfiguration config;
	
	@Override
	public void onEnable() {
		saveResource("config.yml", false); //Save the config (including my comments)
		config = getConfig();
	}
	
}
