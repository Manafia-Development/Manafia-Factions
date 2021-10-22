package com.github.manafia.factions.lunar;

import com.github.manafia.factions.FactionsPlugin;
import com.github.manafia.factions.Util;


public class Lunar {

    public static void lunarSetup() {

        System.out.println("Initializing lunar integration...");
        Util.checkLunar();
        System.out.println("WARNING: This is still in beta! Use at own risk. Many things may not work!");
    }
}
