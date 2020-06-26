package me.oscardoras.menus.commands;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.permissions.Permission;

import me.oscardoras.menus.Menu;
import me.oscardoras.menus.Message;
import me.oscardoras.spigotutils.command.v1_16_1_V1.Argument;
import me.oscardoras.spigotutils.command.v1_16_1_V1.CommandRegister;
import me.oscardoras.spigotutils.command.v1_16_1_V1.LiteralArgument;
import me.oscardoras.spigotutils.command.v1_16_1_V1.CommandRegister.CommandExecutorType;
import me.oscardoras.spigotutils.command.v1_16_1_V1.arguments.EntitySelectorArgument;
import me.oscardoras.spigotutils.command.v1_16_1_V1.arguments.StringArgument;
import me.oscardoras.spigotutils.command.v1_16_1_V1.arguments.EntitySelectorArgument.EntitySelector;

public final class MenuCommand {
	private MenuCommand() {}
	
	
	public static void list() {
		LinkedHashMap<String, Argument<?>> arguments = new LinkedHashMap<>();
		arguments.put("list", new LiteralArgument("list"));
		CommandRegister.register("menu", arguments, new Permission("menus.command.menu"), CommandExecutorType.ALL, (command) -> {
			List<String> menus = Menu.getMenus();
			command.sendListMessage(menus, new Object[] {new Message("command.menu.list.list")}, new Object[] {new Message("command.menu.list.empty")});
			return menus.size();
		});
	}
	
	public static void save() {
		LinkedHashMap<String, Argument<?>> arguments = new LinkedHashMap<>();
		arguments.put("save", new LiteralArgument("save"));
		arguments.put("menu", new StringArgument());
		CommandRegister.register("menu", arguments, new Permission("menus.command.menu"), CommandExecutorType.PLAYER, (command) -> {
			Menu.setMenu((String) command.getArg(0), ((Player) command.getExecutor()).getInventory());
			command.broadcastMessage(new Message("command.menu.save", (String) command.getArg(0)));
			return 1;
		});
	}
	
	@SuppressWarnings("unchecked")
	public static void open() {
		LinkedHashMap<String, Argument<?>> arguments = new LinkedHashMap<>();
		arguments.put("open", new LiteralArgument("open"));
		arguments.put("targets", new EntitySelectorArgument(EntitySelector.MANY_PLAYERS));
		arguments.put("menu", new MenuArgument());
		CommandRegister.register("menu", arguments, new Permission("menus.command.menu"), CommandExecutorType.ALL, (command) -> {
			Entry<String, Inventory> menu = (Entry<String, Inventory>) command.getArg(1);
			for (Entity entity : (Collection<Entity>) command.getArg(0)) {
				Player player = (Player) entity;
				player.openInventory(menu.getValue());
				command.broadcastMessage(new Message("command.menu.open", menu.getKey()));
			}
			return 1;
		});
	}
	
	@SuppressWarnings("unchecked")
	public static void delete() {
		LinkedHashMap<String, Argument<?>> arguments = new LinkedHashMap<>();
		arguments.put("delete", new LiteralArgument("delete"));
		arguments.put("menu", new MenuArgument());
		CommandRegister.register("menu", arguments, new Permission("menus.command.menu"), CommandExecutorType.ALL, (command) -> {
			String menu = ((Entry<String, Inventory>) command.getArg(0)).getKey();
			Menu.setMenu(menu, null);
			command.broadcastMessage(new Message("command.menu.delete", menu));
			return 1;
		});
	}
	
}