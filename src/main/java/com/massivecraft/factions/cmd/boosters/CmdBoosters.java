package com.massivecraft.factions.cmd.boosters;

import com.massivecraft.factions.boosters.BoostersGUIFrame;
import com.massivecraft.factions.cmd.Aliases;
import com.massivecraft.factions.cmd.CommandContext;
import com.massivecraft.factions.cmd.CommandRequirements;
import com.massivecraft.factions.cmd.FCommand;
import com.massivecraft.factions.struct.Permission;
import com.massivecraft.factions.struct.Role;
import com.massivecraft.factions.zcore.util.TL;

public class CmdBoosters extends FCommand {

    public CmdBoosters() {
        super();
        this.aliases.addAll(Aliases.boostersGUI);
        this.requirements = new CommandRequirements.Builder(Permission.BOOSTER_GUI).playerOnly().memberOnly().withRole(Role.MODERATOR).build();
    }

    @Override
    public void perform(CommandContext context) {
        new BoostersGUIFrame(context.fPlayer).open(context.player);
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_BOOSTERS_DESCRIPTION;
    }
}