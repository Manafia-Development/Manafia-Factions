package com.github.manafia.factions.cmd.grace;

import com.github.manafia.factions.Conf;
import com.github.manafia.factions.FPlayer;
import com.github.manafia.factions.FPlayers;
import com.github.manafia.factions.Util;
import com.github.manafia.factions.cmd.Aliases;
import com.github.manafia.factions.cmd.CommandContext;
import com.github.manafia.factions.cmd.CommandRequirements;
import com.github.manafia.factions.cmd.FCommand;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.util.timer.TimerManager;
import com.github.manafia.factions.zcore.util.TL;

import java.util.concurrent.TimeUnit;

public class CmdGrace extends FCommand {

    /**
     * @author Driftay
     */

    public CmdGrace () {
        super();
        this.aliases.addAll(Aliases.grace);

        this.optionalArgs.put("on/off", "toggle");

        this.requirements = new CommandRequirements.Builder(Permission.GRACE)
                .build();
    }

    @Override
    public void perform (CommandContext context) {
        if (!Conf.useGraceSystem) {
            context.msg(TL.GENERIC_DISABLED, "factions grace");
            return;
        }

        if (context.args.size() == 1)
            if (context.sender.hasPermission(String.valueOf(Permission.GRACETOGGLE)))
                switch (context.argAsString(0)) {
                    case "on":
                    case "start":
                        Util.getTimerManager().graceTimer.setPaused(false);
                        Util.getTimerManager().graceTimer.setRemaining(TimeUnit.DAYS.toMillis(Conf.gracePeriodTimeDays), true);
                        if (Conf.broadcastGraceToggles)
                            for (FPlayer follower : FPlayers.getInstance().getOnlinePlayers())
                                follower.msg(TL.COMMAND_GRACE_ENABLED_FORMAT, String.valueOf(TimerManager.getRemaining(Util.getTimerManager().graceTimer.getRemaining(), true)));
                        return;
                    case "off":
                    case "stop":
                        Util.getTimerManager().graceTimer.setRemaining(TimeUnit.SECONDS.toMillis(0L), true);
                        Util.getTimerManager().graceTimer.setPaused(false);
                        if (Conf.broadcastGraceToggles)
                            for (FPlayer follower : FPlayers.getInstance().getOnlinePlayers())
                                follower.msg(TL.COMMAND_GRACE_DISABLED_FORMAT);
                        return;
                }

        if (Util.getTimerManager().graceTimer.getRemaining() <= 0L)
            context.fPlayer.msg(TL.COMMAND_GRACE_DISABLED_NO_FORMAT.toString());
        else
            context.fPlayer.msg(TL.COMMAND_GRACE_TIME_REMAINING, String.valueOf(TimerManager.getRemaining(Util.getTimerManager().graceTimer.getRemaining(), true)));

    }

    @Override
    public TL getUsageTranslation () {
        return TL.COMMAND_GRACE_DESCRIPTION;
    }

}
