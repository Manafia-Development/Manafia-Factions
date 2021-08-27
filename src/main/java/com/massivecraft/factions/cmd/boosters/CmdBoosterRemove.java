package com.massivecraft.factions.cmd.boosters;

import com.massivecraft.factions.Faction;
import com.massivecraft.factions.boosters.BoosterType;
import com.massivecraft.factions.cmd.Aliases;
import com.massivecraft.factions.cmd.CommandContext;
import com.massivecraft.factions.cmd.CommandRequirements;
import com.massivecraft.factions.cmd.FCommand;
import com.massivecraft.factions.struct.Permission;
import com.massivecraft.factions.zcore.util.TL;

public class CmdBoosterRemove extends FCommand {

    public CmdBoosterRemove () {
        super();
        this.aliases.addAll(Aliases.boostersRemove);

        this.requiredArgs.add("faction");
        this.requiredArgs.add("type");

        this.requirements = new CommandRequirements.Builder(Permission.BOOSTER_REMOVE).playerOnly().memberOnly().build();
    }

    @Override
    public void perform (CommandContext context) {
        Faction target = context.argAsFaction(0);

        if (target == null) {
            context.sender.sendMessage(TL.COMMAND_BOOSTERS_REMOVE_INVALID_FAC.format(context.argAsString(0)));
            return;
        }

        String boosterString = context.argAsString(1);
        BoosterType boosterType = BoosterType.getTypeFromString(boosterString);

        if (boosterType == null) {
            context.sender.sendMessage(TL.COMMAND_BOOSTERS_REMOVE_INVALID_TYPE.format(context.argAsString(1)));
            return;
        }

        if (!target.hasBooster(boosterType)) {
            context.sender.sendMessage(TL.COMMAND_BOOSTERS_REMOVE_INVALID_NOT_ACTIVE.format(context.argAsString(1)));
            return;
        }

        target.removeBooster(boosterType);
        context.sender.sendMessage(TL.COMMAND_BOOSTERS_REMOVE_SUCCESSFUL.format(context.argAsString(1), context.argAsString(0)));
    }

    @Override
    public TL getUsageTranslation () {
        return TL.COMMAND_BOOSTERS_REMOVE_DESCRIPTION;
    }
}