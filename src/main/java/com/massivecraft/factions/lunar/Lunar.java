package com.massivecraft.factions.lunar;

import com.lunarclient.bukkitapi.LunarClientAPI;
import com.massivecraft.factions.FactionsPlugin;
import com.massivecraft.factions.Util;
import org.bukkit.ChatColor;
import org.bukkit.event.player.PlayerLoginEvent;



public class Lunar {




    public static void lunarSetup() {

        if (FactionsPlugin.getInstance().getFileManager().getLunar().getConfig().getBoolean("Integration.Enabled", true))

        System.out.println("Initializing lunar setup...");
        Util.checkLunar();
        System.out.println("This has not yet been enabled! More features coming soon!");

    //    LunarMods.lunarmods();




    }

}
