package com.github.manafia.factions.util;

import com.github.manafia.factions.*;

public class CloakUtil {

    public static boolean isCloaked (FLocation fLocation) {
        if (FactionsPlugin.getInstance().getFileManager().getCloaks().getConfig().getBoolean("Enabled")) {
            CloakChunk cloakChunk = new CloakChunk(fLocation);
            Faction faction = Board.getInstance().getFactionAt(fLocation);
            if (faction.getCloakChunks() != null) {
                return faction.getCloakChunks().contains(cloakChunk);
            }
        }
        return false;
    }

}
