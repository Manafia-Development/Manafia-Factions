package com.massivecraft.factions.listeners;

public class NewRaidListener extends Listener {


  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onEntityExplode(EntityExplodeEvent event) {

    Location location = e.getBlockPlaced().getLocation();
    FLocation fLoc = new FLocation(location);
//    Faction fac = Board.getInstance().getFactionAt(fLoc);
    Faction myFaction = me.getFaction();
    Faction otherFaction = Board.getInstance().getFactionAt(loc);
    Relation rel = myFaction.getRelationTo(otherFaction);


  if (FactionsPlugin.getInstance().getFileManager().getraids().getConfig().getBoolean("Enabled", true)) {
    if (rel.isEnemy()) {

      public boolean RaidStarted = true;

    }
  }




  }
}
