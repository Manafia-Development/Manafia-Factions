package com.github.manafia.factions.cmd.check;

import com.github.manafia.factions.cmd.Aliases;
import com.github.manafia.factions.cmd.CommandContext;
import com.github.manafia.factions.cmd.CommandRequirements;
import com.github.manafia.factions.cmd.FCommand;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.zcore.util.TL;

public class CmdWeeWoo extends FCommand {

    /**
     * @author Vankka
     */

    public CmdWeeWoo () {
        this.aliases.addAll(Aliases.weewoo);
        this.requiredArgs.add("start/stop");

        this.requirements = new CommandRequirements.Builder(Permission.CHECK)
                .playerOnly()
                .memberOnly()
                .build();
    }

    public void perform (CommandContext context) {
        if (context.faction == null || !context.faction.isNormal())
            return;
        String argument = context.argAsString(0);
        boolean weewoo = context.faction.isWeeWoo();
        if (argument.equalsIgnoreCase("start")) {
            if (weewoo) {
                context.msg(TL.COMMAND_WEEWOO_ALREADY_STARTED);
                return;
            }
            context.faction.setWeeWoo(true);
            context.msg(TL.COMMAND_WEEWOO_STARTED, context.fPlayer.getNameAndTag());
        } else if (argument.equalsIgnoreCase("stop")) {
            if (!weewoo) {
                context.msg(TL.COMMAND_WEEWOO_ALREADY_STOPPED);
                return;
            }
            context.faction.setWeeWoo(false);
            context.msg(TL.COMMAND_WEEWOO_STOPPED, context.fPlayer.getNameAndTag());
        } else
            context.msg("/f weewoo <start/stop>");
    }

    public TL getUsageTranslation () {
        return TL.COMMAND_WEEWOO_DESCRIPTION;
    }
}
