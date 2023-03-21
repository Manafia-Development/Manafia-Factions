package com.github.manafia.factions.cmd;

import com.github.manafia.factions.Board;
import com.github.manafia.factions.Conf;
import com.github.manafia.factions.FLocation;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.zcore.util.TL;


public class CmdMap extends FCommand {

    /**
     * @author FactionsUUID Team - Modified By CmdrKittens
     */

    public CmdMap() {
        super();
        this.aliases.addAll(Aliases.map_map);
        this.optionalArgs.put("on/off", "once");

        this.requirements = new CommandRequirements.Builder(Permission.MAP)
                .playerOnly()
                .build();
    }

    @Override
    public void perform(CommandContext context) {
        if (context.argIsSet(0)) {
            if (context.argAsBool(0, !context.fPlayer.isMapAutoUpdating())) {
                // Turn on

                // if economy is enabled, they're not on the bypass list, and this command has a cost set, make 'em pay
                if (!context.payForCommand(Conf.econCostMap, "to show the map", "for showing the map")) {
                    return;
                }

                context.fPlayer.setMapAutoUpdating(true);
                context.msg(TL.COMMAND_MAP_UPDATE_ENABLED);

                // And show the map once
                showMap(context);
            } else {
                // Turn off
                context.fPlayer.setMapAutoUpdating(false);
                context.msg(TL.COMMAND_MAP_UPDATE_DISABLED);

            }
        } else {
            // if economy is enabled, they're not on the bypass list, and this command has a cost set, make 'em pay
            if (!context.payForCommand(Conf.econCostMap, TL.COMMAND_MAP_TOSHOW, TL.COMMAND_MAP_FORSHOW)) {
                return;
            }

            showMap(context);
        }
    }

    public void showMap(CommandContext context) {
        context.sendFancyMessage(Board.getInstance().getMap(context.fPlayer, new FLocation(context.fPlayer), context.player.getLocation().getYaw()));
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_MAP_DESCRIPTION;
    }

}