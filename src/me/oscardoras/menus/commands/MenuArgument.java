package me.oscardoras.menus.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.inventory.Inventory;

import me.oscardoras.menus.Menu;
import me.oscardoras.menus.Message;
import me.oscardoras.spigotutils.command.v1_16_1_V1.CustomArgument;

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