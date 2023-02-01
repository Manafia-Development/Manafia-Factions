package com.github.manafia.factions.discord;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.github.manafia.factions.Faction;
import com.github.manafia.factions.Factions;
import com.github.manafia.factions.cmd.CommandContext;
import com.github.manafia.factions.cmd.CommandRequirements;
import com.github.manafia.factions.cmd.FCommand;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.zcore.util.TL;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import org.bukkit.ChatColor;

import java.util.concurrent.TimeUnit;

public class CmdSetGuild extends FCommand {

    /**
     * @author Vankka
     */

    private EventWaiter eventWaiter;
    private boolean waiterAdded;

    public CmdSetGuild() {
        super();
        this.eventWaiter = new EventWaiter();
        this.waiterAdded = false;
        this.aliases.add("setguild");
        this.optionalArgs.put("id", "none");
        this.optionalArgs.put("faction", "you");

        this.requirements = new CommandRequirements.Builder(Permission.SET_GUILD)
                .playerOnly()
                .memberOnly()
                .build();
    }

    @Override
    public void perform(CommandContext context) {
        String guildId = context.argAsString(0, null);
        Faction faction = context.argAsFaction(1, context.faction);
        JDA jda = Discord.jda;
        if (jda != null) {
            if (!this.waiterAdded) {
                //Do Not Change, Must Remain EventWaiter[]
                jda.addEventListener(new EventWaiter[]{this.eventWaiter});
                this.waiterAdded = true;
            }

            if (guildId != null && !guildId.equalsIgnoreCase("null")) {
                Guild guild = null;
                try {
                    guild = jda.getGuildById(guildId);
                } catch (NumberFormatException e) {
                }

                if (guild == null) {
                    context.msg(TL.SET_GUILD_ID_INVALID_ID);
                } else if (Factions.getInstance().getAllFactions().stream().anyMatch((f) -> guildId.equals(f.getGuildId()))) {
                    context.msg(TL.SET_GUILD_ID_GUILD_ALREADY_LINKED);
                } else {
                    context.msg(TL.SET_GUILD_ID_PMING_OWNER);
                    User user = guild.retrieveOwner().complete().getUser();
                    Guild finalGuild = guild;
                    Guild finalGuild1 = guild;
                    user.openPrivateChannel().queue((privateChannel) -> privateChannel.sendMessage("Link guild **" + finalGuild1.getName() + "** to faction **" + ChatColor.stripColor(faction.getTag()) + "**?").queue((message) -> {
                        String checkMark = "✅";
                        message.addReaction(checkMark).queue();
                        this.eventWaiter.waitForEvent(PrivateMessageReactionAddEvent.class, (event) -> event.getReactionEmote().getName().equals(checkMark) && event.getUser().getId().equals(user.getId()) && event.getMessageId().equals(message.getId()), (event) -> {
                            faction.setGuildId(context.argAsString(0));
                            context.msg(TL.SET_GUILD_ID_SUCCESS);
                            privateChannel.sendMessage("Successfully linked **" + finalGuild.getName() + " & " + ChatColor.stripColor(faction.getTag()) + "**").queue();
                        }, 15L, TimeUnit.SECONDS, () -> {
                            privateChannel.sendMessage(TL.SET_GUILD_ID_TIMED_OUT_DISCORD.toString()).queue();
                            context.msg(TL.SET_GUILD_ID_TIMED_OUT_MINECRAFT);
                        });
                    }, (t) -> context.msg(TL.SET_GUILD_ID_UNABLE_TO_MESSAGE_GUILD_OWNER)), (t) -> context.msg(TL.SET_GUILD_ID_UNABLE_TO_MESSAGE_GUILD_OWNER));
                }
            } else {
                faction.setGuildId(null);
                faction.setWeeWooChannelId(null);
                faction.setBufferNotifyChannelId(null);
                faction.setWallNotifyChannelId(null);
                faction.setFactionChatChannelId(null);
                context.msg(TL.SET_GUILD_ID_RESET_ID);
            }
        }
    }

    @Override
    public TL getUsageTranslation() {
        return TL.SET_GUILD_ID_USAGE;
    }

}
