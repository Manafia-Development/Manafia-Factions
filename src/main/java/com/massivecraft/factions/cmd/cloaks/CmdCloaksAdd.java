package com.massivecraft.factions.cmd.cloaks;

import com.massivecraft.factions.Faction;
import com.massivecraft.factions.cloaks.Cloak;
import com.massivecraft.factions.cloaks.CloakType;
import com.massivecraft.factions.cmd.Aliases;
import com.massivecraft.factions.cmd.CommandContext;
import com.massivecraft.factions.cmd.CommandRequirements;
import com.massivecraft.factions.cmd.FCommand;
import com.massivecraft.factions.struct.Permission;
import com.massivecraft.factions.util.TimeUtil;
import com.massivecraft.factions.zcore.util.TL;
import org.bukkit.Bukkit;

public class CmdCloakAdd extends FCommand {

    public CmdCloakAdd () {
        super();
        this.aliases.addAll(Aliases.cloaksAdd);

        this.requiredArgs.add("faction");
        this.requiredArgs.add("type");
        this.requiredArgs.add("time");

        this.requirements = new CommandRequirements.Builder(Permission.CLOAK_ADD).playerOnly().memberOnly().build();
    }

    @Override
    public void perform (CommandContext context) {
        Faction target = context.argAsFaction(0);

        if (target == null) {
              context.sender.sendMessage(TL.COMMAND_CLOAKS_ADD_INVALID_FAC.format(context.argAsString(0)));
            return;
        }

        String cloakString = context.argAsString(1);
        CloakType cloakType = CloakType.getTypeFromString(cloakString);

        if (cloakType == null) {
              context.sender.sendMessage(TL.COMMAND_CLOAKS_ADD_INVALID_TYPE.format(context.argAsString(1)));
            return;
        }

        if (target.hasCloak(cloakType)) {
            context.sender.sendMessage(TL.COMMAND_CLOAKS_ADD_ACTIVE.format(context.argAsString(1)));
            return;
        }

        String timeLength = context.argAsString(3);
        TimeUtil timeUtil;

        try {
            timeUtil = TimeUtil.parseString(timeLength);
        } catch (TimeUtil.TimeParseException e) {
            context.sender.sendMessage(TL.COMMAND_CLOAKS_ADD_INVALID_TIME.format(context.argAsString(3)));
            return;
        }

        long millis = timeUtil.toMilliseconds();
        long endTimeStamp = millis + System.currentTimeMillis();

        Cloak cloak = new Cloak(cloakType, endTimeStamp, );
        target.addCloak(cloak);
        context.sender.sendMessage(TL.COMMAND_CLOAKS_ADD_SUCCESSFUL.format(context.argAsString(1), context.argAsString(0),
                timeUtil.toString()));

        Bukkit.broadcastMessage(TL.CLOAKS_STARTED_ANNOUNCE.format(context.argAsString(0), context.argAsString(1), timeUtil.toString()));
    }

    @Override
    public TL getUsageTranslation () {
        return TL.COMMAND_CLOAK_ADD_DESCRIPTION;
    }
}
