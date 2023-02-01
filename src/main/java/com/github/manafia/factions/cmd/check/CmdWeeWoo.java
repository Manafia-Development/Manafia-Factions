package com.github.manafia.factions.cmd.check;

import com.github.manafia.factions.FactionsPlugin;
import com.github.manafia.factions.cmd.Aliases;
import com.github.manafia.factions.cmd.CommandContext;
import com.github.manafia.factions.cmd.CommandRequirements;
import com.github.manafia.factions.cmd.FCommand;
import com.github.manafia.factions.discord.Discord;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.zcore.util.TL;
import net.dv8tion.jda.api.entities.TextChannel;

public class CmdWeeWoo extends FCommand {

    /**
     * @author Vankka
     */

    public CmdWeeWoo() {
        this.aliases.addAll(Aliases.weewoo);
        this.requiredArgs.add("start/stop");

        this.requirements = new CommandRequirements.Builder(Permission.CHECK)
                .playerOnly()
                .memberOnly()
                .build();
    }

    public void perform(CommandContext context) {
        if (context.faction == null || !context.faction.isNormal()) {
            return;
        }
        String argument = context.argAsString(0);
        boolean weewoo = context.faction.isWeeWoo();
        if (argument.equalsIgnoreCase("start")) {
            if (weewoo) {
                context.msg(TL.COMMAND_WEEWOO_ALREADY_STARTED);
                return;
            }
            context.faction.setWeeWoo(true);
            context.msg(TL.COMMAND_WEEWOO_STARTED, context.fPlayer.getNameAndTag());
            if (!FactionsPlugin.getInstance().getFileManager().getDiscord().fetchBoolean("Discord.useDiscordSystem"))
                return;
            String discordChannelId = context.faction.getWeeWooChannelId();
            if (discordChannelId != null && !discordChannelId.isEmpty()) {
                TextChannel textChannel = Discord.jda.getTextChannelById(discordChannelId);
                if (textChannel == null) {
                    return;
                }
                if (!textChannel.getGuild().getSelfMember().hasPermission(textChannel, net.dv8tion.jda.api.Permission.MESSAGE_READ, net.dv8tion.jda.api.Permission.MESSAGE_WRITE)) {
                    textChannel.getGuild().retrieveOwner().complete().getUser().openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage((":x: Missing read/write in " + textChannel.getAsMention())).queue());
                    return;
                }
                textChannel.sendMessage(TL.WEEWOO_STARTED_DISCORD.format(context.fPlayer.getNameAndTag())).queue();
            }
        } else if (argument.equalsIgnoreCase("stop")) {
            if (!weewoo) {
                context.msg(TL.COMMAND_WEEWOO_ALREADY_STOPPED);
                return;
            }
            context.faction.setWeeWoo(false);
            context.msg(TL.COMMAND_WEEWOO_STOPPED, context.fPlayer.getNameAndTag());
            if (!FactionsPlugin.getInstance().getFileManager().getDiscord().fetchBoolean("Discord.useDiscordSystem"))
                return;
            String discordChannelId = context.faction.getWeeWooChannelId();
            if (discordChannelId != null && !discordChannelId.isEmpty()) {
                TextChannel textChannel = Discord.jda.getTextChannelById(discordChannelId);
                if (textChannel == null) {
                    return;
                }
                if (!textChannel.getGuild().getSelfMember().hasPermission(textChannel, net.dv8tion.jda.api.Permission.MESSAGE_READ, net.dv8tion.jda.api.Permission.MESSAGE_WRITE)) {
                    textChannel.getGuild().retrieveOwner().complete().getUser().openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage((":x: Missing read/write in " + textChannel.getAsMention())).queue());
                    return;
                }
                textChannel.sendMessage(TL.WEEWOO_STOPPED_DISCORD.format(context.fPlayer.getNameAndTag())).queue();
            }
        } else {
            context.msg("/f weewoo <start/stop>");
        }
    }

    public TL getUsageTranslation() {
        return TL.COMMAND_WEEWOO_DESCRIPTION;
    }
}
