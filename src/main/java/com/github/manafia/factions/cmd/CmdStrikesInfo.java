package com.github.manafia.factions.cmd;

import com.github.manafia.factions.Faction;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.zcore.util.TL;

public class CmdStrikesInfo extends FCommand {

    /**
     * @author Driftay
     */

    public CmdStrikesInfo() {
        super();
        this.aliases.addAll(Aliases.strikes_info);
        this.optionalArgs.put("target", "faction");

        this.requirements = new CommandRequirements.Builder(Permission.SETSTRIKES)
                .playerOnly()
                .build();
    }

    @Override
    public void perform(CommandContext context) {
        Faction target = context.argAsFaction(0);
        if (target == null) target = context.faction;
        if (target.isSystemFaction()) {
            context.msg(TL.COMMAND_STRIKES_TARGET_INVALID, context.argAsString(0));
            return;
        }
        context.msg(TL.COMMAND_STRIKES_INFO, target.getTag(), target.getStrikes());
    }


    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_STRIKESINFO_DESCRIPTION;
    }


}
