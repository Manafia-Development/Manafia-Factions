package com.massivecraft.factions.cloaks;

import com.cryptomorin.xseries.XMaterial;
import com.google.common.collect.Lists;
import com.massivecraft.factions.FactionsPlugin;
import com.massivecraft.factions.Util;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class CloakItem {

    private static final List<CloakItem> cloakItems = Lists.newArrayList();
    private static boolean isSetup;
    private final ItemStack item;
    private final CloakType cloakType;
    private final int price;
    private final int slot;
    private final int lengthInSeconds;

    public CloakItem(CloakType cloakType, ItemStack item, int price, int slot, int lengthInSeconds) {
        this.cloakType = cloakType;
        this.item = item;
        this.price = price;
        this.slot = slot;
        this.lengthInSeconds = lengthInSeconds;
        cloakItems.add(this);
    }

    public static void setupItems() {
        for (String name : FactionsPlugin.getInstance().getFileManager().getCloaks().getConfig().getBoolean("Enabled").getKeys(false)) {
            CloakType cloakType = CloakType.getTypeFromString(name);

            if (cloakType == null)
                continue;

            ItemStack item = XMaterial.matchXMaterial(FactionsPlugin.getInstance().getFileManager().getCloaks().getConfig().getConfig().getString("Cloaks." + name + ".Type"))
                    .get().parseItem();
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                meta.setLore(Util.colorList(FactionsPlugin.getInstance().getFileManager().getCloaks().getConfig().getStringList("Cloaks." + name + ".Lore")));
                meta.setDisplayName(Util.color(FactionsPlugin.getInstance().getFileManager().getCloaks().getConfig().getString("Cloaks." + name + ".Name")));
                item.setItemMeta(meta);
            }

            int slot = FactionsPlugin.getInstance().getFileManager().getCloaks().getConfig().getInt("Cloaks." + name + ".Slot");
            int price = FactionsPlugin.getInstance().getFileManager().getCloaks().getConfig().getConfig().getInt("Cloaks." + name + ".price");
            int lengthInSeconds = FactionsPlugin.getInstance().getFileManager().getCloaks().getConfig().getInt("Cloaks." + name + ".time");

            new CloakItem(cloakType, item, price, slot, lengthInSeconds);
        }

        isSetup = true;
    }

    public static boolean isSetup() {
        return isSetup;
    }

    public static List<CloakItem> getCloakItems() {
        return cloakItems;
    }

    public CloakType getCloakType() {
        return cloakType;
    }

    public int getLengthInSeconds() {
        return lengthInSeconds;
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
