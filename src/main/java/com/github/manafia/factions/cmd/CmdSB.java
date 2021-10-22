package com.github.manafia.factions.cmd;

import com.github.manafia.factions.scoreboards.FScoreboard;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.zcore.util.TL;

public class CmdSB extends FCommand {

    /**
     * @author FactionsUUID Team - Modified By CmdrKittens
     */

    public CmdSB () {
        this.aliases.addAll(Aliases.scoreboard);

        this.requirements = new CommandRequirements.Builder(Permission.SCOREBOARD)
                .playerOnly()
                .build();
    }

    @Override
    public void perform (CommandContext context) {
        boolean toggleTo = !context.fPlayer.showScoreboard();
        FScoreboard board = FScoreboard.get(context.fPlayer);
        if (board == null)
            context.msg(TL.COMMAND_TOGGLESB_DISABLED.toString());
        else {
            context.msg(TL.TOGGLE_SB.toString().replace("{value}", String.valueOf(toggleTo)));
            board.setSidebarVisibility(toggleTo);
        }
        context.fPlayer.setShowScoreboard(toggleTo);
    }

    @Override
    public TL getUsageTranslation () {
        return TL.COMMAND_SCOREBOARD_DESCRIPTION;
    }
}