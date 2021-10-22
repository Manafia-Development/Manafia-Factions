package com.massivecraft.factions.listeners;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.FactionsPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityExplodeEvent;

public class NewRaidListener extends Listener {


    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityExplode(EntityExplodeEvent event) {

        Location location = e.getBlockPlaced().getLocation();
        FLocation fLoc = new FLocation(location);
//    Faction fac = Board.getInstance().getFactionAt(fLoc);
        Faction myFaction = me.getFaction();
        Faction otherFaction = Board.getInstance().getFactionAt(loc);
        com.massivecraft.factions.struct.Relation rel = myFaction.getRelationTo(otherFaction);


        if (FactionsPlugin.getInstance().getFileManager().getraids().getConfig().getBoolean("Enabled", true)) {
            if (rel.isEnemy()) {

                public boolean RaidStarted = true;

            }
        }


    }
}
