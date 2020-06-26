package me.oscardoras.menus;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractCommandEvent extends PlayerEvent implements Cancellable {
    
    private final static HandlerList HANDLERS = new HandlerList();
    
    protected final ItemStack itemStack;
    protected String cmd;
    protected final Type type;
    protected boolean cancelled = false;
    
    public PlayerInteractCommandEvent(Player who, ItemStack itemStack, String cmd, Type type) {
        super(who);
        this.itemStack = itemStack;
        this.cmd = cmd;
        this.type = type;
    }
    
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
	
	public ItemStack getItemStack() {
	    return itemStack;
	}
	
	public String getCommand() {
	    return cmd;
	}
	
	public void setCommand(String cmd) {
	    this.cmd = cmd;
	}
	
	public Type getType() {
	    return type;
	}
	
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
    
    public enum Type {
        INTERACT, INVENTORY;
    }

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
	
}