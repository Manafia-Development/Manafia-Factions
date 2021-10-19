package com.massivecraft.factions.cmd.boosters;

import com.massivecraft.factions.Faction;
import com.massivecraft.factions.boosters.Booster;
import com.massivecraft.factions.boosters.BoosterType;
import com.massivecraft.factions.cmd.Aliases;
import com.massivecraft.factions.cmd.CommandContext;
import com.massivecraft.factions.cmd.CommandRequirements;
import com.massivecraft.factions.cmd.FCommand;
import com.massivecraft.factions.struct.Permission;
import com.massivecraft.factions.util.TimeUtil;
import com.massivecraft.factions.zcore.util.TL;
import org.bukkit.Bukkit;

public class CmdBoosterAdd extends FCommand {

    public CmdBoosterAdd() {
        super();
        this.aliases.addAll(Aliases.boostersAdd);

        this.requiredArgs.add("faction");
        this.requiredArgs.add("type");
        this.requiredArgs.add("multiplier");
        this.requiredArgs.add("time");

        this.requirements = new CommandRequirements.Builder(Permission.BOOSTER_ADD).playerOnly().memberOnly().build();
    }

    @Override
    public void perform(CommandContext context) {
        Faction target = context.argAsFaction(0);

        if (target == null) {
            context.sender.sendMessage(TL.COMMAND_BOOSTERS_ADD_INVALID_FAC.format(context.argAsString(0)));
            return;
        }

        String boosterString = context.argAsString(1);
        BoosterType boosterType = BoosterType.getTypeFromString(boosterString);

        if (boosterType == null) {
            context.sender.sendMessage(TL.COMMAND_BOOSTERS_ADD_INVALID_TYPE.format(context.argAsString(1)));
            return;
        }

        if (target.hasBooster(boosterType)) {
            context.sender.sendMessage(TL.COMMAND_BOOSTERS_ADD_ACTIVE.format(context.argAsString(1)));
            return;
        }

        double multiplier = context.argAsDouble(2);

        if (multiplier <= 0) {
            context.sender.sendMessage(TL.COMMAND_BOOSTERS_ADD_INVALID_MULTIPLIER.format(context.argAsString(2)));
            return;
        }

        String timeLength = context.argAsString(3);
        TimeUtil timeUtil;

        try {
            timeUtil = TimeUtil.parseString(timeLength);
        } catch (TimeUtil.TimeParseException e) {
            context.sender.sendMessage(TL.COMMAND_BOOSTERS_ADD_INVALID_TIME.format(context.argAsString(3)));
            return;
        }

        long millis = timeUtil.toMilliseconds();
        long endTimeStamp = millis + System.currentTimeMillis();

        Booster booster = new Booster(boosterType, endTimeStamp, multiplier);
        target.addBooster(booster);
        context.sender.sendMessage(TL.COMMAND_BOOSTERS_ADD_SUCCESSFUL.format(context.argAsString(1), context.argAsString(0), context.argAsString(2),
                timeUtil.toString()));

        Bukkit.broadcastMessage(TL.COMMAND_BOOSTERS_STARTED_ANNOUNCE.format(context.argAsString(0), context.argAsString(1), timeUtil.toString()));
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_BOOSTERS_ADD_DESCRIPTION;
    }
}