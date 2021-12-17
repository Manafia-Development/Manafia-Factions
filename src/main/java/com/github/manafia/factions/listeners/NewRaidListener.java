package com.github.manafia.factions.listeners;

import com.github.manafia.factions.Board;
import com.github.manafia.factions.FLocation;
import com.github.manafia.factions.Faction;
import com.github.manafia.factions.FactionsPlugin;
import com.github.manafia.factions.cmd.raids.RaidStarted;
import com.github.manafia.factions.struct.Relation;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class NewRaidListener implements Listener {

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)

  public void onEntityExplode(EntityExplodeEvent event) {

    Location location = event.getLocation();
    FLocation fLoc = new FLocation(location);
    Faction fac = Board.getInstance().getFactionAt(fLoc);
    Faction otherFaction = Board.getInstance().getFactionAt((FLocation) fLoc);
    Relation rel = fac.getRelationTo(otherFaction);

    if (FactionsPlugin.getInstance().getFileManager().getRaids().getConfig().getBoolean("Enabled", true)) {

    if (rel.isEnemy()) {
      if (fac.getRelationTo(otherFaction).isEnemy()) {

      }

      @EventHandler
              onEntityExplode(fac.sendMessage("We have just started a raid against " + otherFaction.getTag()));

      boolean RaidStarted = true;

    }
  }

  }
}
