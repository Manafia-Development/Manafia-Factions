package com.github.manafia.factions.cmd.check;

import com.github.manafia.factions.Faction;
import com.github.manafia.factions.Factions;
import com.github.manafia.factions.FactionsPlugin;
import com.github.manafia.factions.discord.Discord;
import com.github.manafia.factions.zcore.util.TL;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;

public class WeeWooTask implements Runnable {

    /**
     * @author Driftay
     */

    private FactionsPlugin plugin;

    public WeeWooTask(FactionsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (Faction faction : Factions.getInstance().getAllFactions()) {
            if (!faction.isWeeWoo()) {
                continue;
            }
            faction.msg(TL.WEE_WOO_MESSAGE);

            if (!FactionsPlugin.getInstance().getFileManager().getDiscord().fetchBoolean("Discord.useDiscordSystem"))
                return;

            String discordChannelId = faction.getWeeWooChannelId();
            if (discordChannelId == null || discordChannelId.isEmpty()) {
                continue;
            }
            TextChannel textChannel = Discord.jda.getTextChannelById(discordChannelId);
            if (textChannel == null) {
                continue;
            }
            if (!textChannel.getGuild().getSelfMember().hasPermission(textChannel, Permission.MESSAGE_READ, Permission.MESSAGE_WRITE)) {
                textChannel.getGuild().retrieveOwner().complete().getUser().openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage((":x: Missing read/write in " + textChannel.getAsMention())).queue());
            } else {
                String format = faction.getWeeWooFormat();
                if (format == null || format.isEmpty()) {
                    format = "@everyone, we're being raided! Get online!";
                }
                textChannel.sendMessage(format).queue();
            }
        }
    }
}
