package org.bukkitplugin.menus;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkitutils.io.ConfigurationFile;

public final class Menu {
	private Menu() {}
	
	
	private final static ConfigurationFile config = new ConfigurationFile(Bukkit.getWorlds().get(0).getWorldFolder() + "/data/menus.yml");
	
	public static List<String> getMenus() {
		List<String> list = new ArrayList<String>();
		list.addAll(config.getConfigurationSection("").getKeys(false));
		return list;
	}
	
	public static Inventory getMenu(String name) {
		if (config.contains(name)) {
			Inventory menu = Bukkit.createInventory(null, InventoryType.PLAYER, name);
			for (String item : config.getConfigurationSection(name).getKeys(false)) {
				try {
					menu.setItem(Integer.parseInt(item), config.getItemStack(name + "." + item));
				} catch (IllegalArgumentException ex) {}
			}
			return menu;
		}
		return null;
	}
	
	public static void setMenu(String name, Inventory menu) {
		if (menu != null) {
			int i = 0;
			for (ItemStack item : menu.getContents()) {
				config.set(name + "." + String.valueOf(i), item);
				i++;
			}
		} else config.set(name, null);
		config.save();
	}
	
}
