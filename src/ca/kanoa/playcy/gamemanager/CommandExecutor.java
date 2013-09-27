package ca.kanoa.playcy.gamemanager;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandExecutor implements org.bukkit.command.CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String label, String[] args) {
		if (args.length > 0 && args[0].equalsIgnoreCase("addloc")) {
			
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
