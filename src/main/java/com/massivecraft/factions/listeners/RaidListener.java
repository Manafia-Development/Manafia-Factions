package com.massivecraft.factions.listeners;

import com.cryptomorin.xseries.XMaterial;
import com.massivecraft.factions.FactionsPlugin;
import com.massivecraft.factions.zcore.util.TL;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class RaidListener implements Listener {

    public boolean raidSpawners = false;


    @EventHandler
    public void onSpawnerMine(BlockBreakEvent event) {

     if (FactionsPlugin.getInstance().getFileManager().getRaids().getConfig().getBoolean("Mechanics.Spawner-Break", false)); {
         if (raidSpawners)
       if (event.getBlock().getType().equals(XMaterial.SPAWNER.parseMaterial()));
        event.setCancelled(true);


        if(event.isCancelled()) {
            event.getPlayer().sendMessage(String.valueOf(TL.COMMAND_RAID_SPAWNER_DENY));
            return;
        }

       }

    }
}

