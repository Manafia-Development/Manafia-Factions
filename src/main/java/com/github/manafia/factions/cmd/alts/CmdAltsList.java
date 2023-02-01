package com.github.manafia.factions.cmd.alts;

import com.google.common.base.Joiner;
import com.github.manafia.factions.FPlayer;
import com.github.manafia.factions.Faction;
import com.github.manafia.factions.cmd.Aliases;
import com.github.manafia.factions.cmd.CommandContext;
import com.github.manafia.factions.cmd.CommandRequirements;
import com.github.manafia.factions.cmd.FCommand;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.zcore.util.TL;

import java.util.stream.Collectors;

public class CmdAltsList extends FCommand {

    /**
     * @author Driftay
     */

    public CmdAltsList() {
        super();
        this.aliases.addAll(Aliases.alts_list);
        this.optionalArgs.put("faction", "yours");


        this.requirements = new CommandRequirements.Builder(Permission.LIST)
                .playerOnly()
                .memberOnly()
                .build();

    }

    @Override
    public void perform(CommandContext context) {
        Faction faction = context.faction;
        if (context.argIsSet(0)) {
            faction = context.argAsFaction(0);
        }
        if (faction == null)
            return;

        if (faction != context.faction && !context.fPlayer.isAdminBypassing()) {
            return;
        }

        if (faction.getAltPlayers().size() == 0) {
            context.msg(TL.COMMAND_ALTS_LIST_NOALTS, faction.getTag());
            return;
        }

        context.msg("There are " + faction.getAltPlayers().size() + " alts in " + faction.getTag() + ":");
        context.msg(Joiner.on(", ").join(faction.getAltPlayers().stream().map(FPlayer::getName).collect(Collectors.toList())));
    }


    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_ALTS_LIST_DESCRIPTION;
    }
}
