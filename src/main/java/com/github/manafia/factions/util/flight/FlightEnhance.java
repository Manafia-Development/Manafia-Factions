package com.github.manafia.factions.util.flight;

import com.github.manafia.factions.FLocation;
import com.github.manafia.factions.FPlayer;
import com.github.manafia.factions.FPlayers;
import com.github.manafia.factions.FactionsPlugin;
import com.github.manafia.factions.cmd.CmdFly;
import com.github.manafia.factions.listeners.FactionsEntityListener;
import org.bukkit.GameMode;

/**
 * SaberFactions - Developed by Driftay.
 * All rights reserved 2020.
 * Creation Date: 9/15/2020
 */
public class FlightEnhance implements Runnable {

    @Override
    public void run() {
        for (FPlayer player : FPlayers.getInstance().getOnlinePlayers()) {
            if (!player.isFlying()) continue;

            if (player.isAdminBypassing()
                    || player.getPlayer().isOp()
                    || player.getPlayer().getGameMode() == GameMode.CREATIVE
                    || player.getPlayer().getGameMode() == GameMode.SPECTATOR) continue;

            FLocation fLocation = new FLocation(player.getPlayer().getLocation());

            player.checkIfNearbyEnemies();

            if (!player.hasEnemiesNearby()) {
                if (player.isFlying()) {
                    if (!player.canFlyAtLocation(fLocation)) {
                        player.setFlying(false, false);
                    }
                } else if (player.canFlyAtLocation()
                        && FactionsPlugin.getInstance().getConfig().getBoolean("ffly.AutoEnable")
                        && !FactionsEntityListener.combatList.contains(player.getPlayer().getUniqueId())
                        && !CmdFly.falseList.contains(player.getPlayer().getUniqueId())) {
                    player.setFlying(true);
                }
            }
        }
    }
}