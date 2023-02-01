package com.github.manafia.factions.zcore.fperms.gui;

import com.cryptomorin.xseries.XMaterial;
import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.manafia.factions.FPlayer;
import com.github.manafia.factions.Faction;
import com.github.manafia.factions.FactionsPlugin;
import com.github.manafia.factions.struct.Relation;
import com.github.manafia.factions.struct.Role;
import com.github.manafia.factions.util.CC;
import com.github.manafia.factions.zcore.fperms.Permissable;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PermissableRelationFrame {

    /**
     * @author Illyria Team
     */

    private Gui gui;

    public PermissableRelationFrame(Faction f) {
        ConfigurationSection section = FactionsPlugin.getInstance().getFileManager().getFperms().getConfig().getConfigurationSection("fperm-gui.relation");
        assert section != null;
        gui = new Gui(FactionsPlugin.getInstance(),
                section.getInt("rows", 4),
                CC.translate(Objects.requireNonNull(FactionsPlugin.getInstance().getFileManager().getFperms().getConfig().getString("fperm-gui.relation.name")).replace("{faction}", f.getTag())));
    }

    public void buildGUI(FPlayer fplayer) {
        PaginatedPane pane = new PaginatedPane(0, 0, 9, gui.getRows());
        List<GuiItem> GUIItems = new ArrayList<>();
        ItemStack dumby = buildDummyItem();
        // Fill background of GUI with dumbyitem & replace GUI assets after
        for (int x = 0; x <= (gui.getRows() * 9) - 1; x++) GUIItems.add(new GuiItem(dumby, e -> e.setCancelled(true)));
        ConfigurationSection sec = FactionsPlugin.getInstance().getFileManager().getFperms().getConfig().getConfigurationSection("fperm-gui.relation");
        for (String key : sec.getConfigurationSection("slots").getKeys(false)) {
            if (key == null || sec.getInt("slots." + key) < 0) continue;
            GUIItems.set(sec.getInt("slots." + key), new GuiItem(buildAsset("fperm-gui.relation.materials." + key, key), e -> {
                e.setCancelled(true);
                // Closing and opening resets the cursor.
                // e.getWhoClicked().closeInventory();
                new PermissableActionFrame(fplayer.getFaction()).buildGUI(fplayer, getPermissable(key));
            }));
        }
        pane.populateWithGuiItems(GUIItems);
        gui.addPane(pane);
        gui.update();
        gui.show(fplayer.getPlayer());
    }

    private ItemStack buildAsset(String loc, String relation) {
        Permissable fromRelation = getPermissable(relation);
        // Since only two struct implement Permissible, using ternary operator to cast type is safe. By TwinkleStar03
        String relationName = fromRelation instanceof Relation ? ((Relation) fromRelation).nicename : ((Role) fromRelation).nicename;
        String nameCapitalized = relation.substring(0, 1).toUpperCase() + relation.substring(1);
        ItemStack item = XMaterial.matchXMaterial(FactionsPlugin.getInstance().getFileManager().getFperms().getConfig().getString(loc)).get().parseItem();
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(CC.translate(
                    FactionsPlugin
                            .getInstance()
                            .getFileManager()
                            .getFperms()
                            .getConfig()
                            .getString("fperm-gui.relation.Placeholder-Item.Name")
                            .replace("{relation}", relationName != null ? relationName : nameCapitalized)));
            item.setItemMeta(meta);
        }
        return item;
    }

    private ItemStack buildDummyItem() {
        ConfigurationSection config = FactionsPlugin.getInstance().getFileManager().getFperms().getConfig().getConfigurationSection("fperm-gui.dummy-item");
        ItemStack item = XMaterial.matchXMaterial(config.getString("Type")).get().parseItem();
        ItemMeta meta = item.getItemMeta();
        // So u can set it to air.
        if (meta != null) {
            meta.setLore(CC.translate(config.getStringList("Lore")));
            meta.setDisplayName(CC.translate(config.getString("Name")));
            item.setItemMeta(meta);
        }
        return item;
    }

    private Permissable getPermissable(String name) {
        if (Role.fromString(name.toUpperCase()) != null) {
            return Role.fromString(name.toUpperCase());
        } else if (Relation.fromString(name.toUpperCase()) != null) {
            return Relation.fromString(name.toUpperCase());
        } else {
            return null;
        }
    }
}
