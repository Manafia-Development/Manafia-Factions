package com.github.manafia.factions.cmd.tnt.tntprovider;

public interface TNTProvider {
    int getTnt();

    void sendMessage(String message);

    void takeTnt(int amount);

    boolean isAvailable();
}

