package com.github.manafia.factions.cmd;

import com.github.manafia.factions.Conf;
import com.github.manafia.factions.FPlayer;
import com.github.manafia.factions.FPlayers;
import com.github.manafia.factions.FactionsPlugin;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.struct.Role;
import com.github.manafia.factions.util.Cooldown;
import com.github.manafia.factions.zcore.util.TL;

public class CmdOpen extends FCommand {

    /**
     * @author FactionsUUID Team - Modified By CmdrKittens
     */

    public CmdOpen() {
        super();
        this.aliases.addAll(Aliases.open);
        this.optionalArgs.put("yes/no", "flip");

        this.requirements = new CommandRequirements.Builder(Permission.OPEN)
                .withRole(Role.COLEADER)
                .playerOnly()
                .memberOnly()
                .build();
    }

    @Override
    public void perform(CommandContext context) {
        // if economy is enabled, they're not on the bypass list, and this command has a cost set, make 'em pay
        if (!context.payForCommand(Conf.econCostOpen, TL.COMMAND_OPEN_TOOPEN, TL.COMMAND_OPEN_FOROPEN)) {
            return;
        }

        if (Cooldown.isOnCooldown(context.fPlayer.getPlayer(), "openCooldown") && !context.fPlayer.isAdminBypassing()) {
            context.msg(TL.COMMAND_COOLDOWN);
            return;
        }

        context.faction.setOpen(!context.faction.getOpen());

        String open = context.faction.getOpen() ? TL.COMMAND_OPEN_OPEN.toString() : TL.COMMAND_OPEN_CLOSED.toString();

        // Inform
        for (FPlayer fplayer : FPlayers.getInstance().getOnlinePlayers()) {
            if (fplayer.getFactionId().equals(context.faction.getId())) {
                fplayer.msg(TL.COMMAND_OPEN_CHANGES, context.fPlayer.getName(), open);
                Cooldown.setCooldown(fplayer.getPlayer(), "openCooldown", FactionsPlugin.getInstance().getConfig().getInt("fcooldowns.f-open"));
                continue;
            }
            if (!FactionsPlugin.getInstance().getConfig().getBoolean("faction-open-broadcast")) return;
            fplayer.msg(TL.COMMAND_OPEN_CHANGED, context.faction.getTag(fplayer.getFaction()), open);
        }
        if (!FactionsPlugin.getInstance().getConfig().getBoolean("faction-open-broadcast")) {
            for (FPlayer fPlayer : context.faction.getFPlayersWhereOnline(true)) {
                fPlayer.msg(TL.COMMAND_OPEN_CHANGED, context.faction.getTag(fPlayer.getFaction()), open);
            }
        }

    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_OPEN_DESCRIPTION;
    }

}
