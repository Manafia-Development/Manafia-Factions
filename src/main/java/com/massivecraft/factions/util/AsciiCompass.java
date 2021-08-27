package com.massivecraft.factions.util;

import com.massivecraft.factions.zcore.util.TL;
import org.bukkit.ChatColor;

import java.util.ArrayList;

public class AsciiCompass {

    public static Point getCompassPointForDirection(double inDegrees) {
        double degrees = (inDegrees - 180) % 360;
        if (degrees < 0)
            degrees += 360;
        degrees += 22.5;
        // we can't do thing down there with a single switch statement in which
        // we compare the values. I'll create a while loop and decrease 45
        // each time it's possible and increment the value i. therefore I'll be able
        // to switch for i.
        int i = 0;
        while (degrees - 45 >= 0) {
            i++;
            degrees -= 45;
        }
        switch (i) {
            case 1:
                return Point.NE;
            case 2:
                return Point.E;
            case 3:
                return Point.SE;
            case 4:
                return Point.S;
            case 5:
                return Point.SW;
            case 6:
                return Point.W;
            case 7:
                return Point.NW;
            default:
                return Point.N;
        }
    }

    public static ArrayList<String> getAsciiCompass(Point point, ChatColor colorActive, String colorDefault) {
        ArrayList<String> ret = new ArrayList<>();
        String row;

        row = "";
        row += Point.NW.toString(Point.NW == point, colorActive, colorDefault);
        row += Point.N.toString(Point.N == point, colorActive, colorDefault);
        row += Point.NE.toString(Point.NE == point, colorActive, colorDefault);
        ret.add(row);

        row = "";
        row += Point.W.toString(Point.W == point, colorActive, colorDefault);
        row += colorDefault + "+";
        row += Point.E.toString(Point.E == point, colorActive, colorDefault);
        ret.add(row);

        row = "";
        row += Point.SW.toString(Point.SW == point, colorActive, colorDefault);
        row += Point.S.toString(Point.S == point, colorActive, colorDefault);
        row += Point.SE.toString(Point.SE == point, colorActive, colorDefault);
        ret.add(row);

        return ret;
    }

    public static ArrayList<String> getAsciiCompass(double inDegrees, ChatColor colorActive, String colorDefault) {
        return getAsciiCompass(getCompassPointForDirection(inDegrees), colorActive, colorDefault);
    }

    public enum Point {

        N('N'),
        NE('/'),
        E('E'),
        SE('\\'),
        S('S'),
        SW('/'),
        W('W'),
        NW('\\');

        public final char asciiChar;

        Point(final char asciiChar) {
            this.asciiChar = asciiChar;
        }

        @Override
        public String toString() {
            return String.valueOf(this.asciiChar);
        }

        public String getTranslation() {
            switch (this) {
                case N:
                    return TL.COMPASS_SHORT_NORTH.toString();
                case E:
                    return TL.COMPASS_SHORT_EAST.toString();
                case S:
                    return TL.COMPASS_SHORT_SOUTH.toString();
                case W:
                    return TL.COMPASS_SHORT_WEST.toString();
                default:
                    return toString();
            }
        }

        public String toString(boolean isActive, ChatColor colorActive, String colorDefault) {
            return (isActive ? colorActive : colorDefault) + getTranslation();
        }
    }
}
