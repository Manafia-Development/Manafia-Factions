package com.github.manafia.factions.cmd;

import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.zcore.util.TL;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CmdFGlobal extends FCommand {

    /**
     * @author Trent
     */

    public static List<UUID> toggled = new ArrayList<>();

    public CmdFGlobal() {
        super();
        this.aliases.addAll(Aliases.global);

        this.requirements = new CommandRequirements.Builder(Permission.GLOBALCHAT)
                .playerOnly()
                .memberOnly()
                .build();
    }

    @Override
    public void perform(CommandContext context) {
        // /f global

        if (toggled.contains(context.player.getUniqueId()))
            toggled.remove(context.player.getUniqueId());
        else
            toggled.add(context.player.getUniqueId());

        context.msg(TL.COMMAND_F_GLOBAL_TOGGLE, toggled.contains(context.player.getUniqueId()) ? "disabled" : "enabled");
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_F_GLOBAL_DESCRIPTION;
    }

}
