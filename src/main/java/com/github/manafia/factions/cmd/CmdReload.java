package com.github.manafia.factions.cmd;

import com.github.manafia.factions.Conf;
import com.github.manafia.factions.FactionsPlugin;
import com.github.manafia.factions.discord.Discord;
import com.github.manafia.factions.listeners.FactionsPlayerListener;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.zcore.util.TL;

public class CmdReload extends FCommand {

    /**
     * @author FactionsUUID Team - Modified By CmdrKittens
     */

    public CmdReload() {
        super();
        this.aliases.addAll(Aliases.reload);

        this.requirements = new CommandRequirements.Builder(Permission.RELOAD).build();
    }

    @Override
    public void perform(CommandContext context) {
        long timeInitStart = System.currentTimeMillis();
        Conf.load();
        Conf.save();
        FactionsPlugin.getInstance().getFileManager().loadCustomFiles();
        FactionsPlugin.getInstance().reloadConfig();
        FactionsPlugin.getInstance().loadLang();


        if (FactionsPlugin.getInstance().version != 7) {
            FactionsPlayerListener.loadCorners();
        }

        Discord.setupDiscord();
        //Recheck if commands should truly be disabled and rebuild.
        FCmdRoot.instance.addVariableCommands();
        FCmdRoot.instance.rebuild();
        long timeReload = (System.currentTimeMillis() - timeInitStart);

        context.msg(TL.COMMAND_RELOAD_TIME, timeReload);
        context.msg(TL.COMMAND_RELOAD_NOTICE);
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_RELOAD_DESCRIPTION;
    }
}