package com.github.manafia.factions.zcore.util;

import com.github.manafia.factions.Conf;
import com.github.manafia.factions.FactionsPlugin;
import com.github.manafia.factions.addon.FactionsAddon;
import com.github.manafia.factions.discord.Discord;
import com.github.manafia.factions.discord.DiscordListener;
import com.github.manafia.factions.util.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ShutdownParameter {

    public static void initShutdown(FactionsPlugin plugin) {
        Logger.print( "===== Shutdown Start =====", Logger.PrefixType.DEFAULT);
        Conf.saveSync();
        FactionsPlugin.getInstance().getTimerManager().saveTimerData();
        DiscordListener.saveGuilds();
        for(FactionsAddon factionsAddon : FactionsPlugin.getInstance().getFactionsAddonHashMap().values()) {
            factionsAddon.disableAddon();
            Logger.print("Disabled " + factionsAddon.getAddonName() + " addon", Logger.PrefixType.DEFAULT);
        }

        if (Discord.jda != null) Discord.jda.shutdownNow();

        FactionsPlugin.getInstance().getFlogManager().saveLogs();
        saveReserves();

    }

    public static void saveReserves() {
        try {
            String path = Paths.get(FactionsPlugin.getInstance().getDataFolder().getAbsolutePath()).toAbsolutePath() + File.separator + "data" + File.separator + "reserves.json";
            File file = new File(path);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            Files.write(Paths.get(file.getPath()), FactionsPlugin.getInstance().getGsonBuilder().create().toJson(FactionsPlugin.getInstance().reserveObjects).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
