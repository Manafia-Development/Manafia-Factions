package com.github.manafia.factions.util;

public class Placeholder {

    private final String tag;
    private final String replace;

    public Placeholder(String tag, String replace) {
        this.tag = tag;
        this.replace = replace;
    }

    public String getReplace() {
        return replace;
    }

    public String getTag() {
        return tag;
    }


}
