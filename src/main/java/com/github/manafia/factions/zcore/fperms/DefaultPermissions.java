package com.github.manafia.factions.zcore.fperms;

public class DefaultPermissions {

    /**
     * @author Illyria Team
     */

    public boolean ban;
    public boolean build;
    public boolean destroy;
    public boolean frostwalk;
    public boolean painbuild;
    public boolean door;
    public boolean button;
    public boolean lever;
    public boolean container;
    public boolean invite;
    public boolean kick;
    public boolean items;
    public boolean sethome;
    public boolean territory;
    public boolean home;
    public boolean disband;
    public boolean promote;
    public boolean setwarp;
    public boolean warp;
    public boolean fly;
    public boolean vault;
    public boolean tntbank;
    public boolean tntfill;
    public boolean withdraw;
    public boolean chest;
    public boolean audit;
    public boolean check;
    public boolean drain;
    public boolean spawner;

    public DefaultPermissions() {
    }

    public DefaultPermissions(boolean def) {
        this.ban = def;
        this.build = def;
        this.destroy = def;
        this.frostwalk = def;
        this.painbuild = def;
        this.door = def;
        this.button = def;
        this.lever = def;
        this.container = def;
        this.invite = def;
        this.kick = def;
        this.items = def;
        this.sethome = def;
        this.territory = def;
        this.home = def;
        this.disband = def;
        this.promote = def;
        this.setwarp = def;
        this.warp = def;
        this.fly = def;
        this.vault = def;
        this.tntbank = def;
        this.tntfill = def;
        this.withdraw = def;
        this.chest = def;
        this.audit = def;
        this.check = def;
        this.drain = def;
        this.spawner = def;
    }

    public DefaultPermissions(boolean canBan,
                              boolean canBuild,
                              boolean canDestory,
                              boolean canFrostwalk,
                              boolean canPainbuild,
                              boolean canDoor,
                              boolean canButton,
                              boolean canLever,
                              boolean canContainer,
                              boolean canInvite,
                              boolean canKick,
                              boolean canItems,
                              boolean canSethome,
                              boolean canTerritory,
                              boolean canAudit,
                              boolean canHome,
                              boolean canDisband,
                              boolean canPromote,
                              boolean canSetwarp,
                              boolean canWarp,
                              boolean canFly,
                              boolean canVault,
                              boolean canTntbank,
                              boolean canTntfill,
                              boolean canWithdraw,
                              boolean canChest,
                              boolean canCheck,
                              boolean canDrain,
                              boolean canSpawners) {
        this.ban = canBan;
        this.build = canBuild;
        this.destroy = canDestory;
        this.frostwalk = canFrostwalk;
        this.painbuild = canPainbuild;
        this.door = canDoor;
        this.button = canButton;
        this.lever = canLever;
        this.container = canContainer;
        this.invite = canInvite;
        this.kick = canKick;
        this.items = canItems;
        this.sethome = canSethome;
        this.territory = canTerritory;
        this.home = canHome;
        this.disband = canDisband;
        this.promote = canPromote;
        this.setwarp = canSetwarp;
        this.warp = canWarp;
        this.fly = canFly;
        this.vault = canVault;
        this.tntbank = canTntbank;
        this.tntfill = canTntfill;
        this.withdraw = canWithdraw;
        this.chest = canChest;
        this.audit = canAudit;
        this.check = canCheck;
        this.drain = canDrain;
        this.spawner = canSpawners;
    }

    @Deprecated
    public boolean getbyName(String name) {
        switch (name) {
            case "ban":
                return this.ban;
            case "build":
                return this.build;
            case "destroy":
                return this.destroy;
            case "frostwalk":
                return this.frostwalk;
            case "painbuild":
                return this.painbuild;
            case "door":
                return this.door;
            case "button":
                return this.button;
            case "lever":
                return this.lever;
            case "home":
                return this.home;
            case "container":
                return this.container;
            case "invite":
                return this.invite;
            case "kick":
                return this.kick;
            case "items":
                return this.items;
            case "sethome":
                return this.sethome;
            case "territory":
                return this.territory;
            case "disband":
                return this.disband;
            case "promote":
                return this.promote;
            case "setwarp":
                return this.setwarp;
            case "warp":
                return this.warp;
            case "fly":
                return this.fly;
            case "vault":
                return this.vault;
            case "tntbank":
                return this.tntbank;
            case "tntfill":
                return this.tntfill;
            case "withdraw":
                return this.withdraw;
            case "chest":
                return this.chest;
            case "audit":
                return this.audit;
            case "check":
                return this.check;
            case "drain":
                return this.drain;
            case "spawner":
                return this.spawner;
            default:
                return false;
        }
    }
}
