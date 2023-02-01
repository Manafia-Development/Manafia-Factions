package com.github.manafia.factions.util.flight.stuct;

import com.github.manafia.factions.*;
import com.github.manafia.factions.integration.LunarAPI;
import com.github.manafia.factions.util.TitleUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.ConcurrentHashMap;

/**
 * SaberFactionsX - Developed by Driftay.
 * All rights reserved 2020.
 * Creation Date: 10/27/2020
 */
public class AsyncPlayerMap implements Runnable, Listener {
    private volatile ConcurrentHashMap<String, Player> players = new ConcurrentHashMap<>();
    private volatile ConcurrentHashMap<String, Location> locations = new ConcurrentHashMap<>();

    public AsyncPlayerMap(Plugin bukkitPlugin) {
        Bukkit.getPluginManager().registerEvents(this, bukkitPlugin);
        Bukkit.getScheduler().runTaskTimer(bukkitPlugin, this, 20L, 20L);
    }

    public void run() {
        for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
            if (pl.hasMetadata("showFactionTitle")) {
                FPlayer fPlayer = FPlayers.getInstance().getByPlayer(pl);
                Faction factionTo = Board.getInstance().getFactionAt(fPlayer.getLastStoodAt());
                TitleUtil.sendFactionChangeTitle(fPlayer, factionTo);
            }
            this.locations.put(pl.getName(), pl.getLocation());
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        this.players.put(e.getPlayer().getName(), e.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        this.players.remove(e.getPlayer().getName());
    }

    public ConcurrentHashMap<String, Player> getPlayers() {
        return this.players;
    }

    public ConcurrentHashMap<String, Location> getLocations() {
        return this.locations;
    }
}
