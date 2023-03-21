package com.github.manafia.factions.cmd.reserve;

import com.github.manafia.factions.FactionsPlugin;
import com.github.manafia.factions.cmd.Aliases;
import com.github.manafia.factions.cmd.CommandContext;
import com.github.manafia.factions.cmd.CommandRequirements;
import com.github.manafia.factions.cmd.FCommand;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.zcore.util.TL;

/**
 * @author Saser
 */

public class CmdReserve extends FCommand {

    public CmdReserve() {
        this.aliases.addAll(Aliases.reserve);
        this.requiredArgs.add("tag");
        this.requiredArgs.add("player");
        this.requirements = new CommandRequirements.Builder(
                Permission.RESERVE).build();
    }

    @Override
    public void perform(CommandContext context) {
        ReserveObject reserve = FactionsPlugin.getInstance().getFactionReserves().stream().filter(faction -> faction.getFactionName().equalsIgnoreCase(context.argAsString(0))).findFirst().orElse(null);
        if (reserve != null) {
            context.msg(TL.COMMAND_RESERVE_ALREADYRESERVED, context.argAsString(0));
            return;
        }
        context.msg(TL.COMMAND_RESERVE_SUCCESS, context.argAsString(0), context.argAsString(1));
        FactionsPlugin.getInstance().getFactionReserves().add(new ReserveObject(context.argAsString(1), context.argAsString(0)));
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_RESERVE_DESCRIPTION;
    }
}
