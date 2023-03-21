package com.github.manafia.factions.cmd;

import com.cryptomorin.xseries.XMaterial;
import com.github.manafia.factions.FLocation;
import com.github.manafia.factions.FactionsPlugin;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.util.VisualizeUtil;
import com.github.manafia.factions.zcore.util.TL;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

import java.awt.*;
import java.util.HashMap;

public class CmdSeeChunk extends FCommand {

    //Used a hashmap cuz imma make a particle selection gui later, will store it where the boolean is rn.
    public static HashMap<String, Boolean> seeChunkMap = new HashMap<>();
    Long interval;
    private boolean useParticles;
    private ParticleEffect effect;
    private int taskID = -1;


    //I remade it cause of people getting mad that I had the same seechunk as drtshock


    public CmdSeeChunk() {
        super();
        aliases.addAll(Aliases.seeChunk);
        effect = ParticleEffect.valueOf(FactionsPlugin.getInstance().getConfig().getString("see-chunk.particle-type"));
        if(effect == null) {
            effect = ParticleEffect.REDSTONE;
        }

        this.useParticles = FactionsPlugin.getInstance().getConfig().getBoolean("see-chunk.particles", true);
        interval = FactionsPlugin.getInstance().getConfig().getLong("see-chunk.interval", 10L);

        this.requirements = new CommandRequirements.Builder(Permission.SEECHUNK)
                .playerOnly()
                .build();

    }

    @Override
    public void perform(CommandContext context) {
        if (seeChunkMap.containsKey(context.player.getName())) {
            seeChunkMap.remove(context.player.getName());
            context.msg(TL.COMMAND_SEECHUNK_DISABLED);
        } else {
            seeChunkMap.put(context.player.getName(), true);
            context.msg(TL.COMMAND_SEECHUNK_ENABLED);
            manageTask();
        }
    }

    private void manageTask() {
        if (taskID != -1) {
            if (seeChunkMap.isEmpty()) {
                Bukkit.getScheduler().cancelTask(taskID);
                taskID = -1;
            }
        } else {
            startTask();
        }
    }

    private void startTask() {
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(FactionsPlugin.getInstance(), () -> {
            for (Object nameObject : seeChunkMap.keySet()) {
                String name = nameObject + "";
                Player player = Bukkit.getPlayer(name);
                assert player != null;
                showBorders(player);
            }
            manageTask();
        }, 0, interval);
    }

    private void showBorders(Player me) {
        World world = me.getWorld();
        FLocation flocation = new FLocation(me);
        int chunkX = (int) flocation.getX();
        int chunkZ = (int) flocation.getZ();

        int blockX;
        int blockZ;

        blockX = chunkX * 16;
        blockZ = chunkZ * 16;
        showPillar(me, world, blockX, blockZ);


        blockX = chunkX * 16 + 15;
        blockZ = chunkZ * 16;
        showPillar(me, world, blockX, blockZ);

        blockX = chunkX * 16;
        blockZ = chunkZ * 16 + 15;
        showPillar(me, world, blockX, blockZ);

        blockX = chunkX * 16 + 15;
        blockZ = chunkZ * 16 + 15;
        showPillar(me, world, blockX, blockZ);
    }

    private void showPillar(Player player, World world, int blockX, int blockZ) {
        for (int blockY = player.getLocation().getBlockY() - 15; blockY < player.getLocation().getBlockY() + 15; blockY++) {
            Location loc = new Location(world, blockX, blockY, blockZ).add(0.5, 0, 0.5);
            if (loc.getBlock().getType() != Material.AIR) {
                continue;
            }
            if (useParticles) {
                new ParticleBuilder(this.effect, loc).setColor(Color.RED).display(player);
                    //this.effect.display(loc, player);
            } else {
                Material type = blockY % 5 == 0 ? XMaterial.REDSTONE_LAMP.parseMaterial() : XMaterial.BLACK_STAINED_GLASS.parseMaterial();
                VisualizeUtil.addLocation(player, loc, type);
            }
        }
    }


    @Override
    public TL getUsageTranslation() {
        return TL.GENERIC_PLACEHOLDER;
    }

}
