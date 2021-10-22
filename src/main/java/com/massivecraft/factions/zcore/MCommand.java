package com.massivecraft.factions.zcore;

import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.zcore.util.TL;
import com.massivecraft.factions.zcore.util.TextUtil;
import mkremins.fanciful.FancyMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;


public abstract class MCommand<T extends MPlugin> {

    // FIELD: Help Short
    // This field may be left blank and will in such case be loaded from the permissions node instead.
    // Thus make sure the permissions node description is an action description like "eat hamburgers" or "do admin stuff".
    private final String helpShort;
    public T p;
    // The sub-commands to this command
    public List<MCommand<?>> subCommands;
    // The different names this commands will react to
    public List<String> aliases;
    public boolean allowNoSlashAccess;
    // Information on the args
    public List<String> requiredArgs;
    public LinkedHashMap<String, String> optionalArgs;
    public boolean errorOnToManyArgs = true;
    public List<String> helpLong;
    public CommandVisibility visibility;
    // Some information on permissions
    public boolean senderMustBePlayer;
    public boolean senderMustHaveFaction;
    public String permission;
    // Information available on execution of the command
    public CommandSender sender; // Will always be set
    public Player me; // Will only be set when the sender is a player
    public boolean senderIsConsole;
    public List<String> args; // Will contain the arguments, or and empty list if there are none.
    public List<MCommand<?>> commandChain = new ArrayList<>(); // The command chain used to execute this command

    public MCommand(T p) {
        this.p = p;

        this.permission = null;

        this.allowNoSlashAccess = false;

        this.subCommands = new ArrayList<>();
        this.aliases = new ArrayList<>();

        this.requiredArgs = new ArrayList<>();
        this.optionalArgs = new LinkedHashMap<>();

        this.helpShort = null;
        this.helpLong = new ArrayList<>();
        this.visibility = CommandVisibility.VISIBLE;
    }

    public void addSubCommand(MCommand<?> subCommand) {
        subCommand.commandChain.addAll(this.commandChain);
        subCommand.commandChain.add(this);
        this.subCommands.add(subCommand);
    }

    public String getHelpShort() {
        return this.helpShort != null ? this.helpShort : getUsageTranslation().toString();
    }

    public abstract TL getUsageTranslation();

    public void setCommandSender(CommandSender sender) {
        this.sender = sender;
        if (sender instanceof Player) {
            this.me = (Player) sender;
            this.senderIsConsole = false;
        } else {
            this.me = null;
            this.senderIsConsole = true;
        }
    }

    // The commandChain is a list of the parent command chain used to get to this command.
    public void execute(CommandSender sender, List<String> args, List<MCommand<?>> commandChain) {
        // Set the execution-time specific variables
        setCommandSender(sender);
        this.args = args;
        this.commandChain = commandChain;

        // Is there a matching sub command?
        if (args.size() > 0)
            for (MCommand<?> subCommand : this.subCommands)
                if (subCommand.aliases.contains(args.get(0).toLowerCase())) {
                    args.remove(0);
                    commandChain.add(this);
                    subCommand.execute(sender, args, commandChain);
                    return;
                }
        if (!validCall(this.sender, this.args) || !this.isEnabled())
            return;
        perform();
    }

    public void execute(CommandSender sender, List<String> args) {
        execute(sender, args, new ArrayList<>());
    }

    // This is where the command action is performed.
    public abstract void perform();


    // -------------------------------------------- //
    // Call Validation
    // -------------------------------------------- //

    /**
     * In this method we validate that all prerequisites to perform this command has been met.
     *
     * @param sender of the command
     * @param args   of the command
     * @return true if valid, false if not.
     */
    // TODO: There should be a boolean for silence
    public boolean validCall(CommandSender sender, List<String> args) {
        return validSenderType(sender, true) && validSenderPermissions(sender, true) && validArgs(args, sender);
    }

    public boolean isEnabled() {
        return true;
    }


    public boolean validSenderType(CommandSender sender, boolean informSenderIfNot) {
        if (this.senderMustBePlayer && !(sender instanceof Player)) {
            if (informSenderIfNot) msg(TL.GENERIC_PLAYERONLY);
            return false;
        }
        return !this.senderMustHaveFaction || !FPlayers.getInstance().getByPlayer((Player) sender).hasFaction();
    }

    public boolean validSenderPermissions(CommandSender sender, boolean informSenderIfNot) {
        return this.permission == null || p.perm.has(sender, this.permission, informSenderIfNot);
    }

    public boolean validArgs(List<String> args, CommandSender sender) {
        if (args.size() < this.requiredArgs.size()) {
            if (sender == null) return false;
            msg(TL.GENERIC_ARGS_TOOFEW);
            sender.sendMessage(this.getUseageTemplate());
            return false;
        }

        if (args.size() > this.requiredArgs.size() + this.optionalArgs.size() && this.errorOnToManyArgs) {
            if (sender == null) return false;
            List<String> theToMany = args.subList(this.requiredArgs.size() + this.optionalArgs.size(), args.size());
            msg(TL.GENERIC_ARGS_TOOMANY, TextUtil.implode(theToMany, " "));
            sender.sendMessage(this.getUseageTemplate());
            return false;
        }
        return true;
    }

    public boolean validArgs(List<String> args) {
        return this.validArgs(args, null);
    }

    // -------------------------------------------- //
    // Help and Usage information
    // -------------------------------------------- //

    public String getUseageTemplate(List<MCommand<?>> commandChain, boolean addShortHelp) {
        StringBuilder ret = new StringBuilder();
        ret.append(p.txt.parseTags("<c>"));
        ret.append('/');

        for (MCommand<?> mc : commandChain) {
            ret.append(TextUtil.implode(mc.aliases, ","));
            ret.append(' ');
        }

        ret.append(TextUtil.implode(this.aliases, ","));
        List<String> args = new ArrayList<>();

        for (String requiredArg : this.requiredArgs)
            args.add("<" + requiredArg + ">");

        for (Entry<String, String> optionalArg : this.optionalArgs.entrySet()) {
            String val = optionalArg.getValue();
            if (val == null) val = "";
            else val = "=" + val;
            args.add("[" + optionalArg.getKey() + val + "]");
        }

        if (args.size() > 0) {
            ret.append(p.txt.parseTags("<plugin> "));
            ret.append(TextUtil.implode(args, " "));
        }

        if (addShortHelp) {
            ret.append(p.txt.parseTags(" <i>"));
            ret.append(this.getHelpShort());
        }

        return ret.toString();
    }

    public String getUseageTemplate(boolean addShortHelp) {
        return getUseageTemplate(this.commandChain, addShortHelp);
    }

    public String getUseageTemplate() {
        return getUseageTemplate(false);
    }

    // -------------------------------------------- //
    // Message Sending Helpers
    // -------------------------------------------- //

    public void msg(TL translation, Object... args) {
        sender.sendMessage(p.txt.parse(translation.toString(), args));
    }

    public void sendMessage(String msg) {
        sender.sendMessage(msg);
    }

    public void sendFancyMessage(FancyMessage message) {
        message.send(sender);
    }

    // STRING ======================
    public String argAsString(int idx, String def) {
        if (this.args.size() < idx + 1)
            return def;
        return this.args.get(idx);
    }

    public String argAsString(int idx) {
        return this.argAsString(idx, null);
    }

    // INT ======================
    public Integer strAsInt(String str, Integer def) {
        if (str == null) return def;
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return def;
        }
    }

    public Integer argAsInt(int idx, Integer def) {
        return strAsInt(this.argAsString(idx), def);
    }

    // Double ======================
    public Double strAsDouble(String str, Double def) {
        if (str == null) return def;
        try {
            return Double.parseDouble(str);
        } catch (Exception e) {
            return def;
        }
    }

    public Double argAsDouble(int idx, Double def) {
        return strAsDouble(this.argAsString(idx), def);
    }

    // TODO: Go through the str conversion for the other arg-readers as well.
    // Boolean ======================
    public Boolean strAsBool(String str) {
        str = str.toLowerCase();
        return str.startsWith("y") || str.startsWith("t") || str.startsWith("on") || str.startsWith("+") || str.startsWith("1");
    }

    public Boolean argAsBool(int idx, boolean def) {
        String str = this.argAsString(idx);
        if (str == null) return def;
        return strAsBool(str);
    }

    // PLAYER ======================
    public Player strAsPlayer(String name, Player def, boolean msg) {
        Player ret = def;
        if (name != null) if (Bukkit.getServer().getPlayer(name) != null) ret = Bukkit.getServer().getPlayer(name);
        if (msg && ret == null) this.msg(TL.GENERIC_NOPLAYERFOUND, name);
        return ret;
    }

    public Player argAsPlayer(int idx, Player def, boolean msg) {
        return this.strAsPlayer(this.argAsString(idx), def, msg);
    }

    public Player argAsPlayer(int idx, Player def) {
        return this.argAsPlayer(idx, def, true);
    }

    // BEST PLAYER MATCH ======================
    public Player strAsBestPlayerMatch(String name, Player def, boolean msg) {
        Player ret = def;
        if (name != null) {
            List<Player> players = Bukkit.getServer().matchPlayer(name);
            if (players.size() > 0)
                ret = players.get(0);
        }
        if (msg && ret == null)
            this.msg(TL.GENERIC_NOPLAYERMATCH, name);
        return ret;
    }

    public Player argAsBestPlayerMatch(int idx, Player def, boolean msg) {
        return this.strAsBestPlayerMatch(this.argAsString(idx), def, msg);
    }
}