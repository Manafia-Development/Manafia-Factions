package com.github.manafia.factions.cmd;

import com.github.manafia.factions.FPlayer;
import com.github.manafia.factions.FPlayers;
import com.github.manafia.factions.FactionsPlugin;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.zcore.util.TL;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class CmdNear extends FCommand {

    /**
     * @author FactionsUUID Team - Modified By CmdrKittens
     */

    public CmdNear () {
        super();
        this.aliases.addAll(Aliases.near);

        this.requirements = new CommandRequirements.Builder(Permission.NEAR)
                .playerOnly()
                .memberOnly()
                .build();
    }

    @Override
    public void perform (CommandContext context) {
        if (!FactionsPlugin.getInstance().getConfig().getBoolean("fnear.Enabled")) {
            context.msg(TL.COMMAND_NEAR_DISABLED_MSG);
            return;
        }

        double range = FactionsPlugin.getInstance().getConfig().getInt("fnear.Radius");
        String format = TL.COMMAND_NEAR_FORMAT.toString();
        context.msg(TL.COMMAND_NEAR_USE_MSG);
        for (Entity e : context.player.getNearbyEntities(range, 255, range))
            if (e instanceof Player) {
                Player player = (((Player) e).getPlayer());
                FPlayer fplayer = FPlayers.getInstance().getByPlayer(player);
                if (context.faction == fplayer.getFaction()) {
                    double distance = context.player.getLocation().distance(player.getLocation());
                    context.sendMessage(format.replace("{playername}", player.getDisplayName()).replace("{distance}", (int) distance + ""));
                }
            }
    }

    @Override
    public TL getUsageTranslation () {
        return TL.COMMAND_NEAR_DESCRIPTION;
    }
}
