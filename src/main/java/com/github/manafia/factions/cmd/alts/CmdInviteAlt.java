package com.github.manafia.factions.cmd.alts;

import com.github.manafia.factions.Conf;
import com.github.manafia.factions.FPlayer;
import com.github.manafia.factions.FactionsPlugin;
import com.github.manafia.factions.cmd.Aliases;
import com.github.manafia.factions.cmd.CommandContext;
import com.github.manafia.factions.cmd.CommandRequirements;
import com.github.manafia.factions.cmd.FCommand;
import com.github.manafia.factions.cmd.audit.FLogType;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.struct.Role;
import com.github.manafia.factions.util.CC;
import com.github.manafia.factions.zcore.fperms.Access;
import com.github.manafia.factions.zcore.fperms.PermissableAction;
import com.github.manafia.factions.zcore.util.TL;
import mkremins.fanciful.FancyMessage;

public class CmdInviteAlt extends FCommand {

    /**
     * @author Driftay
     */

    public CmdInviteAlt() {
        super();
        this.aliases.addAll(Aliases.alts_invite);
        this.requiredArgs.add("player name");

        this.requirements = new CommandRequirements.Builder(Permission.INVITE)
                .playerOnly()
                .memberOnly()
                .build();
    }

    @Override
    public void perform(CommandContext context) {
        if (!FactionsPlugin.getInstance().getConfig().getBoolean("f-alts.Enabled", false)) {
            context.msg(TL.GENERIC_DISABLED, "Faction Alts");
            return;
        }

        FPlayer target = context.argAsBestFPlayerMatch(0);
        if (target == null) {
            return;
        }

        if (target.getFaction() == context.faction) {
            context.msg(TL.COMMAND_INVITE_ALREADYMEMBER, target.getName(), context.faction.getTag());
            return;
        }

        // if economy is enabled, they're not on the bypass list, and this
        // command has a cost set, make 'em pay
        if (!context.payForCommand(Conf.econCostInvite, TL.COMMAND_INVITE_TOINVITE.toString(), TL.COMMAND_INVITE_FORINVITE.toString())) {
            return;
        }

        if (!context.fPlayer.isAdminBypassing()) {
            Access access = context.faction.getAccess(context.fPlayer, PermissableAction.INVITE);
            if (access != Access.ALLOW && context.fPlayer.getRole() != Role.LEADER) {
                context.msg(TL.GENERIC_FPERM_NOPERMISSION, "manage invites");
                return;
            }
        }

        if (context.faction.isBanned(target)) {
            context.msg(TL.COMMAND_INVITE_BANNED, target.getName());
            return;
        }

        context.faction.deinvite(target);
        context.faction.altInvite(target);
        if (!target.isOnline()) {
            return;
        }

        FancyMessage message = new FancyMessage(TL.COMMAND_INVITE_INVITEDYOU.toString()
                .replace("%1$s", context.fPlayer.describeTo(target, true))
                .replace("%2$s", context.faction.getTag())
                .replaceAll("&", "§"))
                .tooltip(TL.COMMAND_INVITE_CLICKTOJOIN.toString())
                .command("/" + Conf.baseCommandAliases.get(0) + " join " + context.faction.getTag());
        message.send(target.getPlayer());
        FactionsPlugin.instance.logFactionEvent(context.faction, FLogType.INVITES, context.fPlayer.getName(), CC.Green + "invited", target.getName());
        context.faction.msg(TL.COMMAND_ALTINVITE_INVITED_ALT, context.fPlayer.describeTo(context.faction, true), target.describeTo(context.faction));
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_ALTINVITE_DESCRIPTION;
    }
}
