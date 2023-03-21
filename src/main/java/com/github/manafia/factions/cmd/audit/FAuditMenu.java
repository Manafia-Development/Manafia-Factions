package com.github.manafia.factions.cmd.audit;

/*
  @author Saser
 */

import com.google.common.collect.Lists;
import com.github.manafia.factions.Faction;
import com.github.manafia.factions.FactionsPlugin;
import com.github.manafia.factions.util.CC;
import com.github.manafia.factions.util.ItemBuilder;
import com.github.manafia.factions.util.serializable.ClickableItemStack;
import com.github.manafia.factions.util.serializable.GUIMenu;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class FAuditMenu extends GUIMenu {
    private Player player;
    private boolean showTimestamps = true;
    private Faction faction;

    public FAuditMenu(Player player, Faction faction) {
        super("Faction Logs", 18);
        this.faction = faction;
        this.player = player;
    }

    public void drawItems() {
        for (FLogType type : FLogType.values()) {
            if (type.getSlot() == -1) continue;
            if (type != FLogType.F_TNT || FactionsPlugin.getInstance().getConfig().getBoolean("f-points.Enabled")) {
                FactionLogs logs = FactionsPlugin.instance.getFlogManager().getFactionLogMap().get(faction.getId());
                if (logs == null) logs = new FactionLogs();
                LinkedList<FactionLogs.FactionLog> recentLogs = logs.getMostRecentLogs().get(type);
                if (recentLogs == null) recentLogs = Lists.newLinkedList();
                List<String> lore = Lists.newArrayList("", CC.GreenB + "Recent Logs " + CC.Green + "(" + CC.GreenB + recentLogs.size() + CC.Green + ")");
                int added = 0;
                Iterator backwars = recentLogs.descendingIterator();
                int logsPerPage = 20;
                while (backwars.hasNext()) {
                    FactionLogs.FactionLog log = (FactionLogs.FactionLog) backwars.next();
                    if (added >= logsPerPage) break;
                    String length = log.getLogLine(type, showTimestamps);
                    lore.add(" " + CC.Yellow + length);
                    ++added;
                }
                int logSize = recentLogs.size();
                int logsLeft = logSize - logsPerPage;
                if (logsLeft > 0) lore.add(CC.YellowB + logsLeft + CC.Yellow + " more logs...");
                lore.add("");
                if (logsLeft > 0) lore.add(CC.Yellow + "Left-Click " + CC.Gray + "to view more logs");
                lore.add(CC.Yellow + "Right-Click " + CC.Gray + "to toggle timestamps");
                setItem(type.getSlot(), (new ClickableItemStack((new ItemBuilder(type.getMaterial())).name(type.getDisplayName()).lore(lore).build())).setClickCallback((click) -> {
                    click.setCancelled(true);
                    if (click.getClick() == ClickType.RIGHT) {
                        showTimestamps = !showTimestamps;
                        drawItems();
                    } else {
                        if (logsLeft <= 0) {
                            player.sendMessage(CC.Red + "No extra logs to load.");
                            return;
                        }
                        Bukkit.getScheduler().scheduleSyncDelayedTask(FactionsPlugin.instance, () -> (new FAuditLogMenu(player, faction, type)).open(player));
                    }
                }));
            }
        }
    }

    static class FAuditLogMenu extends GUIMenu {
        private Player player;
        private Faction faction;
        private FLogType logType;
        private boolean timeStamp = false;

        public FAuditLogMenu(Player player, Faction faction, FLogType type) {
            super("Faction Logs", 9);
            this.player = player;
            this.faction = faction;
            this.logType = type;
        }

        public void drawItems() {
            FactionLogs logs = FactionsPlugin.instance.getFlogManager().getFactionLogMap().get(faction.getId());
            int perPage = logType == FLogType.F_TNT ? 25 : 20;
            if (logs != null) {
                LinkedList<FactionLogs.FactionLog> log = logs.getMostRecentLogs().get(logType);
                if (log != null) {
                    int slot = logType == FLogType.F_TNT ? 0 : 3;
                    int pagesToShow = (int) Math.max(1.0D, Math.ceil((double) log.size() / (double) perPage));

                    for (int page = 1; page <= pagesToShow; ++page) {
                        int startIndex = log.size() - (page * perPage - perPage);
                        if (startIndex >= log.size()) {
                            startIndex = log.size() - 1;
                        }

                        List<String> lore = Lists.newArrayList("", CC.GreenB + "Logs");

                        for (int i = startIndex; i > startIndex - perPage; --i) {
                            if (i < log.size()) {
                                if (i < 0) {
                                    break;
                                }
                                FactionLogs.FactionLog l = log.get(i);
                                lore.add(" " + CC.Yellow + l.getLogLine(logType, timeStamp));
                            }
                        }
                        lore.add("");
                        lore.add(CC.Gray + "Click to toggle timestamp");
                        setItem(slot++, (new ClickableItemStack((new ItemBuilder(Material.PAPER)).name(CC.GreenB + "Log #" + page).lore(lore).build())).setClickCallback((e) -> {
                            e.setCancelled(true);
                            timeStamp = !timeStamp;
                            drawItems();
                        }));
                    }
                }
            }
            setItem(getSize() - 1, (new ClickableItemStack((new ItemBuilder(Material.ARROW)).name(CC.Green + "Previous Page").lore("", CC.Gray + "Click to view previous page!").build())).setClickCallback((event) -> {
                event.setCancelled(true);
                Bukkit.getScheduler().scheduleSyncDelayedTask(FactionsPlugin.instance, () -> (new FAuditMenu(player, faction)).open(player));
            }));
        }
    }
}
