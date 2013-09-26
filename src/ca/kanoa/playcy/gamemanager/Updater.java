package ca.kanoa.playcy.gamemanager;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;

public class Updater implements Runnable {

	@Override
	public void run() {
		for (Location loc : PlayCY.getInstance().locations) {
			if (loc.getBlock().getType() == Material.SIGN) {
				Sign sign = (Sign) loc.getBlock().getState();
				SignInfo info = PlayCY.getInstance().getSign(sign.getLine(0).replace("[", "").replace("]", ""));
				if (info == null) {
					continue;
				}
				info.updateSign(sign);
			}
		}
	}

}
