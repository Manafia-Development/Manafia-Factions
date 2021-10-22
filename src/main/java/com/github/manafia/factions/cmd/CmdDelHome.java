package com.github.manafia.factions.cmd;

import com.github.manafia.factions.Conf;
import com.github.manafia.factions.FactionsPlugin;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.zcore.fperms.PermissableAction;
import com.github.manafia.factions.zcore.util.TL;

/**
 * Factions - Developed by Driftay.
 * All rights reserved 2020.
 * Creation Date: 3/24/2020
 */
public class CmdDelHome extends FCommand {

    public CmdDelHome() {
        this.aliases.addAll(Aliases.delfHome);

        this.requirements = new CommandRequirements.Builder(Permission.DELHOME)
                .memberOnly()
                .withAction(PermissableAction.SETHOME)
                .build();
    }


    @Override
    public void perform(CommandContext context) {
        FactionsPlugin.getInstance().getServer().getScheduler().runTaskAsynchronously(FactionsPlugin.getInstance(), () -> {
            //Check if homes are enabled
            if (!Conf.homesEnabled) {
                context.msg(TL.COMMAND_SETHOME_DISABLED);
                return;
            }
            //If They Don't Have Home
            if (!context.faction.hasHome()) {
                context.msg(TL.COMMAND_HOME_NOHOME.toString());
                context.msg(FactionsPlugin.getInstance().cmdBase.cmdSethome.getUsageTemplate(context));
                return;
            }

            context.faction.deleteHome();
            context.faction.msg(TL.COMMAND_DELHOME_SUCCESS, context.fPlayer.describeTo(context.faction, true));

        });
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_DELHOME_DESCRIPTION;
    }
}
