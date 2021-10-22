package com.github.manafia.factions.cmd.check;

import com.cryptomorin.xseries.XMaterial;
import com.google.common.collect.Lists;
import com.github.manafia.factions.Conf;
import com.github.manafia.factions.Faction;
import com.github.manafia.factions.FactionsPlugin;
import com.github.manafia.factions.zcore.frame.FactionGUI;
import com.github.manafia.factions.zcore.util.TL;
import org.bukkit.DyeColor;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

public class CheckHistoryFrame implements FactionGUI {

    /**
     * @author Driftay
     */

    private final FactionsPlugin plugin;
    private final Faction faction;
    private final Inventory inventory;
    private final SimpleDateFormat simpleDateFormat;

    public CheckHistoryFrame (FactionsPlugin plugin, Faction faction) {
        this.simpleDateFormat = new SimpleDateFormat(Conf.dateFormat);
        this.plugin = plugin;
        this.faction = faction;
        this.inventory = plugin.getServer().createInventory(this, 54, TL.CHECK_HISTORY_GUI_TITLE.toString());
    }

    public void onClick (int slot, ClickType action) {
    }

    public void build () {
        int currentSlot = 0;
        for (Map.Entry<Long, String> entry : Lists.reverse(new ArrayList<>(faction.getChecks().entrySet()))) {
            if (currentSlot >= 54)
                continue;
            String str = entry.getValue().substring(0, 1);
            ItemStack itemStack = new ItemStack(XMaterial.MAGENTA_STAINED_GLASS_PANE.parseItem());
            itemStack.setDurability((short) 2);
            MaterialData data = itemStack.getData();
            data.setData(DyeColor.MAGENTA.getWoolData());
            itemStack.setData(data);
            ItemMeta itemMeta = itemStack.getItemMeta();
            switch (str) {
                case "U":
                    itemMeta.setDisplayName(TL.CHECK_WALLS_CHECKED_GUI_ICON.toString());
                    break;
                case "Y":
                    itemMeta.setDisplayName(TL.CHECK_BUFFERS_CHECKED_GUI_ICON.toString());
                    break;
                case "J":
                    itemMeta.setDisplayName(TL.CHECK_WALLS_UNCHECKED_GUI_ICON.toString());
                    break;
                case "H":
                    itemMeta.setDisplayName(TL.CHECK_BUFFERS_UNCHECKED_GUI_ICON.toString());
                    break;
            }
            itemMeta.setLore(Arrays.asList(TL.CHECK_TIME_LORE_LINE.format(simpleDateFormat.format(new Date(entry.getKey()))), TL.CHECK_PLAYER_LORE_LINE.format(entry.getValue().substring(1))));
            itemStack.setItemMeta(itemMeta);
            inventory.setItem(currentSlot, itemStack);
            ++currentSlot;
        }
    }

    public Inventory getInventory () {
        return inventory;
    }
}
