package com.massivecraft.factions.util;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.Conf;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.Faction;

public class CloakUtil {

    public static boolean hasCloak (FLocation fLocation) {
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
