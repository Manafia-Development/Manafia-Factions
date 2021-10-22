package com.github.manafia.factions.util;

import com.github.manafia.factions.Board;
import com.github.manafia.factions.Conf;
import com.github.manafia.factions.FLocation;
import com.github.manafia.factions.Faction;

/**
 * @author Saser
 */
public class SpawnerChunkUtil {

    public static boolean isSpawnerChunk(FLocation fLocation) {
        if (Conf.userSpawnerChunkSystem) {
            FastChunk fastChunk = new FastChunk(fLocation);
            Faction faction = Board.getInstance().getFactionAt(fLocation);
            if (faction.getSpawnerChunks() != null) {
                return faction.getSpawnerChunks().contains(fastChunk);
            }
        }
        return false;
    }

}