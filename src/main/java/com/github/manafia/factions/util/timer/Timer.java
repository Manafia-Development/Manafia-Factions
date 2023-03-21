package com.github.manafia.factions.util.timer;

import com.github.manafia.factions.zcore.file.CustomFile;

/**
 * Factions - Developed by Driftay.
 * All rights reserved 2020.
 * Creation Date: 4/7/2020
 */
public abstract class Timer {
    public final long defaultCooldown;
    protected final String name;


    public Timer(String name, long defaultCooldown) {
        this.name = name;
        this.defaultCooldown = defaultCooldown;
    }

    public String getName() {
        return this.name;
    }

    public void load(CustomFile config) {
    }


    public void save(CustomFile config) {
    }
}