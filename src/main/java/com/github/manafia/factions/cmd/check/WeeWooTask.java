package com.github.manafia.factions.cmd.check;

import com.github.manafia.factions.Faction;
import com.github.manafia.factions.Factions;
import com.github.manafia.factions.FactionsPlugin;
import com.github.manafia.factions.zcore.util.TL;

public class WeeWooTask implements Runnable {

    /**
     * @author Driftay
     */

    private final FactionsPlugin plugin;

    public WeeWooTask (FactionsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run () {
        for (Faction faction : Factions.getInstance().getAllFactions()) {
            if (!faction.isWeeWoo())
                continue;
            faction.msg(TL.WEE_WOO_MESSAGE);
        }
    }
}
