package com.massivecraft.factions.util;

import com.cryptomorin.xseries.XMaterial;
import com.massivecraft.factions.*;
import com.massivecraft.factions.util.particle.ParticleColor;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author FactionsUUID Team - Modified By CmdrKittens
 */

public class SeeChunkUtil extends BukkitRunnable {

    private final Set<UUID> playersSeeingChunks = new HashSet<>();
    private final boolean useColor;
    private final Object effect;

    public SeeChunkUtil() {
        String effectName = FactionsPlugin.instance.getConfig().getString("see-chunk.particle-type");
        this.effect = Util.getParticleProvider().effectFromString(effectName);
        this.useColor = FactionsPlugin.instance.getConfig().getBoolean("see-chunk.use-relational-color");

        FactionsPlugin.instance.getLogger().info(FactionsPlugin.getInstance().txt.parse("Using %s as the ParticleEffect for /f sc", Util.getParticleProvider().effectName(effect)));
    }

    @Override
    public void run() {
        for (UUID playerId : playersSeeingChunks) {
            Player player = Bukkit.getPlayer(playerId);
            if (player == null) {
                playersSeeingChunks.remove(playerId);
                continue;
            }
            FPlayer fme = FPlayers.getInstance().getByPlayer(player);
            showPillars(player, fme, this.effect, useColor);
        }
    }

    public static void showPillars(Player me, FPlayer fme, Object effect, boolean useColor) {
        World world = me.getWorld();
        FLocation flocation = new FLocation(me);
        int chunkX = (int) flocation.getX();
        int chunkZ = (int) flocation.getZ();

        ParticleColor color = null;
        if (useColor) {
            ChatColor chatColor = Board.getInstance().getFactionAt(flocation).getRelationTo(fme).getColor();
            color = ParticleColor.fromChatColor(chatColor);
        }

        int blockX;
        int blockZ;

        // reference sheet for coding pillars
        //           X  Z
        // 1 - 1 => +0 +0
        // 1 - 2 => +0 +16
        // 2 - 1 => +16 +0
        // 2 - 2 => +16 +16

        blockX = chunkX * 16;
        blockZ = chunkZ * 16;

        showPillar(me, world, blockX, blockZ, effect, color);
        showPillar(me, world, blockX, blockZ + 16, effect, color);
        showPillar(me, world, blockX + 16, blockZ, effect, color);
        showPillar(me, world, blockX + 16, blockZ + 16, effect, color);
    }

    public static void showPillar(Player player, World world, int blockX, int blockZ, Object effect, ParticleColor color) {
        // Lets start at the player's Y spot -30 to optimize
        for (int blockY = player.getLocation().getBlockY() - 30; blockY < player.getLocation().getBlockY() + 30; blockY++) {
            Location loc = new Location(world, blockX, blockY, blockZ);
            if (loc.getBlock().getType() != Material.AIR)
                continue;
            if (effect != null) {
                if (color == null)
                    Util.getParticleProvider().playerSpawn(player, effect, loc, 1);
                else
                    Util.getParticleProvider().playerSpawn(player, effect, loc, color);
            } else {
                Material mat = blockY % 5 == 0 ? XMaterial.REDSTONE_LAMP.parseMaterial() : XMaterial.GLASS_PANE.parseMaterial();
                VisualizeUtil.addLocation(player, loc, mat);
            }
        }
    }

    public void updatePlayerInfo(UUID uuid, boolean toggle) {
        if (toggle)
            playersSeeingChunks.add(uuid);
        else
            playersSeeingChunks.remove(uuid);
    }
}
