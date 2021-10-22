package com.github.manafia.factions.cmd;

import com.github.manafia.factions.Conf;
import com.github.manafia.factions.FPlayer;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.zcore.util.TL;

public class CmdLowPower extends FCommand {

    /**
     * @author Illyria Team
     */

    public CmdLowPower() {
        super();
        this.aliases.addAll(Aliases.lowPower);

        this.requirements = new CommandRequirements.Builder(Permission.POWER_ANY)
                .playerOnly()
                .memberOnly()
                .build();
    }


    @Override
    public void perform(CommandContext context) {
        double maxPower = Conf.powerPlayerMax;
        String format = TL.COMMAND_LOWPOWER_FORMAT.toString();
        context.msg(TL.COMMAND_LOWPOWER_HEADER.toString().replace("{maxpower}", (int) maxPower + ""));
        for (FPlayer fPlayer : context.faction.getFPlayers())
            if (fPlayer.getPower() < maxPower)
                context.sendMessage(format.replace("{player}", fPlayer.getName()).replace("{player_power}", (int) fPlayer.getPower() + "").replace("{maxpower}", (int) maxPower + ""));
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_LOWPOWER_DESCRIPTION;
    }


}