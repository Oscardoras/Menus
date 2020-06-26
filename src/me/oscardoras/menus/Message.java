package me.oscardoras.menus;

import me.oscardoras.spigotutils.io.TranslatableMessage;

public class Message extends TranslatableMessage {
	
	public Message(String path, String... args) {
		super(MenusPlugin.plugin, path, args);
	}
	
}