package com.github.manafia.factions.cmd;

import com.mojang.brigadier.builder.ArgumentBuilder;

public interface BrigadierProvider {

    /**
     * @author FactionsUUID Team - Modified By CmdrKittens
     */

    ArgumentBuilder<Object, ?> get(ArgumentBuilder<Object, ?> parent);

}
