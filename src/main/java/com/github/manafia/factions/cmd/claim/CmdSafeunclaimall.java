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
import com.github.manafia.factions.zcore.util.TL;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class CmdSafeunclaimall extends FCommand {

    /**
     * @author FactionsUUID Team - Modified By CmdrKittens
     */

    public CmdSafeunclaimall () {
        this.aliases.addAll(Aliases.unclaim_all_safe);
        this.optionalArgs.put("world", "all");

        this.requirements = new CommandRequirements.Builder(Permission.MANAGE_SAFE_ZONE)
                .build();
    }

    @Override
    public void perform (CommandContext context) {
        String worldName = context.argAsString(0);
        World world = null;

        if (worldName != null)
            world = Bukkit.getWorld(worldName);

        String id = Factions.getInstance().getSafeZone().getId();

        if (world == null)
            Board.getInstance().unclaimAll(id);
        else
            Board.getInstance().unclaimAllInWorld(id, world);

        context.msg(TL.COMMAND_SAFEUNCLAIMALL_UNCLAIMED);

        if (Conf.logLandUnclaims)
            FactionsPlugin.getInstance().log(TL.COMMAND_SAFEUNCLAIMALL_UNCLAIMEDLOG.format(context.sender.getName()));
    }

    @Override
    public TL getUsageTranslation () {
        return TL.COMMAND_SAFEUNCLAIMALL_DESCRIPTION;
    }

}
