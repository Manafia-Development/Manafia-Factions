package com.github.manafia.factions.cmd;

import com.github.manafia.factions.Util;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.zcore.util.TL;

public class CmdPlayerTitleToggle extends FCommand {
    public CmdPlayerTitleToggle() {
        super();
        this.aliases.addAll(Aliases.titles);
        this.requirements = new CommandRequirements.Builder(Permission.TOGGLE_TITLES)
                .build();
    }

    @Override
    public void perform(CommandContext context) {
        context.fPlayer.setTitlesEnabled(!context.fPlayer.hasTitlesEnabled());
        context.msg(TL.COMMAND_TITLETOGGLE_TOGGLED, context.fPlayer.hasTitlesEnabled() ? Util.color("&dEnabled") : Util.color("&dDisabled"));
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_TITLETOGGLE_DESCRIPTION;
    }
}
