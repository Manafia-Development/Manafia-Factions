package com.github.manafia.factions.util.timer;

import com.github.manafia.factions.util.Config;


public abstract class Timer {
    public final long defaultCooldown;
    protected final String name;


    public Timer(String name, long defaultCooldown) {
        this.name = name;
        this.defaultCooldown = defaultCooldown;
    }

    public void load(Config config) {
    }


    public void save(Config config) {
    }
}
