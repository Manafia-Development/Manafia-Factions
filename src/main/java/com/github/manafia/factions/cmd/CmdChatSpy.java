package com.github.manafia.factions.cmd;

import com.github.manafia.factions.FactionsPlugin;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.zcore.util.TL;

public class CmdChatSpy extends FCommand {

    /**
     * @author FactionsUUID Team - Modified By CmdrKittens
     */

    public CmdChatSpy() {
        super();
        this.aliases.addAll(Aliases.chatspy);

        this.optionalArgs.put("on/off", "flip");

        this.requirements = new CommandRequirements.Builder(Permission.CHATSPY)
                .playerOnly()
                .build();
    }

    @Override
    public void perform(CommandContext context) {
        context.fPlayer.setSpyingChat(context.argAsBool(0, !context.fPlayer.isSpyingChat()));

        if (context.fPlayer.isSpyingChat()) {
            context.msg(TL.COMMAND_CHATSPY_ENABLE);
            FactionsPlugin.getInstance().log(context.fPlayer.getName() + TL.COMMAND_CHATSPY_ENABLELOG);
        } else {
            context.msg(TL.COMMAND_CHATSPY_DISABLE);
            FactionsPlugin.getInstance().log(context.fPlayer.getName() + TL.COMMAND_CHATSPY_DISABLELOG);
        }
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_CHATSPY_DESCRIPTION;
    }
}