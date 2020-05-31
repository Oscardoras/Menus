package org.bukkitplugin.menus.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.inventory.Inventory;
import org.bukkitplugin.menus.Menu;
import org.bukkitplugin.menus.Message;
import org.bukkitutils.command.v1_15_V1.CustomArgument;

public class MenuArgument extends CustomArgument<Entry<String, Inventory>> {

	public MenuArgument() {
		withSuggestionsProvider((command) -> {
			return Menu.getMenus();
		});
	}

	@Override
	public Entry<String, Inventory> parse(String arg, SuggestedCommand cmd) throws Exception {
		Inventory menu = Menu.getMenu(arg);
		if (menu == null) throw getCustomException(new Message("menu.does_not_exist").getMessage(cmd.getLanguage(), arg));
		else {
			Map<String, Inventory> map = new HashMap<String, Inventory>();
			map.put(arg, menu);
			return map.entrySet().iterator().next();
		}
	}
}