package com.github.manafia.factions.cmd.claim;

import com.github.manafia.factions.Board;
import com.github.manafia.factions.FLocation;
import com.github.manafia.factions.FPlayer;
import com.github.manafia.factions.FactionsPlugin;
import com.github.manafia.factions.cmd.Aliases;
import com.github.manafia.factions.cmd.CommandContext;
import com.github.manafia.factions.cmd.CommandRequirements;
import com.github.manafia.factions.cmd.FCommand;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.util.CC;
import com.github.manafia.factions.zcore.util.TL;
import mkremins.fanciful.FancyMessage;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.ArrayList;

public class CmdCornerList extends FCommand {
    public CmdCornerList() {
        super();
        this.aliases.addAll(Aliases.corner_list);
        this.optionalArgs.put("world", "name");

        this.requirements = new CommandRequirements.Builder(Permission.CORNER_LIST)
                .playerOnly()
                .build();
    }

    @Override
    public void perform(CommandContext context) {
        if(context.args.size() == 0) {
            //send player location world corners
            handleCornerList(context.fPlayer, context.player.getWorld());
        } else if(context.args.size() == 1) {
            World world = Bukkit.getWorld(context.args.get(0));
            if(world == null) {
                context.msg(TL.INVALID_WORLD.toString().replace("{world}", context.args.get(0)));
                return;
            }

            handleCornerList(context.fPlayer, world);

        }

    }

    public void handleCornerList(FPlayer fme, World world) {
        ArrayList<FancyMessage> ret = new ArrayList<>();
        ret.add(new FancyMessage(FactionsPlugin.getInstance().txt.titleize(TL.COMMAND_CORNERLIST_TITLE.toString().replace("{world}", world.getName()))));

        for(FLocation fLocation : FactionsPlugin.getInstance().getFactionsPlayerListener().getCorners()) {
            if(fLocation.getWorld() == world) {
                ret.add(new FancyMessage(CC.translate("&2Faction At &e" + fLocation.getX() + ", &e" + fLocation.getZ() + ": &r" + Board.getInstance().getFactionAt(fLocation).getTag())));
            }
        }
        fme.sendFancyMessage(ret);
    }

    @Override
    public TL getUsageTranslation() {
        return null;
    }
}
