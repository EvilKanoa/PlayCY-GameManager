package ca.kanoa.playcy.gamemanager;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

public class WorldLoader {

	public static final boolean DEBUG = true;
	
	/**
	 * Unloads a loaded world from bukkit without saving it
	 * @param worldName The name of the world to unload
	 * @return The world was unloaded successfully
	 */
	public static boolean unload(String worldName) {
		return unload(worldName, false);
	}
	
	/**
	 * Unloads a loaded world from bukkit
	 * @param worldName The name of the world to unload
	 * @param save If we should save it as we unload it
	 * @return The world was unloaded successfully
	 */
	public static boolean unload(String worldName, boolean save) {
		
		World world = Bukkit.getWorld(worldName);
		world.setAutoSave(save);
		if (DEBUG) {
			Bukkit.getLogger().info("Unloading world (Autosave: " + 
					save + "): " + world.getName());
		}
		boolean unloaded = Bukkit.unloadWorld(world, save);
		if (DEBUG) {
			Bukkit.getLogger().info("Unloaded fully (" + 
					world.getName() + "): " + unloaded);
		}
		return unloaded;
	}
	
	/**
	 * Loads a world from the root of bukkits directory with autosaving off
	 * @param worldName The name of the world and its folder
	 * @return The world was loaded successfully
	 */
	public static boolean load(String worldName) {
		return load(worldName, false);
	}
	
	/**
	 * Loads a world from the root of bukkits directory
	 * @param worldName The name of the world and its folder
	 * @param save Sets auto saving on or off, you can still use world.save();
	 * @return The world was loaded successfully
	 */
	public static boolean load(String worldName, boolean save) {
		if (DEBUG) {
			Bukkit.getLogger().info("Loading world (Autosave: " + 
					save + "): " + worldName);
		}
		World w = Bukkit.createWorld(new WorldCreator(worldName));
		w.setAutoSave(save);
		boolean loaded = (w != null);
		if (DEBUG) {
			Bukkit.getLogger().info("Loaded fully (" + worldName + "): " + 
					loaded);
		}
		return loaded;
	}
	
}
