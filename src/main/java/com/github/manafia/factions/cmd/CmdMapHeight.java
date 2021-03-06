package com.github.manafia.factions.cmd;

import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.zcore.util.TL;

public class CmdMapHeight extends FCommand {

    /**
     * @author FactionsUUID Team - Modified By CmdrKittens
     */

    public CmdMapHeight() {
        super();

        this.aliases.addAll(Aliases.map_height);
        this.optionalArgs.put("height", "height");

        this.requirements = new CommandRequirements.Builder(Permission.MAPHEIGHT)
                .playerOnly()
                .build();
    }

    @Override
    public void perform(CommandContext context) {
        if (context.args.size() == 0) {
            context.fPlayer.sendMessage(TL.COMMAND_MAPHEIGHT_CURRENT.format(context.fPlayer.getMapHeight()));
            return;
        }

        int height = context.argAsInt(0);

        context.fPlayer.setMapHeight(height);
        context.msg(TL.COMMAND_MAPHEIGHT_SET.format(context.fPlayer.getMapHeight()));
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_MAPHEIGHT_DESCRIPTION;
    }

}
