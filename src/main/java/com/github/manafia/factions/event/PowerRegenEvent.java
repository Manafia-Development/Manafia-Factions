package com.github.manafia.factions.event;

import com.github.manafia.factions.FPlayer;
import com.github.manafia.factions.Faction;
import org.bukkit.event.Cancellable;

/**
 * Event called when a player regenerate power.
 */
public class PowerRegenEvent extends FactionPlayerEvent implements Cancellable {

    private boolean cancelled = false;
    private double delta;

    public PowerRegenEvent(Faction f, FPlayer p, double delta) {
        super(f, p);
        this.delta = delta;
    }

    public double getDelta() {
        return delta;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean c) {
        this.cancelled = c;
    }

}