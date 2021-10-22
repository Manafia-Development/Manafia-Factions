package com.github.manafia.factions.util;


import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Map;
import java.util.Set;

public final class InventoryUtils {

    public static final int DEFAULT_INVENTORY_WIDTH = 9;

    public static final int MINIMUM_INVENTORY_HEIGHT = 1;
    public static final int MINIMUM_INVENTORY_SIZE = MINIMUM_INVENTORY_HEIGHT * DEFAULT_INVENTORY_WIDTH;

    public static final int MAXIMUM_INVENTORY_HEIGHT = 6;
    public static final int MAXIMUM_INVENTORY_SIZE = MAXIMUM_INVENTORY_HEIGHT * DEFAULT_INVENTORY_WIDTH;

    public static final int MAXIMUM_SINGLE_CHEST_SIZE = DEFAULT_INVENTORY_WIDTH * 3;
    public static final int MAXIMUM_DOUBLE_CHEST_SIZE = MAXIMUM_SINGLE_CHEST_SIZE * 2;

    private InventoryUtils () {
    }

    /**
     * Deep clones an array of {@link ItemStack}s calling {@link ItemStack#clone()}
     * for each non-null entry.
     *
     * @param origin the array to clone
     * @return deep cloned array.
     */
    public static ItemStack[] deepClone (ItemStack[] origin) {
        Preconditions.checkNotNull(origin, "Origin cannot be null");
        ItemStack[] cloned = new ItemStack[origin.length];
        for (int i = 0; i < origin.length; i++) {
            ItemStack next = origin[i];
            cloned[i] = next == null ? null : next.clone();
        }

        return cloned;
    }

    /**
     * Converts an initial value to a safe inventory size.
     *
     * @param initialSize the initial size of inventory
     * @return the safe Bukkit {@link Inventory} size
     */
    public static int getSafestInventorySize (int initialSize) {
        return (initialSize + (DEFAULT_INVENTORY_WIDTH - 1)) / DEFAULT_INVENTORY_WIDTH * DEFAULT_INVENTORY_WIDTH;
    }

    /**
     * Safely removes an ItemStack with a specific quantity
     * from an inventory ignoring any ItemMeta.
     *
     * @param inventory the inventory to remove for
     * @param type      the material to remove
     * @param data      the data value to remove
     * @param quantity  the amount to be removed
     */
    public static void removeItem (Inventory inventory, Material type, short data, int quantity) {
        ItemStack[] contents = inventory.getContents();
        boolean compareDamage = type.getMaxDurability() == 0;

        for (int i = quantity; i > 0; i--) {
            for (ItemStack content : contents) {
                if (content == null || content.getType() != type) continue;
                if (compareDamage && content.getData().getData() != data) continue;

                if (content.getAmount() <= 1) {
                    inventory.removeItem(content);
                } else {
                    content.setAmount(content.getAmount() - 1);
                }
                break;
            }
        }
    }

    /**
     * Counts how much of an item an inventory contains.
     *
     * @param inventory the inventory to count for
     * @param type      the material to count for
     * @param data      the data value to count for
     * @return amount of the item inventory contains
     */
    public static int countAmount (Inventory inventory, Material type, short data) {
        ItemStack[] contents = inventory.getContents();
        boolean compareDamage = type.getMaxDurability() == 0;

        int counter = 0;
        for (ItemStack item : contents) {
            if (item == null || item.getType() != type) continue;
            if (compareDamage && item.getData().getData() != data) continue;

            counter += item.getAmount();
        }

        return counter;
    }

    //TODO: Javadocs
    public static boolean isEmpty (Inventory inventory) {
        return isEmpty(inventory, true);
    }

    //TODO: Javadocs
    public static boolean isEmpty (Inventory inventory, boolean checkArmour) {
        boolean result = true;
        ItemStack[] contents = inventory.getContents();
        for (ItemStack content : contents) {
            if (content != null && content.getType() != Material.AIR) {
                result = false;
                break;
            }
        }

        if (!result) return false;
        if (checkArmour && inventory instanceof PlayerInventory) {
            contents = ((PlayerInventory) inventory).getArmorContents();
            for (ItemStack content : contents) {
                if (content != null && content.getType() != Material.AIR) {
                    result = false;
                    break;
                }
            }
        }

        return result;
    }

    /**
     * Checks if the top inventory was clicked in the {@link InventoryDragEvent}.
     *
     * @param event the {@link org.bukkit.event.Event} to check for
     * @return true if the top {@link Inventory} was clicked
     */
    public static boolean clickedTopInventory (InventoryDragEvent event) {
        InventoryView view = event.getView();
        Inventory topInventory = view.getTopInventory();
        if (topInventory == null) {
            return false;
        }

        boolean result = false;
        Set<Map.Entry<Integer, ItemStack>> entrySet = event.getNewItems().entrySet();
        int size = topInventory.getSize();
        for (Map.Entry<Integer, ItemStack> entry : entrySet) {
            if (entry.getKey() < size) {
                result = true;
                break;
            }
        }

        return result;
    }
}