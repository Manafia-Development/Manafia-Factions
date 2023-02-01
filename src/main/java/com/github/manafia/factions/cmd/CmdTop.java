package com.github.manafia.factions.cmd;

import com.github.manafia.factions.FPlayer;
import com.github.manafia.factions.Faction;
import com.github.manafia.factions.Factions;
import com.github.manafia.factions.integration.Econ;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.zcore.util.TL;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CmdTop extends FCommand {

    /**
     * @author FactionsUUID Team - Modified By CmdrKittens
     */

    public CmdTop() {
        super();
        this.aliases.addAll(Aliases.top);
        this.requiredArgs.add("criteria");
        this.optionalArgs.put("page", "1");

        this.requirements = new CommandRequirements.Builder(Permission.TOP)
                .build();
    }


    @Override
    public void perform(CommandContext context) {
        // Can sort by: money, members, online, allies, enemies, power, land.
        // Get all Factions and remove non player ones.
        ArrayList<Faction> factionList = Factions.getInstance().getAllFactions();
        factionList.remove(Factions.getInstance().getWilderness());
        factionList.remove(Factions.getInstance().getSafeZone());
        factionList.remove(Factions.getInstance().getWarZone());

        String criteria = context.argAsString(0);

        // TODO: Better way to sort?
        if (criteria.equalsIgnoreCase("members")) {
            factionList.sort((f1, f2) -> {
                int f1Size = f1.getFPlayers().size();
                int f2Size = f2.getFPlayers().size();
                if (f1Size < f2Size) {
                    return 1;
                } else if (f1Size > f2Size) {
                    return -1;
                }
                return 0;
            });
        } else if (criteria.equalsIgnoreCase("start")) {
            factionList.sort((f1, f2) -> {
                long f1start = f1.getFoundedDate();
                long f2start = f2.getFoundedDate();
                // flip signs because a smaller date is farther in the past
                if (f1start > f2start) {
                    return 1;
                } else if (f1start < f2start) {
                    return -1;
                }
                return 0;
            });
        } else if (criteria.equalsIgnoreCase("power")) {
            factionList.sort((f1, f2) -> {
                int f1Size = f1.getPowerRounded();
                int f2Size = f2.getPowerRounded();
                if (f1Size < f2Size) {
                    return 1;
                } else if (f1Size > f2Size) {
                    return -1;
                }
                return 0;
            });
        } else if (criteria.equalsIgnoreCase("land")) {
            factionList.sort((f1, f2) -> {
                int f1Size = f1.getLandRounded();
                int f2Size = f2.getLandRounded();
                if (f1Size < f2Size) {
                    return 1;
                } else if (f1Size > f2Size) {
                    return -1;
                }
                return 0;
            });
        } else if (criteria.equalsIgnoreCase("online")) {
            factionList.sort((f1, f2) -> {
                int f1Size = f1.getFPlayersWhereOnline(true).size();
                int f2Size = f2.getFPlayersWhereOnline(true).size();
                if (f1Size < f2Size) {
                    return 1;
                } else if (f1Size > f2Size) {
                    return -1;
                }
                return 0;
            });
        } else if (criteria.equalsIgnoreCase("money") || criteria.equalsIgnoreCase("balance") || criteria.equalsIgnoreCase("bal")) {
            factionList.sort((f1, f2) -> {
                double f1Size = f1.getFactionBalance();
                // Lets get the balance of /all/ the players in the Faction.
                for (FPlayer fp : f1.getFPlayers()) {
                    f1Size = f1Size + Econ.getBalance(fp.getAccountId());
                }
                double f2Size = f2.getFactionBalance();
                for (FPlayer fp : f2.getFPlayers()) {
                    f2Size = f2Size + Econ.getBalance(fp.getAccountId());
                }
                if (f1Size < f2Size) {
                    return 1;
                } else if (f1Size > f2Size) {
                    return -1;
                }
                return 0;
            });
        } else {
            context.msg(TL.COMMAND_TOP_INVALID, criteria);
        }

        ArrayList<String> lines = new ArrayList<>();

        final int pageheight = 9;
        int pagenumber = context.argAsInt(1, 1);
        int pagecount = (factionList.size() / pageheight) + 1;
        if (pagenumber > pagecount) {
            pagenumber = pagecount;
        } else if (pagenumber < 1) {
            pagenumber = 1;
        }
        int start = (pagenumber - 1) * pageheight;
        int end = start + pageheight;
        if (end > factionList.size()) {
            end = factionList.size();
        }

        lines.add(TL.COMMAND_TOP_TOP.format(criteria.toUpperCase(), pagenumber, pagecount));

        int rank = 1;
        for (Faction faction : factionList.subList(start, end)) {
            // Get the relation color if player is executing this.
            String fac = context.sender instanceof Player ? faction.getRelationTo(context.fPlayer).getColor() + faction.getTag() : faction.getTag();
            lines.add(TL.COMMAND_TOP_LINE.format(rank, fac, getValue(faction, criteria)));
            rank++;
        }

        context.sendMessage(lines);
    }

    private String getValue(Faction faction, String criteria) {
        if (criteria.equalsIgnoreCase("online")) {
            return String.valueOf(faction.getFPlayersWhereOnline(true).size());
        } else if (criteria.equalsIgnoreCase("start")) {
            return TL.sdf.format(faction.getFoundedDate());
        } else if (criteria.equalsIgnoreCase("members")) {
            return String.valueOf(faction.getFPlayers().size());
        } else if (criteria.equalsIgnoreCase("land")) {
            return String.valueOf(faction.getLandRounded());
        } else if (criteria.equalsIgnoreCase("power")) {
            return String.valueOf(faction.getPowerRounded());
        } else { // Last one is balance, and it has 3 different things it could be.
            double balance = faction.getFactionBalance();
            for (FPlayer fp : faction.getFPlayers()) {
                balance = Math.round(balance + Econ.getBalance(fp.getAccountId()));
            }
            return String.valueOf(balance);
        }
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_TOP_DESCRIPTION;
    }
}
