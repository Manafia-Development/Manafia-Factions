package com.github.manafia.factions.cmd.logout;

import com.github.manafia.factions.Conf;
import com.github.manafia.factions.cmd.Aliases;
import com.github.manafia.factions.cmd.CommandContext;
import com.github.manafia.factions.cmd.CommandRequirements;
import com.github.manafia.factions.cmd.FCommand;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.zcore.util.TL;

public class CmdLogout extends FCommand {

    public CmdLogout () {
        super();
        this.aliases.addAll(Aliases.logout);

        this.requirements = new CommandRequirements.Builder(Permission.LOGOUT)
                .playerOnly()
                .build();
    }

    @Override
    public void perform (CommandContext context) {
        LogoutHandler handler = LogoutHandler.getByName(context.player.getName());

        if (handler.isLogoutActive(context.player)) {
            context.msg(TL.COMMAND_LOGOUT_ACTIVE);
            return;
        }

        handler.applyLogoutCooldown(context.player);
        context.msg(TL.COMMAND_LOGOUT_LOGGING, Conf.logoutCooldown);
    }

    @Override
    public TL getUsageTranslation () {
        return TL.COMMAND_LOGOUT_DESCRIPTION;
    }
}
