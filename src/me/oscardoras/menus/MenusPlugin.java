package me.oscardoras.menus;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
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
import org.bukkit.persistence.PersistentDataType;

import me.oscardoras.menus.commands.MenuCommand;
import me.oscardoras.spigotutils.BukkitPlugin;

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
		if (item != null && item.hasItemMeta() && player.hasPermission("menus.action.interactcommand")) {
			String command = item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(this, "command"), PersistentDataType.STRING);
			if (command != null) {
			    PlayerInteractCommandEvent event = new PlayerInteractCommandEvent(player, item, command, PlayerInteractCommandEvent.Type.INVENTORY);
			    Bukkit.getPluginManager().callEvent(event);
			    if (!event.isCancelled()) {
			    	player.closeInventory();
					Bukkit.dispatchCommand(player, event.getCommand());
					e.setCancelled(true);
					return true;
			    }
			}
		}
		return false;
	}
	
}