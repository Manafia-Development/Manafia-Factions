package com.github.manafia.factions.zcore.persist.json;

import com.github.manafia.factions.zcore.persist.MemoryFaction;

public class JSONFaction extends MemoryFaction {

    public JSONFaction(MemoryFaction arg0) {
        super(arg0);
    }

    public JSONFaction() {
    }

    public JSONFaction(String id) {
        super(id);
    }

    @Override
    public int getCloakChunkCount() {
        return 0;
    }

    @Override
    public int getAllowedCloakChunks() {
        return 0;
    }

    @Override
    public void setAllowedCloakChunks(int chunks) {

    }
}
