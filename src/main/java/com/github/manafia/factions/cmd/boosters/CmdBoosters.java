package com.github.manafia.factions.cmd.boosters;

import com.github.manafia.factions.boosters.BoostersGUIFrame;
import com.github.manafia.factions.cmd.Aliases;
import com.github.manafia.factions.cmd.CommandContext;
import com.github.manafia.factions.cmd.CommandRequirements;
import com.github.manafia.factions.cmd.FCommand;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.struct.Role;
import com.github.manafia.factions.zcore.util.TL;

public class CmdBoosters extends FCommand {

    public CmdBoosters () {
        super();
        this.aliases.addAll(Aliases.boostersGUI);
        this.requirements = new CommandRequirements.Builder(Permission.BOOSTER_GUI).playerOnly().memberOnly().withRole(Role.MODERATOR).build();
    }

    @Override
    public void perform (CommandContext context) {
        new BoostersGUIFrame(context.fPlayer).open(context.player);
    }

    @Override
    public TL getUsageTranslation () {
        return TL.COMMAND_BOOSTERS_DESCRIPTION;
    }
}