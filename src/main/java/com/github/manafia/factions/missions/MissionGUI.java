package com.github.manafia.factions.missions;

import com.cryptomorin.xseries.XMaterial;
import com.github.manafia.factions.FPlayer;
import com.github.manafia.factions.FactionsPlugin;
import com.github.manafia.factions.util.CC;
import com.github.manafia.factions.zcore.frame.FactionGUI;
import com.github.manafia.factions.zcore.util.TL;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class MissionGUI implements FactionGUI {

    /**
     * @author Driftay
     */

    private FactionsPlugin plugin;
    private FPlayer fPlayer;
    private Inventory inventory;
    private Map<Integer, String> slots;

    public MissionGUI(FactionsPlugin plugin, FPlayer fPlayer) {
        this.slots = new HashMap<>();
        this.plugin = plugin;
        this.fPlayer = fPlayer;
        this.inventory = plugin.getServer().createInventory(this, plugin.getFileManager().getMissions().getConfig().getInt("MissionGUISize") * 9, CC.translate(plugin.getFileManager().getMissions().getConfig().getString("Missions-GUI-Title")));
    }

    @Override
    public void onClick(int slot, ClickType action) {
        String missionName = slots.get(slot);
        if (missionName == null) return;
        ConfigurationSection configurationSection = plugin.getFileManager().getMissions().getConfig().getConfigurationSection("Missions");
        if (missionName.equals(CC.translate(plugin.getFileManager().getMissions().getConfig().getString("Randomization.Start-Item.Allowed.Name")))) {
            Mission pickedMission = null;
            Set<String> keys = plugin.getFileManager().getMissions().getConfig().getConfigurationSection("Missions").getKeys(false);
            while (pickedMission == null) {
                Random r = new Random();
                int pick = r.nextInt(keys.size() - 1);
                if (!keys.toArray()[pick].toString().equals("FillItem")) {
                    missionName = keys.toArray()[pick].toString();
                    if (!fPlayer.getFaction().getMissions().containsKey(missionName)) {
                        pickedMission = new Mission(missionName, plugin.getFileManager().getMissions().getConfig().getString("Missions." + missionName + ".Mission.Type"));
                        fPlayer.getFaction().getMissions().put(missionName, pickedMission);
                        fPlayer.msg(TL.MISSION_MISSION_STARTED, fPlayer.describeTo(fPlayer.getFaction()), CC.translate(plugin.getFileManager().getMissions().getConfig().getString("Missions." + missionName + ".Name")));
                        build();
                        fPlayer.getPlayer().openInventory(inventory);
                        return;
                    }
                }
            }
        } else if (plugin.getFileManager().getMissions().getConfig().getBoolean("Randomization.Enabled")) {
            return;
        }
        if (configurationSection == null) return;

        if (plugin.getFileManager().getMissions().getConfig().getBoolean("Allow-Cancellation-Of-Missions") && fPlayer.getFaction().getMissions().containsKey(missionName)) {
            if (action == ClickType.RIGHT) {
                fPlayer.getFaction().getMissions().remove(missionName);
                fPlayer.msg(TL.MISSION_MISSION_CANCELLED);
                build();
                fPlayer.getPlayer().openInventory(inventory);
                return;
            }
        }

        int max = plugin.getFileManager().getMissions().getConfig().getInt("MaximumMissionsAllowedAtOnce");
        if (fPlayer.getFaction().getMissions().size() >= max) {
            fPlayer.msg(TL.MISSION_MISSION_MAX_ALLOWED, max);
            return;
        }
        if (missionName.equals(CC.translate(plugin.getFileManager().getMissions().getConfig().getString("Randomization.Start-Item.Disallowed.Name"))))
            return;

        if (fPlayer.getFaction().getMissions().containsKey(missionName)) {
            fPlayer.msg(TL.MISSION_MISSION_ACTIVE);
            return;
        }
        ConfigurationSection section = configurationSection.getConfigurationSection(missionName);
        if (section == null) return;

        if (plugin.getFileManager().getMissions().getConfig().getBoolean("DenyMissionsMoreThenOnce")) {
            if (fPlayer.getFaction().getCompletedMissions().contains(missionName)) {
                fPlayer.msg(TL.MISSION_ALREAD_COMPLETED);
                return;
            }
        }

        ConfigurationSection missionSection = section.getConfigurationSection("Mission");
        if (missionSection == null) return;

        Mission mission = new Mission(missionName, missionSection.getString("Type"));

        fPlayer.getFaction().getMissions().put(missionName, mission);
        fPlayer.msg(TL.MISSION_MISSION_STARTED, fPlayer.describeTo(fPlayer.getFaction()), CC.translate(section.getString("Name")));
        build();
        fPlayer.getPlayer().openInventory(inventory);
    }

    @Override
    public void build() {
        ConfigurationSection configurationSection = plugin.getFileManager().getMissions().getConfig().getConfigurationSection("Missions");
        if (configurationSection == null) {
            return;
        }
        ItemStack fillItem = XMaterial.matchXMaterial(configurationSection.getString("FillItem.Material")).get().parseItem();
        ItemMeta fillmeta = fillItem.getItemMeta();
        fillmeta.setDisplayName(CC.translate(configurationSection.getString("FillItem.Name")));
        fillmeta.setLore(CC.translate(configurationSection.getStringList("FillItem.Lore")));
        fillItem.setItemMeta(fillmeta);
        for (int fill = 0; fill < configurationSection.getInt("FillItem.Rows") * 9; ++fill) {
            //Why were we generating a new itemstack per slot?????
            inventory.setItem(fill, fillItem);
        }
        for (String key : configurationSection.getKeys(false)) {
            if (!key.equals("FillItem")) {
                ConfigurationSection section = configurationSection.getConfigurationSection(key);
                int slot = section.getInt("Slot");

                ItemStack itemStack = XMaterial.matchXMaterial(section.getString("Material")).get().parseItem();
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', section.getString("Name")));
                List<String> loreLines = new ArrayList<>();
                for (String line : section.getStringList("Lore")) {
                    loreLines.add(ChatColor.translateAlternateColorCodes('&', line));
                }
                if (fPlayer.getFaction().getMissions().containsKey(key)) {
                    Mission mission = fPlayer.getFaction().getMissions().get(key);
                    itemMeta.addEnchant(Enchantment.SILK_TOUCH, 1, true);
                    itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    loreLines.add("");
                    loreLines.add(CC.translate(plugin.getFileManager().getMissions().getConfig().getString("Mission-Progress-Format")
                            .replace("{progress}", String.valueOf(mission.getProgress()))
                            .replace("{total}", String.valueOf(section.getConfigurationSection("Mission").get("Amount")))));
                }
                itemMeta.setLore(loreLines);
                itemStack.setItemMeta(itemMeta);
                inventory.setItem(slot, itemStack);
                slots.put(slot, key);
            }
        }

        if (plugin.getFileManager().getMissions().getConfig().getBoolean("Randomization.Enabled")) {
            ItemStack start;
            ItemMeta meta;
            start = XMaterial.matchXMaterial(plugin.getFileManager().getMissions().getConfig().getString("Randomization.Start-Item.Allowed.Material")).get().parseItem();
            meta = start.getItemMeta();
            meta.setDisplayName(CC.translate(plugin.getFileManager().getMissions().getConfig().getString("Randomization.Start-Item.Allowed.Name")));
            List<String> loree = new ArrayList<>();
            for (String string : plugin.getFileManager().getMissions().getConfig().getStringList("Randomization.Start-Item.Allowed.Lore")) {
                loree.add(CC.translate(string));
            }
            meta.setLore(loree);
            start.setItemMeta(meta);
            if (fPlayer.getFaction().getCompletedMissions().size() >= configurationSection.getKeys(false).size() - 1 && plugin.getFileManager().getMissions().getConfig().getBoolean("DenyMissionsMoreThenOnce")) {
                start = XMaterial.matchXMaterial(plugin.getFileManager().getMissions().getConfig().getString("Randomization.Start-Item.Disallowed.Material")).get().parseItem();
                meta = start.getItemMeta();
                meta.setDisplayName(CC.translate(plugin.getFileManager().getMissions().getConfig().getString("Randomization.Start-Item.Disallowed.Name")));
                List<String> lore = new ArrayList<>();
                for (String string : plugin.getFileManager().getMissions().getConfig().getStringList("Randomization.Start-Item.Disallowed.Lore")) {
                    lore.add(CC.translate(string).replace("%reason%", TL.MISSION_MISSION_ALL_COMPLETED.toString()));
                }
                meta.setLore(lore);
                start.setItemMeta(meta);
            }
            if (fPlayer.getFaction().getMissions().size() >= plugin.getFileManager().getMissions().getConfig().getInt("MaximumMissionsAllowedAtOnce")) {
                start = XMaterial.matchXMaterial(plugin.getFileManager().getMissions().getConfig().getString("Randomization.Start-Item.Disallowed.Material")).get().parseItem();
                meta = start.getItemMeta();
                meta.setDisplayName(CC.translate(plugin.getFileManager().getMissions().getConfig().getString("Randomization.Start-Item.Disallowed.Name")));
                List<String> lore = new ArrayList<>();
                for (String string : plugin.getFileManager().getMissions().getConfig().getStringList("Randomization.Start-Item.Disallowed.Lore")) {
                    lore.add(CC.translate(string).replace("%reason%", FactionsPlugin.getInstance().txt.parse(TL.MISSION_MISSION_MAX_ALLOWED.toString(), plugin.getFileManager().getMissions().getConfig().getInt("MaximumMissionsAllowedAtOnce"))));
                }
                meta.setLore(lore);
                start.setItemMeta(meta);
            }
            inventory.setItem(plugin.getFileManager().getMissions().getConfig().getInt("Randomization.Start-Item.Slot"), start);
            slots.put(plugin.getFileManager().getMissions().getConfig().getInt("Randomization.Start-Item.Slot"), start.getItemMeta().getDisplayName());
        }
    }

    public Inventory getInventory() {
        return inventory;
    }
}
