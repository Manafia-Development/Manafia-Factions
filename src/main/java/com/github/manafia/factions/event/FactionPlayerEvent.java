package com.github.manafia.factions.event;

import com.github.manafia.factions.FPlayer;
import com.github.manafia.factions.Faction;

/**
 * Represents an event involving a Faction and a FPlayer.
 */
public class FactionPlayerEvent extends FactionEvent {

    /**
     * @author FactionsUUID Team - Modified By CmdrKittens
     */

    protected final FPlayer fPlayer;

    public FactionPlayerEvent(Faction faction, FPlayer fPlayer) {
        super(faction);
        this.fPlayer = fPlayer;
    }

    public FPlayer getfPlayer() {
        return this.fPlayer;
    }
}
