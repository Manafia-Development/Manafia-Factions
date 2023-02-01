package com.github.manafia.factions.listeners;

import com.earth2me.essentials.IEssentials;
import com.earth2me.essentials.User;
import com.github.manafia.factions.Board;
import com.github.manafia.factions.FLocation;
import com.github.manafia.factions.Faction;
import com.github.manafia.factions.event.FPlayerLeaveEvent;
import com.github.manafia.factions.util.Logger;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;
import java.util.UUID;

public class EssentialsHomeHandler implements Listener {

    /**
     * @author Driftay
     */

    private IEssentials ess;

    public EssentialsHomeHandler(IEssentials essentials) {
        this.ess = essentials;
    }

    @EventHandler
    public void onLeave(FPlayerLeaveEvent event) throws Exception {
        Faction faction = event.getFaction();
        User user = ess.getUser(UUID.fromString(event.getfPlayer().getId()));
        List<String> homes = user.getHomes();
        if (homes == null || homes.isEmpty()) {
            return;
        }
        for (String homeName : user.getHomes()) {
            Location loc = user.getHome(homeName);
            FLocation floc = new FLocation(loc);
            Faction factionAt = Board.getInstance().getFactionAt(floc);
            if (factionAt.equals(faction) && factionAt.isNormal()) {
                user.delHome(homeName);
                Logger.printArgs( "Removing home %s, player %s, in territory of %s", Logger.PrefixType.DEFAULT, homeName, event.getfPlayer().getName(), faction.getTag());
            }
        }
    }
}
