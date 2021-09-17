package com.massivecraft.factions.zcore.file.impl;

import com.massivecraft.factions.FactionsPlugin;
import com.massivecraft.factions.zcore.file.CustomFile;

import java.io.File;

/**
 * Factions - Developed by Driftay.
 * All rights reserved 2020.
 * Creation Date: 4/4/2020
 */
public class FileManager {

    private final CustomFile shop = new CustomFile(new File(FactionsPlugin.getInstance().getDataFolder() + "/shop.yml"));
    private final CustomFile permissions = new CustomFile(new File(FactionsPlugin.getInstance().getDataFolder() + File.separator + "configurations" + File.separator + "/permissions.yaml"));
    private final CustomFile lunar = new CustomFile(new File(FactionsPlugin.getInstance().getDataFolder() + File.separator + "integrations" + File.separator + "/lunar.yaml"));
    private final CustomFile badlion = new CustomFile(new File(FactionsPlugin.getInstance().getDataFolder() + File.separator + "integrations" + File.separator + "/badlion.yaml"));
    private final CustomFile raids = new CustomFile(new File(FactionsPlugin.getInstance().getDataFolder() + "/raids.yaml"));




    public void setupFiles() {
        shop.setup(true, "");
        permissions.setup(true, "configurations");
        lunar.setup(true, "integrations");
        badlion.setup(true, "integrations");
        raids.setup(true, "");
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

    public CustomFile getRaids() {
        return raids;
    }




}
