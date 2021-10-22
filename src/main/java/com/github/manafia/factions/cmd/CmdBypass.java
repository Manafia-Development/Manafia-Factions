package com.github.manafia.factions.cmd;

import com.github.manafia.factions.FactionsPlugin;
import com.github.manafia.factions.struct.Permission;
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
            FactionsPlugin.getInstance().log(context.fPlayer.getName() + TL.COMMAND_BYPASS_ENABLELOG);
        } else {
            context.fPlayer.msg(TL.COMMAND_BYPASS_DISABLE.toString());
            FactionsPlugin.getInstance().log(context.fPlayer.getName() + TL.COMMAND_BYPASS_DISABLELOG);
        }
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_BYPASS_DESCRIPTION;
    }
}