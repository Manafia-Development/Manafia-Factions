package com.github.manafia.factions.cmd;

import com.github.manafia.factions.FPlayer;
import com.github.manafia.factions.FactionsPlugin;
import com.github.manafia.factions.cmd.audit.FLogType;
import com.github.manafia.factions.event.FPlayerLeaveEvent;
import com.github.manafia.factions.struct.BanInfo;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.util.CC;
import com.github.manafia.factions.util.Logger;
import com.github.manafia.factions.zcore.fperms.PermissableAction;
import com.github.manafia.factions.zcore.util.TL;
import org.bukkit.Bukkit;

public class CmdBan extends FCommand {

    /**
     * @author FactionsUUID Team - Modified By CmdrKittens
     */

    public CmdBan() {
        super();
        this.aliases.addAll(Aliases.ban_ban);

        this.requiredArgs.add("target");

        this.requirements = new CommandRequirements.Builder(Permission.BAN)
                .playerOnly()
                .withAction(PermissableAction.BAN)
                .build();
    }

    @Override
    public void perform(CommandContext context) {

        // Good on permission checks. Now lets just ban the player.
        FPlayer target = context.argAsFPlayer(0);
        if (target == null) {
            return; // the above method sends a message if fails to find someone.
        }

        if (context.fPlayer == target) {
            // You may not ban yourself
            context.msg(TL.COMMAND_BAN_SELF);
            return;
        } else if (target.getFaction() == context.faction && target.getRole().value >= context.fPlayer.getRole().value) {
            // You may not ban someone that has same or higher faction rank
            context.msg(TL.COMMAND_BAN_INSUFFICIENTRANK, target.getName());
            return;
        }

        for (BanInfo banInfo : context.faction.getBannedPlayers()) {
            if (banInfo.getBanned().equals(target.getId())) {
                context.msg(TL.COMMAND_BAN_ALREADYBANNED);
                return;
            }
        }


        // Ban the user.
        context.faction.ban(target, context.fPlayer);
        context.faction.deinvite(target); // can't hurt

        // If in same Faction, lets make sure to kick them and throw an event.
        if (target.getFaction() == context.faction) {

            FPlayerLeaveEvent event = new FPlayerLeaveEvent(target, context.faction, FPlayerLeaveEvent.PlayerLeaveReason.BANNED);
            Bukkit.getServer().getPluginManager().callEvent(event);

            if (event.isCancelled()) {
                // if someone cancels a ban, we'll get people complaining here. So lets log it.
                Logger.printArgs( "Attempted to ban {0} but someone cancelled the kick event. This isn't good.", Logger.PrefixType.WARNING, target.getName());
                return;
            }

            // Didn't get cancelled so remove them and reset their invite.
            context.faction.removeFPlayer(target);
            target.resetFactionData();
        }

        // Lets inform the people!
        target.msg(TL.COMMAND_BAN_TARGET, context.faction.getTag(target.getFaction()));
        FactionsPlugin.instance.logFactionEvent(context.faction, FLogType.BANS, context.fPlayer.getName(), CC.Green + "banned", target.getName());
        context.faction.msg(TL.COMMAND_BAN_BANNED, context.fPlayer.getName(), target.getName());
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_BAN_DESCRIPTION;
    }
}
