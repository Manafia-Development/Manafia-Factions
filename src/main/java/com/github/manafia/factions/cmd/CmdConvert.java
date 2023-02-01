package com.github.manafia.factions.cmd;

import com.github.manafia.factions.Conf;
import com.github.manafia.factions.Conf.Backend;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.zcore.persist.json.FactionsJSON;
import com.github.manafia.factions.zcore.util.TL;
import org.bukkit.command.ConsoleCommandSender;

public class CmdConvert extends FCommand {

    /**
     * @author FactionsUUID Team - Modified By CmdrKittens
     */

    public CmdConvert() {
        this.aliases.addAll(Aliases.convert);
        this.requiredArgs.add("[MYSQL|JSON]");

        this.requirements = new CommandRequirements.Builder(Permission.CONVERT)
                .build();
    }

    @Override
    public void perform(CommandContext context) {
        if (!(context.sender instanceof ConsoleCommandSender)) {
            context.sender.sendMessage(TL.GENERIC_CONSOLEONLY.toString());
        }
        Backend nb = Backend.valueOf(context.argAsString(0).toUpperCase());
        if (nb == Conf.backEnd) {
            context.sender.sendMessage(TL.COMMAND_CONVERT_BACKEND_RUNNING.toString());
            return;
        }
        if (nb == Backend.JSON) {
            FactionsJSON.convertTo();
        } else {
            context.sender.sendMessage(TL.COMMAND_CONVERT_BACKEND_INVALID.toString());
            return;
        }
        Conf.backEnd = nb;
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_CONVERT_DESCRIPTION;
    }

}