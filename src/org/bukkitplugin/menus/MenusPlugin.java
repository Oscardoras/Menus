package org.bukkitplugin.menus;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkitplugin.menus.commands.MenuCommand;
import org.bukkitutils.BukkitPlugin;

public final class MenusPlugin extends BukkitPlugin implements Listener {
	
	public static MenusPlugin plugin;
	
	public MenusPlugin() {
		plugin = this;
	}
	
	
	@Override
	public void onLoad() {
		MenuCommand.list();
		MenuCommand.save();
		MenuCommand.open();
		MenuCommand.delete();
	}
	
	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onMenu(InventoryClickEvent e) {
		onMenu(e, (Player) e.getWhoClicked(), e.getCurrentItem());
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onMenu(PlayerInteractEvent e) {
	    onMenu(e, e.getPlayer(), e.getItem());
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onMenu(PlayerInteractEntityEvent e) {
		Player player = e.getPlayer();
		onMenu(e, player, player.getInventory().getItemInMainHand());
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onMenu(PlayerInteractAtEntityEvent e) {
		Player player = e.getPlayer();
	    onMenu(e, player, player.getInventory().getItemInMainHand());
	}
	
	public boolean onMenu(Cancellable e, Player player, ItemStack item) {
		if (item != null && player.hasPermission("menus.action.interactcommand")) {
			boolean found = false;
			try {
				for (String line : item.getItemMeta().getLore()) {
					if (line.startsWith("command:")) {
					    PlayerInteractCommandEvent event = new PlayerInteractCommandEvent(player, item, line.substring(8, line.length()), PlayerInteractCommandEvent.Type.INVENTORY);
					    Bukkit.getPluginManager().callEvent(event);
					    if (!event.isCancelled()) {
					    	if (!found) player.closeInventory();
	    					Bukkit.dispatchCommand(player, event.getCommand());
	    					found = true;
					    }
					}
				}
			} catch(NullPointerException ex) {}
			if (found) {
			    e.setCancelled(true);
			    return true;
			}
		}
		return false;
	}
	
}