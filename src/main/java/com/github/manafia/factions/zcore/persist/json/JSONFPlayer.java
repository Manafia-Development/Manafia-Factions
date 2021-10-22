package com.github.manafia.factions.zcore.persist.json;

import com.github.manafia.factions.Conf;
import com.github.manafia.factions.FPlayers;
import com.github.manafia.factions.zcore.persist.MemoryFPlayer;

public class JSONFPlayer extends MemoryFPlayer {

    public JSONFPlayer(MemoryFPlayer arg0) {
        super(arg0);
    }

    public JSONFPlayer(String id) {
        super(id);
    }

    @Override
    public void remove() {
        ((JSONFPlayers) FPlayers.getInstance()).fPlayers.remove(getId());
    }

    public boolean shouldBeSaved() {
        return this.hasFaction() || (this.getPowerRounded() != this.getPowerMaxRounded() && this.getPowerRounded() != (int) Math.round(Conf.powerPlayerStarting));
    }
}
