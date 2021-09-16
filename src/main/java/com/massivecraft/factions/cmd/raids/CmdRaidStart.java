package com.massivecraft.factions.cmd.raids;

import com.massivecraft.factions.cmd.Aliases;
import com.massivecraft.factions.cmd.CommandContext;
import com.massivecraft.factions.cmd.CommandRequirements;
import com.massivecraft.factions.cmd.FCommand;
import com.massivecraft.factions.struct.Permission;
import com.massivecraft.factions.zcore.util.TL;

public class CmdRaidStart extends FCommand {

    public CmdRaidStart () {
        super();
        this.aliases.addAll(Aliases.raid_start);

        this.requiredArgs.add("faction");

        this.requirements = new CommandRequirements.Builder(Permission.RAID_START).build();
    }


    @Override
    public void perform (CommandContext context) {

    }

    @Override
    public TL getUsageTranslation () {
        return TL.COMMAND_RAID_DESCRIPTION;
    }
}