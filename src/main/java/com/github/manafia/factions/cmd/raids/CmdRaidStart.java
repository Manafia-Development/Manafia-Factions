package com.github.manafia.factions.cmd.raids;

import com.github.manafia.factions.cmd.Aliases;
import com.github.manafia.factions.cmd.CommandContext;
import com.github.manafia.factions.cmd.CommandRequirements;
import com.github.manafia.factions.cmd.FCommand;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.zcore.util.TL;

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