package com.github.manafia.factions.cmd.check;

import com.github.manafia.factions.Conf;
import com.github.manafia.factions.FactionsPlugin;
import com.github.manafia.factions.cmd.Aliases;
import com.github.manafia.factions.cmd.CommandContext;
import com.github.manafia.factions.cmd.CommandRequirements;
import com.github.manafia.factions.cmd.FCommand;
import com.github.manafia.factions.discord.Discord;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.struct.Role;
import com.github.manafia.factions.zcore.fperms.PermissableAction;
import com.github.manafia.factions.zcore.util.TL;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.OfflinePlayer;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class CmdCheck extends FCommand {

    /**
     * @author Driftay
     */

    private SimpleDateFormat simpleDateFormat;

    public CmdCheck() {
        this.simpleDateFormat = new SimpleDateFormat(Conf.dateFormat);
        this.aliases.addAll(Aliases.check);
        this.requiredArgs.add("walls/buffers/settings/leaderboard");

        this.requirements = new CommandRequirements.Builder(Permission.CHECK)
                .playerOnly()
                .withAction(PermissableAction.CHECK)
                .memberOnly()
                .build();
    }


    public void perform(CommandContext context) {
        if (context.faction == null || !context.faction.isNormal()) {
            return;
        }
        String subCommand = context.argAsString(0, null);

        long currentTime = System.currentTimeMillis();
        if (subCommand.equalsIgnoreCase("leaderboard")) {
            context.msg(TL.CHECK_LEADERBOARD_HEADER);
            Map<UUID, Integer> players = new HashMap<>(context.faction.getPlayerWallCheckCount());
            for (Map.Entry<UUID, Integer> entry : context.faction.getPlayerBufferCheckCount().entrySet()) {
                if (players.containsKey(entry.getKey())) {
                    players.replace(entry.getKey(), players.get(entry.getKey()) + entry.getValue());
                } else {
                    players.put(entry.getKey(), entry.getValue());
                }
            }
            List<Map.Entry<UUID, Integer>> entryList = players.entrySet().stream().sorted(Comparator.comparingInt(Map.Entry::getValue)).collect(Collectors.toList());
            for (int max = Math.min(entryList.size(), 10), current = 0; current < max; ++current) {
                Map.Entry<UUID, Integer> entry = entryList.get(current);
                OfflinePlayer offlinePlayer = FactionsPlugin.getInstance().getServer().getOfflinePlayer(entry.getKey());
                context.msg(TL.CHECK_LEADERBOARD_LINE.format(current + 1, offlinePlayer.getName(), entry.getValue(), context.faction.getPlayerBufferCheckCount().getOrDefault(entry.getKey(), 0), context.faction.getPlayerWallCheckCount().getOrDefault(entry.getKey(), 0)));
            }
            if (entryList.isEmpty()) {
                context.msg(TL.CHECK_LEADERBOARD_NO_DATA);
            }
        } else if (subCommand.equalsIgnoreCase("walls")) {
            if (!CheckTask.wallCheck(context.faction.getId())) {
                if (context.faction.getChecks().isEmpty()) {
                    context.msg(TL.CHECK_NO_CHECKS);
                    return;
                }
                context.msg(TL.CHECK_ALREADY_CHECKED);
            } else {
                int current = context.faction.getPlayerWallCheckCount().getOrDefault(context.player.getUniqueId(), 0);
                if (current == 0) {
                    context.faction.getPlayerWallCheckCount().put(context.player.getUniqueId(), 1);
                } else {
                    context.faction.getPlayerWallCheckCount().replace(context.player.getUniqueId(), current + 1);
                }
                context.faction.getChecks().put(currentTime, "U" + context.fPlayer.getNameAndTag());
                context.msg(TL.CHECK_WALLS_MARKED_CHECKED);
                if (!FactionsPlugin.getInstance().getFileManager().getDiscord().fetchBoolean("Discord.useDiscordSystem"))
                    return;
                String channelId = context.faction.getWallNotifyChannelId();
                if (channelId == null || channelId.isEmpty()) {
                    return;
                }
                TextChannel textChannel = Discord.jda.getTextChannelById(channelId);
                if (textChannel == null) {
                    return;
                }
                if (!textChannel.getGuild().getSelfMember().hasPermission(textChannel, net.dv8tion.jda.api.Permission.MESSAGE_READ, net.dv8tion.jda.api.Permission.MESSAGE_WRITE)) {
                    textChannel.getGuild().retrieveOwner().complete().getUser().openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage((":x: Missing read/write in " + textChannel.getAsMention())).queue());
                    return;
                }
                MessageEmbed embed = new EmbedBuilder().setColor(Color.MAGENTA).setTitle("Walls checked by " + context.fPlayer.getNameAndTag()).setFooter(simpleDateFormat.format(new Date(currentTime)), null).build();
                textChannel.sendMessage(embed).queue();
            }
        } else if (subCommand.equalsIgnoreCase("buffers")) {
            if (!CheckTask.bufferCheck(context.faction.getId())) {
                if (context.faction.getChecks().isEmpty()) {
                    context.msg(TL.CHECK_NO_CHECKS);
                    return;
                }
                context.msg(TL.CHECK_ALREADY_CHECKED);
            } else {
                int current = context.faction.getPlayerBufferCheckCount().getOrDefault(context.player.getUniqueId(), 0);
                if (current == 0) {
                    context.faction.getPlayerBufferCheckCount().put(context.player.getUniqueId(), 1);
                } else {
                    context.faction.getPlayerBufferCheckCount().replace(context.player.getUniqueId(), current + 1);
                }
                context.faction.getChecks().put(System.currentTimeMillis(), "Y" + context.fPlayer.getNameAndTag());
                context.msg(TL.CHECK_BUFFERS_MARKED_CHECKED);
                if (!FactionsPlugin.getInstance().getFileManager().getDiscord().fetchBoolean("Discord.useDiscordSystem"))
                    return;
                String channelId = context.faction.getBufferNotifyChannelId();
                if (channelId == null || channelId.isEmpty()) {
                    return;
                }
                TextChannel textChannel = Discord.jda.getTextChannelById(channelId);
                if (textChannel == null) {
                    return;
                }
                if (!textChannel.getGuild().getSelfMember().hasPermission(textChannel, net.dv8tion.jda.api.Permission.MESSAGE_READ, net.dv8tion.jda.api.Permission.MESSAGE_WRITE)) {
                    textChannel.getGuild().retrieveOwner().complete().getUser().openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage((":x: Missing read/write in " + textChannel.getAsMention())).queue());
                    return;
                }
                MessageEmbed embed = new EmbedBuilder().setColor(Color.MAGENTA).setTitle("Buffers checked by " + context.fPlayer.getNameAndTag()).setFooter(simpleDateFormat.format(new Date(currentTime)), null).build();
                textChannel.sendMessage(embed).queue();
            }
        } else if (subCommand.equalsIgnoreCase("settings")) {
            if (!context.fPlayer.getRole().isAtLeast(Role.COLEADER)) {
                context.msg(TL.CHECK_MUST_BE_ATLEAST_COLEADER);
                return;
            }
            CheckSettingsFrame checkGUI = new CheckSettingsFrame(FactionsPlugin.getInstance(), context.fPlayer);
            checkGUI.build();
            context.fPlayer.getPlayer().openInventory(checkGUI.getInventory());
        }
    }

    public TL getUsageTranslation() {
        return TL.COMMAND_CHECK_DESCRIPTION;
    }
}
