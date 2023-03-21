package com.github.manafia.factions.cmd;

import com.github.manafia.factions.Conf;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.zcore.util.TL;

public class CmdBoom extends FCommand {

    /**
     * @author FactionsUUID Team - Modified By CmdrKittens
     */

    public CmdBoom() {
        super();
        this.aliases.addAll(Aliases.boom);

        //this.requiredArgs.add("");
        this.optionalArgs.put("on/off", "flip");

        this.requirements = new CommandRequirements.Builder(Permission.NO_BOOM)
                .playerOnly()
                .build();
    }

    @Override
    public void perform(CommandContext context) {
        if (!context.faction.isPeaceful()) {
            context.msg(TL.COMMAND_BOOM_PEACEFULONLY);
            return;
        }

        // if economy is enabled, they're not on the bypass list, and this command has a cost set, make 'em pay
        if (!context.payForCommand(Conf.econCostNoBoom, TL.COMMAND_BOOM_TOTOGGLE, TL.COMMAND_BOOM_FORTOGGLE)) {
            return;
        }

        context.faction.setPeacefulExplosionsEnabled(context.argAsBool(0, !context.faction.getPeacefulExplosionsEnabled()));

        String enabled = context.faction.noExplosionsInTerritory() ? TL.GENERIC_DISABLED.toString() : TL.GENERIC_ENABLED.toString();

        // Inform
        context.faction.msg(TL.COMMAND_BOOM_ENABLED, context.fPlayer.describeTo(context.faction), enabled);
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_BOOM_DESCRIPTION;
    }
}
