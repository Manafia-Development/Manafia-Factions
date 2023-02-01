package com.github.manafia.factions.cmd.claim;

import com.github.manafia.factions.Board;
import com.github.manafia.factions.Conf;
import com.github.manafia.factions.Factions;
import com.github.manafia.factions.FactionsPlugin;
import com.github.manafia.factions.cmd.Aliases;
import com.github.manafia.factions.cmd.CommandContext;
import com.github.manafia.factions.cmd.CommandRequirements;
import com.github.manafia.factions.cmd.FCommand;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.util.Logger;
import com.github.manafia.factions.zcore.util.TL;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class CmdWarunclaimall extends FCommand {

    /**
     * @author FactionsUUID Team - Modified By CmdrKittens
     */

    public CmdWarunclaimall() {
        this.aliases.addAll(Aliases.unclaim_all_war);
        this.optionalArgs.put("world", "all");

        this.requirements = new CommandRequirements.Builder(Permission.MANAGE_WAR_ZONE)
                .build();
    }

    @Override
    public void perform(CommandContext context) {
        String worldName = context.argAsString(0);
        World world = null;

        if (worldName != null) world = Bukkit.getWorld(worldName);

        String id = Factions.getInstance().getWarZone().getId();

        if (world == null) {
            Board.getInstance().unclaimAll(id);
        } else {
            Board.getInstance().unclaimAllInWorld(id, world);
        }

        context.msg(TL.COMMAND_WARUNCLAIMALL_SUCCESS);
        FactionsPlugin.getInstance().getServer().getScheduler().runTaskAsynchronously(FactionsPlugin.instance, () -> {
            if (Conf.logLandUnclaims) {
                Logger.print(TL.COMMAND_WARUNCLAIMALL_LOG.format(context.fPlayer.getName()), Logger.PrefixType.DEFAULT);
            }
        });
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_WARUNCLAIMALL_DESCRIPTION;
    }

}

