package com.github.manafia.factions.cmd;

import com.github.manafia.factions.*;
import com.github.manafia.factions.cmd.reserve.ReserveObject;
import com.github.manafia.factions.discord.Discord;
import com.github.manafia.factions.event.FPlayerJoinEvent;
import com.github.manafia.factions.event.FactionCreateEvent;
import com.github.manafia.factions.integration.Econ;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.struct.Role;
import com.github.manafia.factions.util.Cooldown;
import com.github.manafia.factions.util.Logger;
import com.github.manafia.factions.util.MiscUtil;
import com.github.manafia.factions.zcore.util.TL;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.exceptions.HierarchyException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.ArrayList;


public class CmdCreate extends FCommand {

    /**
     * @author FactionsUUID Team - Modified By CmdrKittens
     */

    public CmdCreate() {
        super();
        this.aliases.addAll(Aliases.create);

        this.requiredArgs.add("faction tag");

        this.requirements = new CommandRequirements.Builder(Permission.CREATE)
                .playerOnly()
                .build();
    }

    @Override
    public void perform(CommandContext context) {
        if (FactionsPlugin.getInstance().getFileManager().getDiscord().fetchBoolean("Discord.Guild.restrictActionsWhenNotLinked") && !context.fPlayer.discordSetup()) {
            context.player.sendMessage(ChatColor.translateAlternateColorCodes('&', TL.DISCORD_LINK_REQUIRED.toString()));
            return;
        }

        String tag = context.argAsString(0);

        if (context.fPlayer.hasFaction()) {
            context.msg(TL.COMMAND_CREATE_MUSTLEAVE);
            return;
        }

        if (Factions.getInstance().isTagTaken(tag)) {
            context.msg(TL.COMMAND_CREATE_INUSE);
            return;
        }

        if (Cooldown.isOnCooldown(context.fPlayer.getPlayer(), "createCooldown") && !context.fPlayer.isAdminBypassing()) {
            context.msg(TL.COMMAND_COOLDOWN);
            return;
        }

        ReserveObject factionReserve = FactionsPlugin.getInstance().getFactionReserves().stream().filter(factionReserve1 -> factionReserve1.getFactionName().equalsIgnoreCase(tag)).findFirst().orElse(null);
        if (factionReserve != null && !factionReserve.getName().equalsIgnoreCase(context.player.getName())) {
            context.msg(TL.COMMAND_CREATE_ALREADY_RESERVED);
            return;
        }

        ArrayList<String> tagValidationErrors = MiscUtil.validateTag(tag);
        if (tagValidationErrors.size() > 0) {
            context.sendMessage(tagValidationErrors);
            return;
        }

        // if economy is enabled, they're not on the bypass list, and this command has a cost set, make sure they can pay
        if (!context.canAffordCommand(Conf.econCostCreate, TL.COMMAND_CREATE_TOCREATE.toString())) {
            return;
        }

        // trigger the faction creation event (cancellable)
        FactionCreateEvent createEvent = new FactionCreateEvent(context.player, tag);
        Bukkit.getServer().getPluginManager().callEvent(createEvent);
        if (createEvent.isCancelled()) {
            return;
        }

        // then make 'em pay (if applicable)
        if (!context.payForCommand(Conf.econCostCreate, TL.COMMAND_CREATE_TOCREATE, TL.COMMAND_CREATE_FORCREATE)) {
            return;
        }

        Faction faction = Factions.getInstance().createFaction();

        // TODO: Why would this even happen??? Auto increment clash??
        if (faction == null) {
            context.msg(TL.COMMAND_CREATE_ERROR);
            return;
        }

        // finish setting up the Faction
        faction.setTag(tag);
        if (factionReserve != null) {
            FactionsPlugin.getInstance().getFactionReserves().remove(factionReserve);
        }
        // trigger the faction join event for the creator
        FPlayerJoinEvent joinEvent = new FPlayerJoinEvent(FPlayers.getInstance().getByPlayer(context.player), faction, FPlayerJoinEvent.PlayerJoinReason.CREATE);
        Bukkit.getServer().getPluginManager().callEvent(joinEvent);
        // join event cannot be cancelled or you'll have an empty faction
        // finish setting up the FPlayer
        context.fPlayer.setFaction(faction, false);
        // We should consider adding the role just AFTER joining the faction.
        // That way we don't have to mess up deleting more stuff.
        // And prevent the user from being returned to NORMAL after deleting his old faction.
        context.fPlayer.setRole(Role.LEADER);

        Cooldown.setCooldown(context.fPlayer.getPlayer(), "createCooldown", FactionsPlugin.getInstance().getConfig().getInt("fcooldowns.f-create"));
        if (FactionsPlugin.getInstance().getConfig().getBoolean("faction-creation-broadcast", true)) {
            for (FPlayer follower : FPlayers.getInstance().getOnlinePlayers()) {
                follower.msg(TL.COMMAND_CREATE_CREATED, context.fPlayer.getName(), faction.getTag(follower));
            }
        }
        //Discord
        try {
            if (Discord.useDiscord && context.fPlayer.discordSetup() && Discord.isInMainGuild(context.fPlayer.discordUser()) && Discord.mainGuild != null) {
                Member m = Discord.mainGuild.retrieveMember(context.fPlayer.discordUser()).complete();
                if (FactionsPlugin.getInstance().getFileManager().getDiscord().fetchBoolean("Discord.Guild.factionRoles")) {
                    Discord.mainGuild.addRoleToMember(m, Discord.createFactionRole(faction.getTag())).queue();
                }
                if (FactionsPlugin.getInstance().getFileManager().getDiscord().fetchBoolean("Discord.Guild.leaderRoles") && Discord.leader != null) {
                    Discord.mainGuild.addRoleToMember(m, Discord.leader).queue();
                }
                if (FactionsPlugin.getInstance().getFileManager().getDiscord().fetchBoolean("Discord.Guild.factionDiscordTags")) {
                    Discord.mainGuild.modifyNickname(m, Discord.getNicknameString(context.fPlayer)).queue();
                }
            }
        } catch (HierarchyException e) {
            Logger.print(e.getMessage(), Logger.PrefixType.FAILED);
        }
        //End Discord
        context.msg(TL.COMMAND_CREATE_YOUSHOULD, FactionsPlugin.getInstance().cmdBase.cmdDescription.getUsageTemplate(context));
        if (Conf.econEnabled) Econ.setBalance(faction.getAccountId(), Conf.econFactionStartingBalance);
        if (Conf.logFactionCreate)
            Logger.print(context.fPlayer.getName() + TL.COMMAND_CREATE_CREATEDLOG + tag, Logger.PrefixType.DEFAULT);
        if (FactionsPlugin.getInstance().getConfig().getBoolean("fpaypal.Enabled"))
            context.msg(TL.COMMAND_PAYPALSET_CREATED);
        if(Conf.allFactionsPeaceful) {
            faction.setPeaceful(true);
            faction.setPeacefulExplosionsEnabled(false);
        }
        if (Conf.useCustomDefaultPermissions) faction.setDefaultPerms();
        if (Conf.usePermissionHints) context.msg(TL.COMMAND_HINT_PERMISSION);
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_CREATE_DESCRIPTION;
    }

}