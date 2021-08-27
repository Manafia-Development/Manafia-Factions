package com.massivecraft.factions.shield;

import com.cryptomorin.xseries.XMaterial;
import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.FactionsPlugin;
import com.massivecraft.factions.Util;
import com.massivecraft.factions.util.ItemBuilder;
import com.massivecraft.factions.zcore.util.TL;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ShieldFrame {
    private final Gui gui;
    private final Faction faction;


    public ShieldFrame(Faction faction) {
        this.gui = new Gui(FactionsPlugin.getInstance(), FactionsPlugin.getInstance().getConfig().getInt("Shields.Rows"), Util.color(FactionsPlugin.getInstance().getConfig().getString("Shields.Title")));
        this.faction = faction;
        buildGui();


    }

    public void buildGui() {
        gui.setOnGlobalClick(event -> {
            event.setCancelled(true);
        });


        PaginatedPane pane = new PaginatedPane(1, 1, 7, 3);
        List<GuiItem> items = new ArrayList<>();
        ShieldTimes.getAllTimes().forEach((first, second) -> {
            ConfigurationSection config = FactionsPlugin.getInstance().getConfig().getConfigurationSection("Shields.GUI");
            ItemStack item = XMaterial.matchXMaterial(config.getString("DisplayItem")).get().parseItem();
            ItemMeta meta = item.getItemMeta();

            if (meta != null) {
                meta.setLore(Util.colorList(config.getStringList("Lore")));
                meta.setDisplayName(Util.color(config.getString("Color")) + first.toString() + " - " + second.toString());
                item.setItemMeta(meta);
            }

            if (this.faction.isShieldSelected()) {
                if (this.faction.getShieldStart().equals(first) && this.faction.getShieldEnd().equals(second)) {
                    meta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
                    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                }
            }
            item.setItemMeta(meta);
            items.add(new GuiItem(item, event -> {
                if (faction.isShieldSelected()) {
                    event.getWhoClicked().sendMessage(TL.SHIELD_INFO.format(first, second));
                    event.getWhoClicked().closeInventory();
                    return;
                }
                faction.setShieldStart(first);
                faction.setShieldEnd(second);

                Bukkit.broadcastMessage(TL.SHIELD_SET_GLOBAL.format(faction.getTag(), first, second));
                event.getWhoClicked().sendMessage(TL.SHIELD_SET.format(event.getWhoClicked().getName(), first, second));


                event.getWhoClicked().closeInventory();
            }));
        });
        pane.populateWithGuiItems(items);

        StaticPane forward = new StaticPane(5, 5, 1, 1);
        StaticPane back = new StaticPane(3, 5, 1, 1);
        back.addItem(new GuiItem(new ItemBuilder(Material.ARROW).name("&7Previous Page").build(), event -> {
            event.setResult(Event.Result.DENY);
            pane.setPage(pane.getPage() - 1);

            if (pane.getPage() == 0) {
                back.setVisible(false);
            }
            forward.setVisible(true);
            gui.update();
        }), 0, 0);

        forward.addItem(new GuiItem(new ItemBuilder(Material.ARROW).name("&7Next Page").build(), event -> {
            event.setResult(Event.Result.DENY);
            pane.setPage(pane.getPage() + 1);

            if (pane.getPage() == pane.getPages() - 1) {
                forward.setVisible(false);
            }
            back.setVisible(true);
            gui.update();
        }), 0, 0);

        gui.addPane(pane);
        gui.addPane(forward);
        gui.addPane(back);
        back.setVisible(false);
        gui.update();
    }


    public void open(Player player) {
        gui.show(player);
    }
}
