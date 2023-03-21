package com.github.manafia.factions.zcore.persist.json;

import com.google.gson.reflect.TypeToken;
import com.github.manafia.factions.Board;
import com.github.manafia.factions.FLocation;
import com.github.manafia.factions.FactionsPlugin;
import com.github.manafia.factions.util.Logger;
import com.github.manafia.factions.zcore.persist.MemoryBoard;
import com.github.manafia.factions.zcore.util.DiscUtil;

import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;


public class JSONBoard extends MemoryBoard {
    private static transient File file = new File(FactionsPlugin.getInstance().getDataFolder(), "board.json");

    // -------------------------------------------- //
    // Persistance
    // -------------------------------------------- //

    public Map<String, Map<String, String>> dumpAsSaveFormat() {
        Map<String, Map<String, String>> worldCoordIds = new HashMap<>();

        String worldName, coords;
        String id;

        for (Entry<FLocation, String> entry : flocationIds.entrySet()) {
            worldName = entry.getKey().getWorldName();
            coords = entry.getKey().getCoordString();
            id = entry.getValue();
            if (!worldCoordIds.containsKey(worldName)) {
                worldCoordIds.put(worldName, new TreeMap<>());
            }

            worldCoordIds.get(worldName).put(coords, id);
        }

        return worldCoordIds;
    }

    public void loadFromSaveFormat(Map<String, Map<String, String>> worldCoordIds) {
        flocationIds.clear();

        String worldName;
        String[] coords;
        int x, z;
        String factionId;

        for (Entry<String, Map<String, String>> entry : worldCoordIds.entrySet()) {
            worldName = entry.getKey();
            for (Entry<String, String> entry2 : entry.getValue().entrySet()) {
                coords = entry2.getKey().trim().split("[,\\s]+");
                x = Integer.parseInt(coords[0]);
                z = Integer.parseInt(coords[1]);
                factionId = entry2.getValue();
                flocationIds.put(new FLocation(worldName, x, z), factionId);
            }
        }
    }

    public void forceSave() {
        forceSave(true);
    }

    public void forceSave(boolean sync) {
        DiscUtil.writeCatch(file, FactionsPlugin.getInstance().gson.toJson(dumpAsSaveFormat()), sync);
    }

    public boolean load() {
        Logger.print("Loading board from disk", Logger.PrefixType.DEFAULT);

        if (!file.exists()) {
            Logger.print("No board to load from disk. Creating new file.", Logger.PrefixType.DEFAULT);
            forceSave();
            return true;
        }

        try {
            Type type = new TypeToken<Map<String, Map<String, String>>>() {
            }.getType();
            Map<String, Map<String, String>> worldCoordIds = FactionsPlugin.getInstance().gson.fromJson(DiscUtil.read(file), type);
            loadFromSaveFormat(worldCoordIds);
            Logger.print("Loaded " + flocationIds.size() + " board locations", Logger.PrefixType.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.print("Failed to load the board from disk.", Logger.PrefixType.FAILED);
            return false;
        }

        return true;
    }

    @Override
    public void convertFrom(MemoryBoard old) {
        this.flocationIds = old.flocationIds;
        forceSave();
        Board.instance = this;
    }
}
