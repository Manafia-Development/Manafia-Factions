package com.github.manafia.factions.cmd.points;

import com.github.manafia.factions.Faction;
import com.github.manafia.factions.cmd.Aliases;
import com.github.manafia.factions.cmd.CommandContext;
import com.github.manafia.factions.cmd.CommandRequirements;
import com.github.manafia.factions.cmd.FCommand;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.zcore.util.TL;

/**
 * Factions - Developed by Driftay.
 * All rights reserved 2020.
 * Creation Date: 3/30/2020
 */
public class CmdPointsBalance extends FCommand {

    public CmdPointsBalance() {
        super();
        this.aliases.addAll(Aliases.points_balance);

        this.optionalArgs.put("faction", "yours");

        this.requirements = new CommandRequirements.Builder(Permission.POINTS)
                .build();
    }


    @Override
    public void perform(CommandContext context) {
        Faction faction;

        if (context.argIsSet(0)) {
            faction = context.argAsFaction(0);
        } else if (context.faction.isNormal()) {
            context.msg(TL.COMMAND_POINTS_SHOW_OWN, context.faction.getPoints());
            return;
        } else {
            context.msg(TL.COMMAND_POINTS_SHOW_WILDERNESS);
            return;
        }

        if (faction == null) return;

        if (faction != context.faction && !context.fPlayer.isAdminBypassing()) return;

        context.msg(TL.COMMAND_POINTS_SHOW_OTHER.toString().replace("{faction}", faction.getTag()).replace("{points}", faction.getPoints() + ""));

    }


    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_POINTS_SHOW_DESCRIPTION;
    }
}
