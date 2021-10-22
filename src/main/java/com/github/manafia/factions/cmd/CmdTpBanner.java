package com.github.manafia.factions.cmd;

import com.github.manafia.factions.FactionsPlugin;
import com.github.manafia.factions.listeners.FactionsBlockListener;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.util.WarmUpUtil;
import com.github.manafia.factions.zcore.util.TL;

public class CmdTpBanner extends FCommand {

    /**
     * @author Illyria Team
     */

    public CmdTpBanner() {
        super();
        this.aliases.addAll(Aliases.tpBanner);

        this.requirements = new CommandRequirements.Builder(Permission.TPBANNER)
                .playerOnly()
                .memberOnly()
                .build();
    }

    @Override
    public void perform(CommandContext context) {
        if (!FactionsPlugin.getInstance().getConfig().getBoolean("fbanners.Enabled"))
            return;

        if (FactionsBlockListener.bannerLocations.containsKey(context.fPlayer.getTag())) {
            context.msg(TL.COMMAND_TPBANNER_SUCCESS);
            context.doWarmUp(WarmUpUtil.Warmup.BANNER, TL.WARMUPS_NOTIFY_TELEPORT, "Banner", () -> context.player.teleport(FactionsBlockListener.bannerLocations.get(context.fPlayer.getTag())), FactionsPlugin.getInstance().getConfig().getLong("warmups.f-banner", 10));
        } else
            context.msg(TL.COMMAND_TPBANNER_NOTSET);

    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_TPBANNER_DESCRIPTION;
    }
}
