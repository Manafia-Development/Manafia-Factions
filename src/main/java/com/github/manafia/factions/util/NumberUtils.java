package com.github.manafia.factions.util;

public class NumberUtils {

    public static boolean isNumber(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");

    }
}
