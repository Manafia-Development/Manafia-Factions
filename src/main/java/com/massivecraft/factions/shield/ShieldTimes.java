package com.massivecraft.factions.shield;

import com.massivecraft.factions.FactionsPlugin;
import org.bukkit.Bukkit;

import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;

public class ShieldTimes {
    private static final Map<LocalTime, LocalTime> times = new LinkedHashMap<>();

    static {
        int diff = FactionsPlugin.getInstance().getConfig().getInt("Shields.Duration");
        int total = -diff;
        while (total < 24 * diff) {
            times.put(LocalTime.MIDNIGHT.plusMinutes(total + diff), LocalTime.MIDNIGHT.plusMinutes((total += diff) + diff));
        }
        Bukkit.getLogger().log(Level.INFO, times.toString());
    }

    public static Map<LocalTime, LocalTime> getAllTimes() {
        return times;
    }


}
