package com.massivecraft.factions.zcore;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.massivecraft.factions.*;
import com.massivecraft.factions.zcore.persist.SaveTask;
import com.massivecraft.factions.zcore.util.PermUtil;
import com.massivecraft.factions.zcore.util.Persist;
import com.massivecraft.factions.zcore.util.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.*;
import java.util.Map.Entry;
import java.util.logging.Level;


public abstract class MPlugin extends JavaPlugin {

    public static MPlugin instance;
    public final Gson gson = this.getGsonBuilder().create();
    private final List<MCommand<?>> baseCommands = new ArrayList<>();
    private final Map<UUID, Long> timers = new HashMap<>();
    public Persist persist;
    public TextUtil txt;
    public PermUtil perm;
    public String refCommand = "";
    public Map<UUID, Integer> stuckMap = new HashMap<>();
    public Map<String, String> rawTags = new LinkedHashMap<>();
    protected boolean loadSuccessful = false;
    private Integer saveTask = null;
    private boolean autoSave = true;
    private MPluginSecretPlayerListener mPluginSecretPlayerListener;
    private long timeEnableStart;

    public boolean getAutoSave() {
        return this.autoSave;
    }

    public void setAutoSave(boolean val) {
        this.autoSave = val;
    }

    public List<MCommand<?>> getBaseCommands() {
        return this.baseCommands;
    }

    public boolean preEnable() {
        instance = this;
        log("- Initiating Plugin Start -");
        timeEnableStart = System.currentTimeMillis();

        this.getDataFolder().mkdirs();

        this.perm = new PermUtil(this);
        this.persist = new Persist(this);

        this.txt = new TextUtil();
        initTXT();

        // attempt to get first command defined in plugin.yml as reference command, if any commands are defined in there
        // reference command will be used to prevent "unknown command" console messages
        try {
            Map<String, Map<String, Object>> refCmd = this.getDescription().getCommands();
            if (refCmd != null && !refCmd.isEmpty()) {
                this.refCommand = (String) (refCmd.keySet().toArray()[0]);
            }
        } catch (ClassCastException ex) {
            ex.printStackTrace();
        }

        // Create and register player command listener
        this.mPluginSecretPlayerListener = new MPluginSecretPlayerListener(this);
        getServer().getPluginManager().registerEvents(this.mPluginSecretPlayerListener, this);

        // Register recurring tasks
        if (saveTask == null && Conf.saveToFileEveryXMinutes > 0.0) {
            long saveTicks = (long) (20 * 60 * Conf.saveToFileEveryXMinutes); // Approximately every 30 min by default
            saveTask = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new SaveTask(this), saveTicks, saveTicks);
        }
        loadSuccessful = true;
        return true;
    }

    public void postEnable() {
        log("- Plugin Successfully Enabled (Process took: " + (System.currentTimeMillis() - timeEnableStart) + "ms) -");
    }

    public void onDisable() {
        if (saveTask != null) {
            this.getServer().getScheduler().cancelTask(saveTask);
            saveTask = null;
        }
        if (loadSuccessful) {
            Factions.getInstance().forceSave();
            FPlayers.getInstance().forceSave();
            Board.getInstance().forceSave();
        }
        log("Disabled");
    }

    // -------------------------------------------- //
    // Some inits...
    // You are supposed to override these in the plugin if you aren't satisfied with the defaults
    // The goal is that you always will be satisfied though.
    // -------------------------------------------- //

    public void suicide() {
        log("Now I suicide!");
        this.getServer().getPluginManager().disablePlugin(this);
    }

    // -------------------------------------------- //
    // LANG AND TAGS
    // -------------------------------------------- //

    public GsonBuilder getGsonBuilder() {
        return new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().serializeNulls().excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.VOLATILE);
    }

    public void initTXT() {
        Util.addRawTags();

        Type type = new TypeToken<Map<String, String>>() {
        }.getType();

        Map<String, String> tagsFromFile = this.persist.load(type, "data/tags");
        if (tagsFromFile != null)
            this.rawTags.putAll(tagsFromFile);
        this.persist.save(this.rawTags, "data/tags");

        for (Entry<String, String> rawTag : this.rawTags.entrySet())
            this.txt.tags.put(rawTag.getKey(), TextUtil.parseColor(rawTag.getValue()));
    }

    public boolean logPlayerCommands() {
        return true;
    }

    public boolean handleCommand(CommandSender sender, String commandString, boolean testOnly) {
        return handleCommand(sender, commandString, testOnly, false);
    }

    public boolean handleCommand(final CommandSender sender, String commandString, boolean testOnly, boolean async) {
        boolean noSlash = true;
        if (commandString.startsWith("/")) {
            noSlash = false;
            commandString = commandString.substring(1);
        }
        for (final MCommand<?> command : this.getBaseCommands()) {
            if (!noSlash || command.allowNoSlashAccess) {
                for (String alias : command.aliases) {
                    if (!commandString.startsWith(alias + "  ")) {
                        if (commandString.startsWith(alias + " ") || commandString.equals(alias)) {
                            final List<String> args = new ArrayList<>(Arrays.asList(commandString.split("\\s+")));
                            args.remove(0);
                            if (!testOnly) {
                                if (async)
                                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, () -> command.execute(sender, args));
                                else
                                    command.execute(sender, args);
                            }
                            return true;
                        }
                        return false;
                    }
                }
            }
        }
        return false;
    }

    public boolean handleCommand(CommandSender sender, String commandString) {
        return this.handleCommand(sender, commandString, false);
    }

    // -------------------------------------------- //
    // HOOKS
    // -------------------------------------------- //
    public void preAutoSave() {

    }

    public void postAutoSave() {

    }

    public Map<UUID, Integer> getStuckMap() {
        return this.stuckMap;
    }

    public Map<UUID, Long> getTimers() {
        return this.timers;
    }

    // -------------------------------------------- //
    // LOGGING
    // -------------------------------------------- //

    public void log(Object msg) {
        log(Level.INFO, msg);
    }

    public void log(String str, Object... args) {
        log(Level.INFO, this.txt.parse(str, args));
    }

    public void log(Level level, String str, Object... args) {
        log(level, this.txt.parse(str, args));
    }

    public void log(Level level, Object msg) {
        getLogger().log(level, "" + " " + msg); // Full name is really ugly
    }
}
