package com.github.manafia.factions.cmd;

import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.util.Logger;
import com.github.manafia.factions.zcore.util.TL;

public class CmdBypass extends FCommand {

    /**
     * @author FactionsUUID Team - Modified By CmdrKittens
     */

    public CmdBypass() {
        super();
        this.aliases.addAll(Aliases.bypass);

        //this.requiredArgs.add("");
        this.optionalArgs.put("on/off", "flip");

        this.requirements = new CommandRequirements.Builder(Permission.BYPASS)
                .playerOnly()
                .build();
    }

    @Override
    public void perform(CommandContext context) {
        context.fPlayer.setIsAdminBypassing(context.argAsBool(0, !context.fPlayer.isAdminBypassing()));

        // TODO: Move this to a transient field in the model??
        if (context.fPlayer.isAdminBypassing()) {
            context.fPlayer.msg(TL.COMMAND_BYPASS_ENABLE.toString());
            Logger.print(context.fPlayer.getName() + TL.COMMAND_BYPASS_ENABLELOG, Logger.PrefixType.DEFAULT);
        } else {
            context.fPlayer.msg(TL.COMMAND_BYPASS_DISABLE.toString());
            Logger.print(context.fPlayer.getName() + TL.COMMAND_BYPASS_DISABLELOG, Logger.PrefixType.DEFAULT);
        }
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_BYPASS_DESCRIPTION;
    }
}