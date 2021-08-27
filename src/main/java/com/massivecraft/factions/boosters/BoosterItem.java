package com.massivecraft.factions.boosters;

import com.cryptomorin.xseries.XMaterial;
import com.google.common.collect.Lists;
import com.massivecraft.factions.FactionsPlugin;
import com.massivecraft.factions.Util;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class BoosterItem {

    private static final List<BoosterItem> boosterItems = Lists.newArrayList();
    private static boolean isSetup;
    private final ItemStack item;
    private final BoosterType boosterType;
    private final int price;
    private final int slot;
    private final int lengthInSeconds;
    private final double multiplier;

    public BoosterItem(BoosterType boosterType, ItemStack item, int price, int slot, int lengthInSeconds, double multiplier) {
        this.boosterType = boosterType;
        this.item = item;
        this.price = price;
        this.slot = slot;
        this.lengthInSeconds = lengthInSeconds;
        this.multiplier = multiplier;
        boosterItems.add(this);
    }

    public static void setupItems() {
        for (String name : FactionsPlugin.getInstance().getConfig().getConfigurationSection("fboosters.Boosters").getKeys(false)) {
            BoosterType boosterType = BoosterType.getTypeFromString(name);

            if (boosterType == null)
                continue;

            ItemStack item = XMaterial.matchXMaterial(FactionsPlugin.getInstance().getConfig().getString("fboosters.Boosters." + name + ".Type"))
                    .get().parseItem();
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                meta.setLore(Util.colorList(FactionsPlugin.getInstance().getConfig().getStringList("fboosters.Boosters." + name + ".Lore")));
                meta.setDisplayName(Util.color(FactionsPlugin.getInstance().getConfig().getString("fboosters.Boosters." + name + ".Name")));
                item.setItemMeta(meta);
            }

            int slot = FactionsPlugin.getInstance().getConfig().getInt("fboosters.Boosters." + name + ".Slot");
            int price = FactionsPlugin.getInstance().getConfig().getInt("fboosters.Boosters." + name + ".price");
            int lengthInSeconds = FactionsPlugin.getInstance().getConfig().getInt("fboosters.Boosters." + name + ".time");
            double multiplier = FactionsPlugin.getInstance().getConfig().getDouble("fboosters.Boosters." + name + ".multiplier");

            new BoosterItem(boosterType, item, price, slot, lengthInSeconds, multiplier);
        }

        isSetup = true;
    }

    public static boolean isSetup() {
        return isSetup;
    }

    public static List<BoosterItem> getBoosterItems() {
        return boosterItems;
    }

    public BoosterType getBoosterType() {
        return boosterType;
    }

    public int getLengthInSeconds() {
        return lengthInSeconds;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public int getPrice() {
        return price;
    }

    public ItemStack getItem() {
        return item;
    }

    public int getSlot() {
        return slot;
    }
}