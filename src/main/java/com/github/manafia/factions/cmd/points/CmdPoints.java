package com.github.manafia.factions.cmd.points;

import com.github.manafia.factions.FactionsPlugin;
import com.github.manafia.factions.cmd.Aliases;
import com.github.manafia.factions.cmd.CommandContext;
import com.github.manafia.factions.cmd.CommandRequirements;
import com.github.manafia.factions.cmd.FCommand;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.zcore.util.TL;

public class CmdPoints extends FCommand {

    /**
     * @author Driftay
     */

    public CmdPointsRemove cmdPointsRemove = new CmdPointsRemove();
    public CmdPointsSet cmdPointsSet = new CmdPointsSet();
    public CmdPointsAdd cmdPointsAdd = new CmdPointsAdd();
    public CmdPointsBalance cmdPointsBalance = new CmdPointsBalance();

    public CmdPoints() {
        super();
        this.aliases.addAll(Aliases.points_points);

        this.requirements = new CommandRequirements.Builder(Permission.POINTS)
                .playerOnly()
                .build();


        this.addSubCommand(this.cmdPointsBalance);
        this.addSubCommand(this.cmdPointsAdd);
        this.addSubCommand(this.cmdPointsRemove);
        this.addSubCommand(this.cmdPointsSet);
        this.addSubCommand(this.cmdPointsBalance);
    }


    @Override
    public void perform(CommandContext context) {
        if (!FactionsPlugin.getInstance().getConfig().getBoolean("f-points.Enabled", true)) {
            context.msg(TL.GENERIC_DISABLED, "Faction Points");
            return;
        }

        if (context.fPlayer.isAdminBypassing() || context.player.isOp()) {
            context.commandChain.add(this);
        } else {
            context.commandChain.add(this.cmdPointsBalance);
        }

        FactionsPlugin.getInstance().cmdAutoHelp.execute(context);
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_POINTS_DESCRIPTION;
    }


}
