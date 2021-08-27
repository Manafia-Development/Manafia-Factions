package com.massivecraft.factions.struct;


public class Placeholder {

    /**
     * @author Illyria Team
     */


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
