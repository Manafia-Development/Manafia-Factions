package com.massivecraft.factions;

import com.massivecraft.factions.util.ClipPlaceholderAPIManager;
import com.massivecraft.factions.util.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.logging.Level;

public class PlaceholderUtil {

    public static void setupPlaceholderAPI() {
        Plugin clip = Bukkit.getServer().getPluginManager().getPlugin("PlaceholderAPI");
        if (clip != null && clip.isEnabled()) {
            FactionsPlugin.getInstance().clipPlaceholderAPIManager = new ClipPlaceholderAPIManager();
            if (FactionsPlugin.getInstance().clipPlaceholderAPIManager.register()) {
                FactionsPlugin.getInstance().PlaceholderApi = true;
                FactionsPlugin.getInstance().log(Level.INFO, "Successfully registered placeholders with PlaceholderAPI.");
            } else {
                FactionsPlugin.getInstance().PlaceholderApi = false;
            }
        } else {
            FactionsPlugin.getInstance().PlaceholderApi = false;
        }

        Plugin mvdw = Bukkit.getServer().getPluginManager().getPlugin("MVdWPlaceholderAPI");
        if (mvdw != null && mvdw.isEnabled()) {
            FactionsPlugin.getInstance().mvdwPlaceholderAPIManager = true;
            FactionsPlugin.getInstance().log(Level.INFO, "Found MVdWPlaceholderAPI. Adding hooks.");
        }
    }

    public static List<String> replacePlaceholders(List<String> lore, Placeholder... placeholders) {
        for (Placeholder placeholder : placeholders) {
            for (int x = 0; x <= lore.size() - 1; x++)
                lore.set(x, lore.get(x).replace(placeholder.getTag(), placeholder.getReplace()));
        }
        return lore;
    }

    public static boolean isClipPlaceholderAPIHooked() {
        return FactionsPlugin.getInstance().clipPlaceholderAPIManager != null;
    }

    public static boolean isMVdWPlaceholderAPIHooked() {
        return FactionsPlugin.getInstance().mvdwPlaceholderAPIManager;
    }

}
