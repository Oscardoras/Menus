package org.bukkitplugin.menus;

import org.bukkitutils.io.TranslatableMessage;

public class Message extends TranslatableMessage {
	
	public Message(String path, String... args) {
		super(MenusPlugin.plugin, path, args);
	}
	
}