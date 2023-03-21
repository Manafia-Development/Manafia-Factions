package com.github.manafia.factions.cmd.points;

import com.github.manafia.factions.FPlayer;
import com.github.manafia.factions.Faction;
import com.github.manafia.factions.Factions;
import com.github.manafia.factions.cmd.Aliases;
import com.github.manafia.factions.cmd.CommandContext;
import com.github.manafia.factions.cmd.CommandRequirements;
import com.github.manafia.factions.cmd.FCommand;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.zcore.util.TL;

public class CmdPointsRemove extends FCommand {

    /**
     * @author Driftay
     */

    public CmdPointsRemove() {
        super();
        this.aliases.addAll(Aliases.points_remove);

        this.requiredArgs.add("faction/player");
        this.requiredArgs.add("# of points");

        this.requirements = new CommandRequirements.Builder(Permission.REMOVEPOINTS)
                .build();

    }


    @Override
    public void perform(CommandContext context) {
        Faction faction = Factions.getInstance().getByTag(context.args.get(0));


        FPlayer fPlayer = context.argAsFPlayer(0);
        if (fPlayer != null) {
            faction = fPlayer.getFaction();
        }

        if (faction == null || faction.isWilderness()) {
            context.msg(TL.COMMAND_POINTS_FAILURE.toString().replace("{faction}", context.args.get(0)));
            return;
        }

        if (context.argAsInt(1) <= 0) {
            context.msg(TL.COMMAND_POINTS_INSUFFICIENT);
            return;
        }

        faction.setPoints(faction.getPoints() - context.argAsInt(1));
        context.msg(TL.COMMAND_REMOVEPOINTS_SUCCESSFUL, context.argAsInt(1), faction.getTag(), faction.getPoints());
    }


    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_REMOVEPOINTS_DESCRIPTION;
    }


}
