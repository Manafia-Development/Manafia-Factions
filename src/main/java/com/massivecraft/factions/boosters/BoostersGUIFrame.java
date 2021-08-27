package com.massivecraft.factions.boosters;

import com.cryptomorin.xseries.XMaterial;
import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.FactionsPlugin;
import com.massivecraft.factions.Util;
import com.massivecraft.factions.util.TimeUtil;
import com.massivecraft.factions.zcore.util.TL;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BoostersGUIFrame {

    private final Gui gui;
    private final Faction faction;
    private final FPlayer fPlayer;

    public BoostersGUIFrame(FPlayer fPlayer) {
        this.gui = new Gui(FactionsPlugin.getInstance(), FactionsPlugin.getInstance().getConfig().getInt("fboosters.Rows"), Util.color(FactionsPlugin
                .getInstance().getConfig().getString("fboosters.Title")));
        this.faction = fPlayer.getFaction();
        this.fPlayer = fPlayer;
        buildGui();
    }

    public void buildGui() {
        List<GuiItem> GUIItems = new ArrayList<>();

        ItemStack dummy = buildDummyItem();

        for (int x = 0; x <= (gui.getRows() * 9) - 1; x++) {
            GUIItems.add(new GuiItem(dummy, e -> e.setCancelled(true)));
        }

        if (!BoosterItem.isSetup())
            BoosterItem.setupItems();

        List<BoosterItem> boosterItems = BoosterItem.getBoosterItems();

        for (BoosterItem boosterItem : boosterItems) {
            int slot = boosterItem.getSlot();
            BoosterType boosterType = boosterItem.getBoosterType();
            ItemStack item;

            if (faction.hasBooster(boosterItem.getBoosterType())) {
                item = getAlreadyActiveItem(boosterType);
            } else {
                item = boosterItem.getItem();
            }

            GUIItems.set(slot, new GuiItem(item, e -> {
                e.setCancelled(true);

                if (faction.hasBooster(boosterType)) {
                    e.getWhoClicked().closeInventory();
                    fPlayer.msg(TL.COMMAND_BOOSTERS_ALREADY_ACTIVE.format(boosterType.name().toLowerCase()));
                    return;
                }

                if (!fPlayer.takeMoney(boosterItem.getPrice())) {
                    e.getWhoClicked().closeInventory();
                    return;
                }

                long millis = boosterItem.getLengthInSeconds() * 1000;
                long endTimeStamp = millis + System.currentTimeMillis();
                TimeUtil timeUtil = new TimeUtil(millis, TimeUnit.MILLISECONDS);

                Booster booster = new Booster(boosterType, endTimeStamp, boosterItem.getMultiplier());
                faction.addBooster(booster);
                fPlayer.msg(TL.COMMAND_BOOSTERS_PURCHASED.format(boosterType.name().toLowerCase()));
                Bukkit.broadcastMessage(TL.COMMAND_BOOSTERS_STARTED_ANNOUNCE.format(faction.getTag(), boosterType.name().toLowerCase(), timeUtil
                        .toString()));
                e.getWhoClicked().closeInventory();
            }));
        }

        PaginatedPane pane = new PaginatedPane(0, 0, 9, gui.getRows());
        pane.populateWithGuiItems(GUIItems);
        gui.addPane(pane);
        gui.update();
    }

    private ItemStack buildDummyItem() {
        ConfigurationSection config = FactionsPlugin.getInstance().getConfig().getConfigurationSection("fboosters.DummyItem");
        ItemStack item = XMaterial.matchXMaterial(config.getString("Type")).get().parseItem();
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setLore(Util.colorList(config.getStringList("Lore")));
            meta.setDisplayName(Util.color(config.getString("Name")));
            item.setItemMeta(meta);
        }
        return item;
    }

    public ItemStack getAlreadyActiveItem(BoosterType boosterType) {
        ConfigurationSection config = FactionsPlugin.getInstance().getConfig().getConfigurationSection("fboosters.ActiveBooster");
        ItemStack item = XMaterial.matchXMaterial(config.getString("Type")).get().parseItem();
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setLore(Util.colorList(config.getStringList("Lore")));
            meta.setDisplayName(Util.color(config.getString("Name")).replaceAll("%type%", boosterType.name().toLowerCase()));
            item.setItemMeta(meta);
        }
        return item;
    }

    public void open(Player player) {
        gui.show(player);
    }
}
