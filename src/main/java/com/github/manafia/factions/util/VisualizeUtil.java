package com.github.manafia.factions.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.*;

public class VisualizeUtil {

    protected static Map<UUID, Set<Location>> playerLocations = new HashMap<>();

    public static Set<Location> getPlayerLocations(Player player) {
        return getPlayerLocations(player.getUniqueId());
    }

    public static Set<Location> getPlayerLocations(UUID uuid) {
        return playerLocations.computeIfAbsent(uuid, k -> new HashSet<>());
    }

    public static Set<Location> getPlayerLocationsRaw(UUID uuid) {
        return playerLocations.get(uuid);
    }

    @SuppressWarnings("deprecation")
    public static void addLocation(Player player, Location location, Material type, byte data) {
        getPlayerLocations(player).add(location);
        player.sendBlockChange(location, type, data);
    }

    @SuppressWarnings("deprecation")
    public static void addLocation(Player player, Location location, Material material) {
        getPlayerLocations(player).add(location);
        player.sendBlockChange(location, material, (byte) 0);
    }


    @SuppressWarnings("deprecation")
    public static void addLocations(Player player, Collection<Location> locations, Material material) {
        Set<Location> ploc = getPlayerLocations(player);
        for (Location location : locations) {
            ploc.add(location);
            player.sendBlockChange(location, material, (byte) 0);
        }
    }

    @SuppressWarnings("deprecation")
    public static void addBlocks(Player player, Collection<Block> blocks, Material material) {
        Set<Location> ploc = getPlayerLocations(player);
        for (Block block : blocks) {
            Location location = block.getLocation();
            ploc.add(location);
            player.sendBlockChange(location, material, (byte) 0);
        }
    }

    @SuppressWarnings("deprecation")
    public static void clear(Player player) {
        Set<Location> locations = getPlayerLocationsRaw(player.getUniqueId());
        if (locations == null) {
            return;
        }
        for (Location location : locations) {
            Block block = location.getWorld().getBlockAt(location);
            player.sendBlockChange(location, block.getType(), block.getData());
        }
        locations.clear();
    }

}
