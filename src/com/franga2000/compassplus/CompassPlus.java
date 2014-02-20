package com.franga2000.compassplus;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class CompassPlus extends JavaPlugin implements Listener{
	static FileConfiguration config;
	@Override
    public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
        if(!getDataFolder().exists())
            getDataFolder().mkdir();
        if(getConfig() == null)
            saveDefaultConfig();
        config = getConfig();
    }
    @Override
    public void onDisable(){
        saveConfig();
    }
    /**
     * Converts a rotation to a cardinal direction name.
     * 
     * @param rot
     * @return
     */
    private static String getDirection(Player p) {
    	double rot = (p.getLocation().getYaw() - 90) % 360;
        if (rot < 0) {
            rot += 360.0;
        }
    	if (0 <= rot && rot < 22.5) {
            return config.getString("North");
        } else if (22.5 <= rot && rot < 67.5) {
            return config.getString("NorthEast");
        } else if (67.5 <= rot && rot < 112.5) {
            return config.getString("East");
        } else if (112.5 <= rot && rot < 157.5) {
            return config.getString("SouthEast");
        } else if (157.5 <= rot && rot < 202.5) {
            return config.getString("South");
        } else if (202.5 <= rot && rot < 247.5) {
            return config.getString("SouthWest");
        } else if (247.5 <= rot && rot < 292.5) {
            return config.getString("West");
        } else if (292.5 <= rot && rot < 337.5) {
            return config.getString("NorthWest");
        } else if (337.5 <= rot && rot < 360.0) {
            return config.getString("North");
        } else {
            return null;
        }
    }
	@EventHandler
	public void onMove(PlayerMoveEvent e){
		Player p = e.getPlayer();
		 ItemStack itemInHand = p.getItemInHand();
		if(itemInHand.getType() == Material.COMPASS){
			String name = this.getConfig().getString("name");
		    String finalName = name.replace("<direction>", getDirection(p));
		    finalName.replace("<player>", p.getName());
			ItemMeta meta = itemInHand.getItemMeta();
			meta.setDisplayName(finalName);
			itemInHand.setItemMeta(meta);
		}
	}
}
