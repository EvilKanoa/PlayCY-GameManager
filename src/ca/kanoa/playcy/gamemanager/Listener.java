package ca.kanoa.playcy.gamemanager;


import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import ca.kanoa.playcy.gamemanager.SignInfo.SignState;

public class Listener implements org.bukkit.event.Listener {
	
	@EventHandler(ignoreCancelled=true)
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}
		Block block = event.getClickedBlock();
		if (block.getType() == Material.SIGN || 
				block.getType() == Material.SIGN_POST ||
				block.getType() == Material.WALL_SIGN) {
			SignInfo info = PlayCY.getInstance().getSignInfo((Sign) 
					block.getState());
			if (info == null) {
				return;
			}
			if (info.getState() == SignState.FULL) {
				event.getPlayer().sendMessage(PlayCY.getInstance().
						config.getString("server-full"));
			} else if (info.getState() == SignState.OFFLINE) {
				event.getPlayer().sendMessage(PlayCY.getInstance().
						config.getString("server-offline"));
			} else {
				info.execute(event.getPlayer());
			}
		}
	}
	
}
