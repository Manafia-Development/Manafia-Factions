package com.github.manafia.factions.cmd.alts;

import com.github.manafia.factions.Conf;
import com.github.manafia.factions.FPlayer;
import com.github.manafia.factions.Faction;
import com.github.manafia.factions.FactionsPlugin;
import com.github.manafia.factions.cmd.Aliases;
import com.github.manafia.factions.cmd.CommandContext;
import com.github.manafia.factions.cmd.CommandRequirements;
import com.github.manafia.factions.cmd.FCommand;
import com.github.manafia.factions.cmd.audit.FLogType;
import com.github.manafia.factions.event.FPlayerLeaveEvent;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.struct.Role;
import com.github.manafia.factions.util.CC;
import com.github.manafia.factions.util.Logger;
import com.github.manafia.factions.zcore.fperms.Access;
import com.github.manafia.factions.zcore.fperms.PermissableAction;
import com.github.manafia.factions.zcore.util.TL;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

public class CmdKickAlt extends FCommand {

    /**
     * @author Driftay
     */

    public CmdKickAlt() {
        super();
        this.aliases.addAll(Aliases.alts_kick);
        this.requiredArgs.add("player name");

        this.requirements = new CommandRequirements.Builder(Permission.KICK)
                .playerOnly()
                .memberOnly()
                .withAction(PermissableAction.KICK)
                .build();
    }

    @Override
    public void perform(CommandContext context) {
        if (!FactionsPlugin.getInstance().getConfig().getBoolean("f-alts.Enabled", false)) {
            context.msg(TL.GENERIC_DISABLED, "Faction Alts");
            return;
        }

        FPlayer toKick = context.argIsSet(0) ? context.argAsBestFPlayerMatch(0) : null;

        if (toKick == null) {
            context.msg(TL.COMMAND_ALTKICK_NOTMEMBER);
            return;
        }

        if (context.fPlayer == toKick) {
            context.msg(TL.COMMAND_KICK_SELF);
            context.msg(TL.GENERIC_YOUMAYWANT + FactionsPlugin.instance.cmdBase.cmdLeave.getUsageTemplate(context));
            return;
        }

        Faction toKickFaction = toKick.getFaction();

        if (toKickFaction.isWilderness()) {
            context.sender.sendMessage(TL.COMMAND_KICK_NONE.toString());
            return;
        }

        // players with admin-level "disband" permission can bypass these
        // requirements
        if (!Permission.KICK_ANY.has(context.sender)) {
            if (toKickFaction != context.faction) {
                context.msg(TL.COMMAND_KICK_NOTMEMBER, toKick.describeTo(context.fPlayer, true), context.faction.describeTo(context.fPlayer));
                return;
            }

            if (!toKick.isAlt()) {
                context.msg(TL.COMMAND_ALTKICK_NOTALT);
                return;
            }

            if (!Conf.canLeaveWithNegativePower && toKick.getPower() < 0) {
                context.msg(TL.COMMAND_KICK_NEGATIVEPOWER);
                return;
            }
        }

        Access access = context.faction.getAccess(context.fPlayer, PermissableAction.KICK);
        // This statement allows us to check if they've specifically denied it,
        // or default to
        // the old setting of allowing moderators to kick
        if (access != Access.ALLOW && !context.assertMinRole(Role.MODERATOR)) {
            context.msg(TL.GENERIC_NOPERMISSION, "kick");
            return;
        }


        // if economy is enabled, they're not on the bypass list, and this
        // command has a cost set, make sure they can pay
        if (!context.canAffordCommand(Conf.econCostKick, TL.COMMAND_KICK_TOKICK.toString())) {
            return;
        }

        // trigger the leave event (cancellable) [reason:kicked]
        FPlayerLeaveEvent event = new FPlayerLeaveEvent(toKick, toKick.getFaction(), FPlayerLeaveEvent.PlayerLeaveReason.KICKED);
        Bukkit.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return;
        }


        // then make 'em pay (if applicable)
        if (!context.payForCommand(Conf.econCostKick, TL.COMMAND_KICK_TOKICK.toString(), TL.COMMAND_KICK_FORKICK.toString())) {
            return;
        }


        toKickFaction.msg(TL.COMMAND_KICK_FACTION, context.fPlayer.describeTo(toKickFaction, true), toKick.describeTo(toKickFaction, true));

        toKick.msg(TL.COMMAND_KICK_KICKED, context.fPlayer.describeTo(toKick, true), toKickFaction.describeTo(toKick));

        if (toKickFaction != context.faction) {
            context.msg(TL.COMMAND_KICK_KICKS, toKick.describeTo(context.fPlayer), toKickFaction.describeTo(context.fPlayer));
        }

        if (Conf.logFactionKick) {
            Logger.print((context.sender instanceof ConsoleCommandSender ? "A console command" : context.fPlayer.getName()) + " kicked " + toKick.getName() + " from the faction: " + toKickFaction.getTag(), Logger.PrefixType.DEFAULT);
        }
        // SHOULD NOT BE POSSIBLE BUT KEPT INCASE
        if (toKick.getRole() == Role.LEADER) {
            toKickFaction.promoteNewLeader();
        }

        FactionsPlugin.instance.logFactionEvent(toKickFaction, FLogType.INVITES, context.fPlayer.getName(), CC.Red + "kicked alt", toKick.getName());
        toKickFaction.removeAltPlayer(toKick);
        toKickFaction.deinvite(toKick);
        toKick.resetFactionData();
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_ALTKICK_DESCRIPTION;
    }

}