package com.github.manafia.factions.zcore.persist;

import com.github.manafia.factions.Board;
import com.github.manafia.factions.FPlayers;
import com.github.manafia.factions.Factions;
import com.github.manafia.factions.zcore.MPlugin;

public class SaveTask implements Runnable {

    private static boolean running = false;

    MPlugin p;

    public SaveTask(MPlugin p) {
        this.p = p;
    }

    public void run() {
        if (!p.getAutoSave() || running) return;
        running = true;
        p.preAutoSave();
        Factions.getInstance().forceSave(false);
        FPlayers.getInstance().forceSave(false);
        Board.getInstance().forceSave(false);
        p.postAutoSave();
        running = false;
    }
}
