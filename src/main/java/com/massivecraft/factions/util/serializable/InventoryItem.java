package com.massivecraft.factions.util.serializable;

import com.massivecraft.factions.util.ItemBuilder;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Driftay
 */
public class InventoryItem {
    private final ItemStack item;
    private final Map<ClickType, Runnable> clickMap;
    private Runnable runnable;

    public InventoryItem(ItemStack original) {
        this.clickMap = new HashMap<>();
        this.item = original;
    }

    public InventoryItem(ItemBuilder original) {
        this(original.build());
    }

    public InventoryItem click(ClickType type, Runnable runnable) {
        this.clickMap.put(type, runnable);
        return this;
    }

    public InventoryItem click(Runnable runnable) {
        this.runnable = runnable;
        return this;
    }

    public ItemStack getItem() {
        return this.item;
    }

    public Map<ClickType, Runnable> getClickMap() {
        return this.clickMap;
    }

    public Runnable getRunnable() {
        return this.runnable;
    }
}
