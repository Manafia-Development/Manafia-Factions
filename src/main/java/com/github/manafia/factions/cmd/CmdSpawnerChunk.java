package com.github.manafia.factions.cmd;

import com.github.manafia.factions.FLocation;
import com.github.manafia.factions.Faction;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.struct.Role;
import com.github.manafia.factions.util.FastChunk;
import com.github.manafia.factions.zcore.fperms.PermissableAction;
import com.github.manafia.factions.zcore.util.TL;
import org.bukkit.Location;

public class CmdSpawnerChunk extends FCommand {

    public CmdSpawnerChunk() {
        super();
        this.aliases.addAll(Aliases.spawnerChunks);

        this.requirements = new CommandRequirements.Builder(Permission.SPAWNER_CHUNKS)
                .withAction(PermissableAction.TERRITORY)
                .withRole(Role.COLEADER)
                .playerOnly()
                .memberOnly()
                .build();
    }

    @Override
    public void perform(CommandContext context) {
        Faction fac = context.faction;
        Location location = context.player.getLocation();
        FastChunk fastChunk = new FastChunk(location.getWorld().getName(), location.getChunk().getX(), location.getChunk().getZ());
        if (fac.getSpawnerChunkCount() < fac.getAllowedSpawnerChunks()) {
            if (context.fPlayer.attemptClaim(fac, new FLocation(context.player.getLocation()), true)) {
                if (!fac.getSpawnerChunks().contains(fastChunk)) {
                    fac.getSpawnerChunks().add(fastChunk);
                    context.fPlayer.msg(TL.COMMAND_SPAWNERCHUNK_CLAIM_SUCCESSFUL);
                } else {
                    context.fPlayer.msg(TL.COMMAND_SPAWNERCHUNK_ALREADY_CHUNK);
                }
            }
        } else {
            context.fPlayer.msg(TL.COMMAND_SPAWNERCHUNK_PAST_LIMIT, fac.getAllowedSpawnerChunks());
        }
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_SPAWNERCHUNK_DESCRIPTION;
    }
}
