package com.github.manafia.factions.discord;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.WebhookMessage;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import com.github.manafia.factions.Conf;
import com.github.manafia.factions.Faction;
import com.github.manafia.factions.Factions;
import com.github.manafia.factions.FactionsPlugin;
import mkremins.fanciful.FancyMessage;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.Webhook;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


/**
 * @author Vankka & SaberTeam
 */

public class FactionChatHandler extends ListenerAdapter {

    private FactionsPlugin plugin;

    public FactionChatHandler(FactionsPlugin plugin) {
        this.plugin = plugin;
    }

    public static void sendMessage(FactionsPlugin plugin, Faction faction, UUID uuid, String username, String message) {
        String factionsChatChannelId = faction.getFactionChatChannelId();
        String messageWithMentions = null;
        if (factionsChatChannelId == null || factionsChatChannelId.isEmpty()) return;
        if (Discord.jda == null) return;
        TextChannel textChannel = Discord.jda.getTextChannelById(factionsChatChannelId);
        if (textChannel == null) return;
        if (!textChannel.getGuild().getSelfMember().hasPermission(textChannel, Permission.MANAGE_WEBHOOKS)) {
            textChannel.sendMessage("Missing `Manage Webhooks` permission in this channel").queue();
            return;
        }
        Webhook webhook = (textChannel.retrieveWebhooks().complete()).stream().filter(w -> w.getName().equals(FactionsPlugin.getInstance().getFileManager().getDiscord().fetchString("Discord.Bot.webhookName"))).findAny().orElse(null);
        WebhookClientBuilder builder;

        if (webhook == null) {
            webhook = textChannel.createWebhook(FactionsPlugin.getInstance().getFileManager().getDiscord().fetchString("Discord.Bot.webhookName")).complete();
        }

        builder = new WebhookClientBuilder(webhook.getUrl()); // or id, token
        builder.setThreadFactory((job) -> {
            Thread thread = new Thread(job);
            thread.setName("WebHook Parse Thread");
            thread.setDaemon(true);
            return thread;
        });
        builder.setWait(true);
        WebhookClient client = builder.build();
        if (message.contains("@") && message.contains("#")) {
            List<String> x = new ArrayList<>(Arrays.asList(message.split(" ")));
            for (String y : x) {
                if (y.contains("@") && y.contains("#")) {
                    String[] target = y.replace("@", "").split("#");
                    for (User u : Discord.jda.getUsersByName(target[0], false)) {
                        if (u.getDiscriminator().equals(target[1])) {
                            x.set(x.indexOf(y), u.getAsMention());
                        }
                    }
                } else if (y.contains("@")) {
                    List<Integer> ii = new ArrayList<>();
                    int i = x.indexOf(y);
                    StringBuilder mention = new StringBuilder();
                    while (i <= x.size() - 1) {
                        mention.append(" ").append(x.get(i));
                        ii.add(i);
                        if (mention.toString().contains("#")) {
                            break;
                        }
                        i++;
                    }
                    if (mention.toString().contains("#")) {
                        String[] mentionA = mention.toString().replace(" @", "").split("#");
                        for (User u : Discord.jda.getUsersByName(mentionA[0], false)) {
                            if (u.getDiscriminator().equals(mentionA[1])) {
                                for (Integer l : ii) {
                                    x.set(l, "");
                                }
                                x.set(ii.get(0), u.getAsMention());
                            }
                        }
                    }
                }
            }
            StringBuilder sB = new StringBuilder();
            for (String s : x) {
                sB.append(s);
                sB.append(" ");
            }
            messageWithMentions = sB.toString();
        }
        if (messageWithMentions != null) {
            WebhookMessage webhookMessage = new WebhookMessageBuilder().setUsername(ChatColor.stripColor(username)).setAvatarUrl(FactionsPlugin.getInstance().getFileManager().getDiscord().fetchString("Discord.Bot.avatarUrl").replace("%uuid%", uuid.toString())).setContent(ChatColor.stripColor(messageWithMentions)).build();
            client.send(webhookMessage).join();
            client.close();
            return;
        }
        WebhookMessage webhookMessage = new WebhookMessageBuilder().setUsername(ChatColor.stripColor(username)).setAvatarUrl(FactionsPlugin.getInstance().getFileManager().getDiscord().fetchString("Discord.Bot.avatarUrl").replace("%uuid%", uuid.toString())).setContent(ChatColor.stripColor(message)).build();
        client.send(webhookMessage).join();
        client.close();
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if (event.isWebhookMessage() || event.getAuthor().isBot()) return;
        Faction faction = Factions.getInstance().getAllFactions().stream().filter(f -> event.getChannel().getId().equals(f.getFactionChatChannelId())).findAny().orElse(null);
        if (faction == null) return;

        String content = event.getMessage().getContentDisplay();
        String message = (content.length() > 500) ? content.substring(0, 500) : content;
        FancyMessage fancyMessage = new FancyMessage();
        fancyMessage.text(ChatColor.translateAlternateColorCodes('&', FactionsPlugin.getInstance().getFileManager().getDiscord().fetchString("Discord.Bot.discordToFactionChatPrefix") + String.format(Conf.factionChatFormat, event.getAuthor().getAsTag(), message)));
        List<FancyMessage> messages = new ArrayList<>();
        messages.add(fancyMessage);
        for (Message.Attachment attachment : event.getMessage().getAttachments()) {
            messages.add(new FancyMessage().text(" [Attachment]").color(ChatColor.AQUA).link(attachment.getUrl()).tooltip(attachment.getFileName()));
        }
        plugin.getServer().getScheduler().runTask(plugin, () -> messages.forEach(msg -> faction.getOnlinePlayers().forEach(fancyMessage::send)));
    }
}

