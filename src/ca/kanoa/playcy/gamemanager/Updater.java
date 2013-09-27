package ca.kanoa.playcy.gamemanager;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;

public class Updater implements Runnable {

	@Override
	public void run() {
		for (Location loc : PlayCY.getInstance().locations) {
			try {
				if (loc.getBlock().getType() == Material.WALL_SIGN || 
						loc.getBlock().getType() == Material.SIGN_POST ||
						loc.getBlock().getType() == Material.SIGN) {
					Sign sign = (Sign) loc.getBlock().getState();
					SignInfo info = PlayCY.getInstance().getSignInfo(sign);
					if (info == null) {
						continue;
					}
					info.updateSign(sign);
				}
			} catch (NullPointerException e) {
				continue;
			}
		}
	}

}
