package com.massivecraft.factions;

import com.massivecraft.factions.cmd.Aliases;
import com.massivecraft.factions.cmd.CmdAutoHelp;
import com.massivecraft.factions.cmd.FCmdRoot;
import com.massivecraft.factions.cmd.audit.FChestListener;
import com.massivecraft.factions.cmd.audit.FLogManager;
import com.massivecraft.factions.cmd.audit.FLogType;
import com.massivecraft.factions.cmd.check.CheckTask;
import com.massivecraft.factions.cmd.check.WeeWooTask;
import com.massivecraft.factions.cmd.chest.AntiChestListener;
import com.massivecraft.factions.cmd.reserve.ReserveObject;
import com.massivecraft.factions.integration.Econ;
import com.massivecraft.factions.listeners.*;
import com.massivecraft.factions.missions.MissionHandler;
import com.massivecraft.factions.shield.ShieldListener;
import com.massivecraft.factions.util.FlightEnhance;
import com.massivecraft.factions.util.SeeChunkUtil;
import com.massivecraft.factions.util.particle.ParticleProvider;
import com.massivecraft.factions.util.timer.TimerManager;
import com.massivecraft.factions.util.wait.WaitExecutor;
import com.massivecraft.factions.zcore.MPlugin;
import com.massivecraft.factions.zcore.frame.fupgrades.UpgradesListener;
import me.lucko.commodore.CommodoreProvider;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.scheduler.BukkitTask;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class Util {

    public static FLogManager getFlogManager() {
        return FactionsPlugin.getInstance().fLogManager;
    }

    public static void logFactionEvent(Faction faction, FLogType type, String... arguments) {
        FactionsPlugin.getInstance().fLogManager.log(faction, type, arguments);
    }
    /*

    YamlConfiguration configuracion = YamlConfiguration.loadConfiguration(configFile);

    String defpermisos = "";
    String textpermisos = configuracion.getString("Configuration.NoPermissionsMessage", defpermisos);
    String permisos = ChatColor.translateAlternateColorCodes('&', textpermisos);

    String defprefix = "";
    String textprefix = configuracion.getString("Configuration.Prefix", defprefix);
    String prefix = ChatColor.translateAlternateColorCodes('&', textprefix);

     */


    public static String color(String line) {
        line = ChatColor.translateAlternateColorCodes('&', line);
        return line;
    }

    public static List<String> colorList(List<String> lore) {
        for (int i = 0; i <= lore.size() - 1; i++) lore.set(i, Util.color(lore.get(i)));
        return lore;
    }

    public static List<ReserveObject> getFactionReserves() {
        return FactionsPlugin.getInstance().reserveObjects;
    }

    public static void debug(Level level, String s) {
        if (FactionsPlugin.getInstance().getConfig().getBoolean("debug", false)) Bukkit.getLogger().log(level, s);
    }

    public static void debug(String s) {
        debug(Level.INFO, s);
    }

    public static void handleFactionTagExternally(boolean notByFactions) {
        Conf.chatTagHandledByAnotherPlugin = notByFactions;
    }

    public static String getPrimaryGroup(OfflinePlayer player) {
        return FactionsPlugin.perms == null || !FactionsPlugin.perms.hasGroupSupport() ? " " : FactionsPlugin.perms.getPrimaryGroup(Bukkit.getWorlds().get(0).toString(), player);
    }

    public static FactionsPlayerListener getFactionsPlayerListener() {
        return FactionsPlugin.getInstance().factionsPlayerListener;
    }

    public static ParticleProvider getParticleProvider() {
        return FactionsPlugin.getInstance().particleProvider;
    }

    public static SeeChunkUtil getSeeChunkUtil() {
        return FactionsPlugin.getInstance().seeChunkUtil;
    }

    public static TimerManager getTimerManager() {
        return FactionsPlugin.getInstance().timerManager;
    }

    public static void checkVault() {
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
            System.out.println("You are missing dependencies!");
            System.out.println("Please verify [Vault] is installed!");
            Conf.save();
            Bukkit.getPluginManager().disablePlugin(FactionsPlugin.getInstance());
            return;
        }
    }


    public static void checkLunar() {
        if (Bukkit.getServer().getPluginManager().getPlugin("LunarClient-API") == null) {
            System.out.println("You are missing the LunarClient-API. Please install it to use the Lunar features.");
            Conf.save();
            Bukkit.getPluginManager().disablePlugin(FactionsPlugin.getInstance());
            return;

        }
    }




    public static void addFPlayers() {
        for (FPlayer fPlayer : FPlayers.getInstance().getAllFPlayers()) {
            Faction faction = Factions.getInstance().getFactionById(fPlayer.getFactionId());
            if (faction == null) {
                FactionsPlugin.getInstance().log("Invalid faction id on " + fPlayer.getName() + ":" + fPlayer.getFactionId());
                fPlayer.resetFactionData(false);
                continue;
            }
            if (fPlayer.isAlt()) faction.addAltPlayer(fPlayer);
            else faction.addFPlayer(fPlayer);
        }
    }

    public static void registerChecks() {
        if (Conf.useCheckSystem) {
            int minute = 1200;
            FactionsPlugin.getInstance().getServer().getScheduler().runTaskTimerAsynchronously(FactionsPlugin.getInstance(), new CheckTask(FactionsPlugin.getInstance(), 3), 0L, minute * 3);
            FactionsPlugin.getInstance().getServer().getScheduler().runTaskTimerAsynchronously(FactionsPlugin.getInstance(), new CheckTask(FactionsPlugin.getInstance(), 5), 0L, minute * 5);
            FactionsPlugin.instance.getServer().getScheduler().runTaskTimerAsynchronously(FactionsPlugin.instance, new CheckTask(FactionsPlugin.instance, 10), 0L, minute * 10);
            FactionsPlugin.instance.getServer().getScheduler().runTaskTimerAsynchronously(FactionsPlugin.instance, new CheckTask(FactionsPlugin.instance, 15), 0L, minute * 15);
            FactionsPlugin.instance.getServer().getScheduler().runTaskTimerAsynchronously(FactionsPlugin.instance, new CheckTask(FactionsPlugin.instance, 30), 0L, minute * 30);
            FactionsPlugin.instance.getServer().getScheduler().runTaskTimer(FactionsPlugin.instance, CheckTask::cleanupTask, 0L, 1200L);
            FactionsPlugin.instance.getServer().getScheduler().runTaskTimerAsynchronously(FactionsPlugin.instance, new WeeWooTask(FactionsPlugin.instance), 600L, 600L);
        }
    }

    public static void registerEvents() {
        FactionsPlugin.instance.eventsListener = new Listener[]{
                new FactionsChatListener(),
                new FactionsEntityListener(),
                new FactionsExploitListener(),
                new FactionsBlockListener(),
                new UpgradesListener(),
                new MissionHandler(FactionsPlugin.instance),
                new FChestListener(),
                new MenuListener(),
                new AntiChestListener(),
                new ShieldListener()
        };

        for (Listener eventListener : FactionsPlugin.instance.eventsListener)
            Bukkit.getServer().getPluginManager().registerEvents(eventListener, FactionsPlugin.instance);
    }

    public static void registerSeeChunk() {
        if (FactionsPlugin.instance.getConfig().getBoolean("see-chunk.particles")) {
            double delay = Math.floor(FactionsPlugin.instance.getConfig().getDouble("see-chunk.interval") * 20);
            FactionsPlugin.instance.seeChunkUtil = new SeeChunkUtil();
            FactionsPlugin.instance.seeChunkUtil.runTaskTimer(FactionsPlugin.instance, 0, (long) delay);
        }
    }

    public static void migrateFPlayerLeaders() {
        List<String> lines = new ArrayList<>();
        File fplayerFile = new File("plugins" + File.pathSeparator + "Factions" + File.pathSeparator + "data" + File.pathSeparator + "players.json");

        try {
            BufferedReader br = new BufferedReader(new FileReader(fplayerFile));
            System.out.println("Migrating old players.json file.");
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("\"role\": \"ADMIN\"")) {
                    line = line.replace("\"role\": \"ADMIN\"", "\"role\": " + "\"LEADER\"");
                }
                lines.add(line);
            }
            br.close();
            BufferedWriter bw = new BufferedWriter(new FileWriter(fplayerFile));
            for (String newLine : lines) {
                bw.write(newLine + "\n");
            }
            bw.flush();
            bw.close();
        } catch (IOException ex) {
            System.out.println("File was not found for players.json, assuming"
                    + " there is no need to migrate old players.json file.");
        }
    }

    public static Economy getEcon() {
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        return rsp.getProvider();
    }

    public static void sendBetaAlert() {
        if (Bukkit.getPluginManager().getPlugin("Factions").getDescription().getFullName().contains("BETA")) {
            System.out.println("You are using a BETA version of the plugin!");
            System.out.println("This comes with risks of small bugs in newer features!");
            System.out.println("Our discord support: https://discord.gg/GjFdmVm96t");
        }
    }

    public static void addRawTags() {
        MPlugin.instance.rawTags.put("l", "<green>"); // logo
        MPlugin.instance.rawTags.put("a", "<gold>"); // art
        MPlugin.instance.rawTags.put("n", "<silver>"); // notice
        MPlugin.instance.rawTags.put("i", "<yellow>"); // info
        MPlugin.instance.rawTags.put("g", "<lime>"); // good
        MPlugin.instance.rawTags.put("b", "<rose>"); // bad
        MPlugin.instance.rawTags.put("h", "<pink>"); // highligh
        MPlugin.instance.rawTags.put("c", "<aqua>"); // command
        MPlugin.instance.rawTags.put("plugin", "<teal>"); // parameter
    }

    public static void initSetup() {
        FlightEnhance.get().wipe();
        WaitExecutor.startTask();
        com.massivecraft.factions.integration.Essentials.setup();
        FactionsPlugin.instance.hookedPlayervaults = DataUtils.setupPlayervaults();
        FactionsPlugin.instance.initConfig();
        FPlayers.getInstance().load();
        Factions.getInstance().load();

        Util.addFPlayers();
        Factions.getInstance().getAllFactions().forEach(Faction::refreshFPlayers);

        if (FactionsPlugin.instance.getConfig().getBoolean("enable-faction-flight", true)) FlightEnhance.get().start();

        Board.getInstance().load();
        Board.getInstance().clean();

        Aliases.load();
        FactionsPlugin.instance.cmdBase = new FCmdRoot();
        FactionsPlugin.instance.cmdAutoHelp = new CmdAutoHelp();

        Econ.setup();
        DataUtils.setupPermissions();

        Version.initParticleProvider();
        Version.initNonPacketParticles();

        Util.registerSeeChunk();
        Util.registerChecks();

        FactionsPlugin.instance.startAutoLeaveTask(false);

        FactionsPlugin.cachedRadiusClaim = Conf.useRadiusClaimSystem;

        FactionsPlugin.instance.fLogManager.loadLogs(FactionsPlugin.instance);

        FactionsPlugin.instance.timerManager = new TimerManager(FactionsPlugin.instance);
        FactionsPlugin.instance.timerManager.reloadTimerData();
        System.out.println("Successfully hooked into " + FactionsPlugin.instance.getTimers().size() + " timers!");

        FactionsPlugin.instance.getServer().getPluginManager().registerEvents(FactionsPlugin.instance.factionsPlayerListener = new FactionsPlayerListener(), FactionsPlugin.instance);
        Util.registerEvents();

        if (Conf.useGraceSystem)
            FactionsPlugin.instance.getServer().getPluginManager().registerEvents(FactionsPlugin.instance.timerManager.graceTimer, FactionsPlugin.instance);

        FactionsPlugin.instance.getCommand(FactionsPlugin.instance.refCommand).setExecutor(FactionsPlugin.instance.cmdBase);
        if (!CommodoreProvider.isSupported())
            FactionsPlugin.instance.getCommand(FactionsPlugin.instance.refCommand).setTabCompleter(FactionsPlugin.instance);

        FactionsPlugin.instance.reserveObjects = new ArrayList<>();
        DataUtils.initReserves();

        Util.sendBetaAlert();

        System.out.println("WARNING: Shields may not work as intended! Beware");

        PlaceholderUtil.setupPlaceholderAPI();
        FactionsPlugin.instance.postEnable();
        FactionsPlugin.startupFinished = true;
    }

}
