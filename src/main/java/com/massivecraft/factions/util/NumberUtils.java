package com.massivecraft.factions.util;

public class NumberUtils {

    public static boolean isNumber(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");

    }
}
