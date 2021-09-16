package com.massivecraft.factions.zcore.file.impl;

import com.massivecraft.factions.FactionsPlugin;
import com.massivecraft.factions.zcore.file.CustomFile;

import java.awt.color.ICC_Profile;
import java.io.File;

/**
 * Factions - Developed by Driftay.
 * All rights reserved 2020.
 * Creation Date: 4/4/2020
 */
public class FileManager {

    private final CustomFile shop = new CustomFile(new File(FactionsPlugin.getInstance().getDataFolder() + "/shop.yml"));
    private final CustomFile permissions = new CustomFile(new File(FactionsPlugin.getInstance().getDataFolder() + "/permissions.yml"));
    private final CustomFile lunar = new CustomFile(new File(FactionsPlugin.getInstance().getDataFolder() + File.seperator + "integrations" + File.seperator + "/lunar.yml"));
    //private final CustomFile badlion = new CustomFile(new File(FactionsPlugin.getInstance().getDataFolder() + File.seperator + "integrations" + File.seperator + "/badlion.yaml"));



    public void setupFiles() {
        shop.setup(true, "");
        permissions.setup(true, "");
        lunar.setup(true, "integrations");
       // badlion.setup(true, "integrations")
    }

    public CustomFile getPermissions() {
        return permissions;
    }

    public CustomFile getShop() {
        return shop;
    }

    public CustomFile getLunar() {
        return lunar;
    }



}
