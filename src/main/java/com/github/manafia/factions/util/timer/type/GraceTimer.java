package com.github.manafia.factions.util.timer.type;

import com.github.manafia.factions.Conf;
import com.github.manafia.factions.FPlayer;
import com.github.manafia.factions.FPlayers;
import com.github.manafia.factions.util.timer.GlobalTimer;
import com.github.manafia.factions.zcore.file.CustomFile;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.concurrent.TimeUnit;

/**
 * Factions - Developed by Driftay.
 * All rights reserved 2020.
 * Creation Date: 4/7/2020
 */
public class GraceTimer extends GlobalTimer implements Listener {

    public GraceTimer() {
        super("GRACE", TimeUnit.DAYS.toMillis(Conf.gracePeriodTimeDays));
    }

    @EventHandler
    public void onBreak(EntityExplodeEvent e) {
        if (getRemaining() > 0)
            e.setCancelled(true);
    }

    @EventHandler
    public void onTNTPlace(BlockPlaceEvent event) {
        FPlayer fp = FPlayers.getInstance().getByPlayer(event.getPlayer());
        if (getRemaining() > 0) {
            if (!fp.isAdminBypassing()) {
                if (event.getBlock().getType().equals(Material.TNT)) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @Override
    public void load(CustomFile config) {
        setPaused(config.getConfig().getBoolean(this.name + ".paused"));
        setRemaining(config.getConfig().getLong(this.name + ".time"), false);
    }

    @Override
    public void save(CustomFile config) {
        config.getConfig().set(this.name + ".paused", isPaused());
        config.getConfig().set(this.name + ".time", getRemaining());
    }

}
