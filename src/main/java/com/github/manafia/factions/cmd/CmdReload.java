package com.github.manafia.factions.cmd;

import com.github.manafia.factions.Conf;
import com.github.manafia.factions.FactionsPlugin;
import com.github.manafia.factions.listeners.FactionsPlayerListener;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.zcore.util.LangUtil;
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
        FactionsPlugin.getInstance().getFileManager().getShop().loadFile();
        FactionsPlugin.getInstance().getFileManager().getRaids().loadFile();
        FactionsPlugin.getInstance().getFileManager().getLunar().loadFile();
        FactionsPlugin.getInstance().getFileManager().getPermissions().loadFile();
        FactionsPlugin.getInstance().getFileManager().getBadlion().loadFile();
        FactionsPlugin.getInstance().reloadConfig();
        FactionsPlugin.langMap = LangUtil.getLangMap();

        if (FactionsPlugin.getInstance().version != 7)
            FactionsPlayerListener.loadCorners();

        FCmdRoot.instance.addVariableCommands();
        FCmdRoot.instance.rebuild();
        long timeReload = (System.currentTimeMillis() - timeInitStart);

        context.msg(TL.COMMAND_RELOAD_TIME, timeReload);
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_RELOAD_DESCRIPTION;
    }
}