package com.massivecraft.factions.cmd;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.FactionsPlugin;
import com.massivecraft.factions.Util;
import com.massivecraft.factions.integration.Econ;
import com.massivecraft.factions.zcore.CommandVisibility;
import com.massivecraft.factions.zcore.util.TL;
import com.massivecraft.factions.zcore.util.TextUtil;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.text.SimpleDateFormat;
import java.util.*;


public abstract class FCommand {

    /**
     * @author FactionsUUID Team - Modified By CmdrKittens
     */

    public SimpleDateFormat sdf = new SimpleDateFormat(TL.DATE_FORMAT.toString());

    // Command Aliases
    public List<String> aliases;

    // Information on the args
    public List<String> requiredArgs;
    public LinkedHashMap<String, String> optionalArgs;

    // Requirements to execute this command
    public CommandRequirements requirements;
    /*
        Subcommands
     */
    public List<FCommand> subCommands;
    /*
        Help
     */
    public List<String> helpLong;
    public CommandVisibility visibility;
    private String helpShort;

    public FCommand() {

        requirements = new CommandRequirements.Builder(null).build();

        this.subCommands = new ArrayList<>();
        this.aliases = new ArrayList<>();

        this.requiredArgs = new ArrayList<>();
        this.optionalArgs = new LinkedHashMap<>();

        this.helpShort = null;
        this.helpLong = new ArrayList<>();
        this.visibility = CommandVisibility.VISIBLE;
    }

    public abstract void perform(CommandContext context);

    public void execute(CommandContext context) {
        // Is there a matching sub command?
        if (context.args.size() > 0)
            for (FCommand subCommand : this.subCommands)
                if (subCommand.aliases.contains(context.args.get(0).toLowerCase())) {
                    context.args.remove(0);
                    context.commandChain.add(this);
                    subCommand.execute(context);
                    return;
                }
        if (!validCall(context))
            return;
        if (!this.isEnabled(context))
            return;
        perform(context);
    }

    public boolean validCall(CommandContext context) {
        return requirements.computeRequirements(context, true) && validArgs(context);
    }

    public boolean isEnabled(CommandContext context) {
        if (FactionsPlugin.getInstance().getLocked() && requirements.disableOnLock) {
            context.msg("<b>Factions was locked by an admin. Please try again later.");
            return false;
        }
        return true;
    }

    public boolean validArgs(CommandContext context) {
        if (context.args.size() < this.requiredArgs.size()) {
            if (context.sender != null) {
                context.msg(TL.GENERIC_ARGS_TOOFEW);
                context.sender.sendMessage(this.getUsageTemplate(context));
            }
            return false;
        }

        if (context.args.size() > this.requiredArgs.size() + this.optionalArgs.size() && this.requirements.errorOnManyArgs) {
            if (context.sender != null) {
                // Get the to many string slice
                List<String> theToMany = context.args.subList(this.requiredArgs.size() + this.optionalArgs.size(), context.args.size());
                context.msg(TL.GENERIC_ARGS_TOOMANY, TextUtil.implode(theToMany, " "));
                context.sender.sendMessage(this.getUsageTemplate(context));
            }
            return false;
        }
        return true;
    }

    public void addSubCommand(FCommand subCommand) {
        this.subCommands.add(subCommand);
    }

    public String getHelpShort() {
        if (this.helpShort == null)
            return getUsageTranslation().toString();
        return this.helpShort;
    }

    public void setHelpShort(String val) {
        this.helpShort = val;
    }

    public abstract TL getUsageTranslation();


    /*
        Common Logic
     */
    public List<String> getToolTips(FPlayer player) {
        List<String> lines = new ArrayList<>();
        for (String s : FactionsPlugin.getInstance().getConfig().getStringList("tooltips.show"))
            lines.add(ChatColor.translateAlternateColorCodes('&', replaceFPlayerTags(s, player)));
        return lines;
    }

    public List<String> getToolTips(Faction faction) {
        List<String> lines = new ArrayList<>();
        for (String s : FactionsPlugin.getInstance().getConfig().getStringList("tooltips.list"))
            lines.add(ChatColor.translateAlternateColorCodes('&', replaceFactionTags(s, faction)));
        return lines;
    }

    public String replaceFPlayerTags(String s, FPlayer player) {
        List<String> keys = Arrays.asList("{balance}", "{lastSeen}", "{power}", "{group}");
        for (String str : keys)
            if (s.contains(str))
                switch (str) {
                    case "{balance}":
                        String balance = Econ.isSetup() ? Econ.getFriendlyBalance(player) : "no balance";
                        s = s.replace("{balance}", balance);
                    case "{lastSeen}":
                        String humanized = DurationFormatUtils.formatDurationWords(System.currentTimeMillis() - player.getLastLoginTime(), true, true) + " ago";
                        String lastSeen = player.isOnline() ? ChatColor.GREEN + "Online" : (System.currentTimeMillis() - player.getLastLoginTime() < 432000000 ? ChatColor.YELLOW + humanized : ChatColor.RED + humanized);
                        s = s.replace("{lastSeen}", lastSeen);
                    case "{power}":
                        String power = player.getPowerRounded() + "/" + player.getPowerMaxRounded();
                        s = s.replace("{power}", power);
                    case "{group}":
                        String group = Util.getPrimaryGroup(Bukkit.getOfflinePlayer(UUID.fromString(player.getId())));
                        s = s.replace("{group}", group);
                }
        return s;
    }

    public String replaceFactionTags(String s, Faction faction) {
        List<String> keys = Arrays.asList("{power}", "{maxPower}", "{leader}", "{group}", "{chunks}", "{members}", "{online}");
        for (String str : keys)
            if (s.contains(str))
                switch (str) {
                    case "{power}":
                        s = s.replace("{power}", String.valueOf(faction.getPowerRounded()));
                    case "{maxPower}":
                        s = s.replace("{maxPower}", String.valueOf(faction.getPowerMaxRounded()));
                    case "{leader}":
                        FPlayer fLeader = faction.getFPlayerAdmin();
                        String leader = fLeader == null ? "Server" : fLeader.getName().substring(0, fLeader.getName().length() > 14 ? 13 : fLeader.getName().length());
                        s = s.replace("{leader}", leader);
                    case "{chunks}":
                        s = s.replace("{chunks}", String.valueOf(faction.getLandRounded()));
                    case "{members}":
                        s = s.replace("{members}", String.valueOf(faction.getSize()));
                    case "{online}":
                        s = s.replace("{online}", String.valueOf(faction.getOnlinePlayers().size()));
                }
        return s;
    }

    /*
    Help and Usage information
 */
    public String getUsageTemplate(CommandContext context, boolean addShortHelp) {
        StringBuilder ret = new StringBuilder();
        ret.append(Util.color(TL.COMMAND_USEAGE_TEMPLATE_COLOR.toString()));
        ret.append('/');

        for (FCommand fc : context.commandChain) {
            ret.append(TextUtil.implode(fc.aliases, ","));
            ret.append(' ');
        }

        ret.append(TextUtil.implode(this.aliases, ","));

        List<String> args = new ArrayList<>();

        for (String requiredArg : this.requiredArgs)
            args.add("<" + requiredArg + ">");

        for (Map.Entry<String, String> optionalArg : this.optionalArgs.entrySet()) {
            String val = optionalArg.getValue();
            if (val == null)
                val = "";
            else
                val = "=" + val;
            args.add("[" + optionalArg.getKey() + val + "]");
        }

        if (args.size() > 0) {
            ret.append(FactionsPlugin.getInstance().txt.parseTags(" "));
            ret.append(TextUtil.implode(args, " "));
        }

        if (addShortHelp) {
            ret.append(FactionsPlugin.getInstance().txt.parseTags(" "));
            ret.append(this.getHelpShort());
        }

        return ret.toString();
    }

    public String getUsageTemplate(CommandContext context) {
        return getUsageTemplate(context, false);
    }

}
