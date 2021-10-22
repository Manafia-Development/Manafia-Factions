package com.github.manafia.factions.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/*
 * Made by Sothatsit for Spigot
 *
 * Thread: https://www.spigotmc.org/threads/194248/
 */
public class TimeUtil {

    private static final long TICK_MS = 50;
    private static final long SECOND_MS = 1000;
    private static final long MINUTE_MS = SECOND_MS * 60;
    private static final long HOUR_MS = MINUTE_MS * 60;
    private static final long DAY_MS = HOUR_MS * 24;
    private static final long YEAR_MS = DAY_MS * 365;

    private static final Map<String, Long> unitMultipliers = new HashMap<>();

    static {
        addTimeMultiplier(1, "ms", "milli", "millis", "millisecond", "milliseconds");
        addTimeMultiplier(TICK_MS, "t", "tick", "ticks");
        addTimeMultiplier(SECOND_MS, "s", "sec", "secs", "second", "seconds");
        addTimeMultiplier(MINUTE_MS, "m", "min", "mins", "minute", "minutes");
        addTimeMultiplier(HOUR_MS, "h", "hour", "hours");
        addTimeMultiplier(DAY_MS, "d", "day", "days");
        addTimeMultiplier(YEAR_MS, "y", "year", "years");
    }

    private final long milliseconds;

    public TimeUtil(long time, TimeUnit timeUnit) {
        this(TimeUnit.MILLISECONDS.convert(time, timeUnit));
    }

    private TimeUtil(long milliseconds) {
        if (milliseconds < 0) {
            throw new IllegalArgumentException("Number of milliseconds cannot be less than 0");
        }

        this.milliseconds = milliseconds;
    }

    private static void addTimeMultiplier(long multiplier, String... keys) {
        for (String key : keys) {
            unitMultipliers.put(key, multiplier);
        }
    }

    public static TimeUtil parseString(String timeString) throws TimeParseException {
        if (timeString == null) {
            throw new IllegalArgumentException("timeString cannot be null");
        }

        if (timeString.isEmpty()) {
            throw new TimeParseException("Empty time string");
        }

        long totalMilliseconds = 0;

        boolean readingNumber = true;

        StringBuilder number = new StringBuilder();
        StringBuilder unit = new StringBuilder();

        for (char c : timeString.toCharArray()) {
            if (c == ' ' || c == ',') {
                readingNumber = false;
                continue;
            }

            if (c == '.' || (c >= '0' && c <= '9')) {
                if (!readingNumber) {
                    totalMilliseconds += parseTimeComponent(number.toString(), unit.toString());

                    number.setLength(0);
                    unit.setLength(0);

                    readingNumber = true;
                }

                number.append(c);
            } else {
                readingNumber = false;
                unit.append(c);
            }
        }

        if (readingNumber) {
            throw new TimeParseException("Number \"" + number + "\" not matched with unit at end of string");
        } else {
            totalMilliseconds += parseTimeComponent(number.toString(), unit.toString());
        }

        return new TimeUtil(totalMilliseconds);
    }

    private static double parseTimeComponent(String magnitudeString, String unit) throws TimeParseException {
        if (magnitudeString.isEmpty()) {
            throw new TimeParseException("Missing number for unit \"" + unit + "\"");
        }

        long magnitude;

        try {
            magnitude = Long.valueOf(magnitudeString);
        } catch (NumberFormatException e) {
            throw new TimeParseException("Unable to parse number \"" + magnitudeString + "\"", e);
        }

        unit = unit.toLowerCase();

        if (unit.length() > 3 && unit.endsWith("and")) {
            unit = unit.substring(0, unit.length() - 3);
        }

        Long unitMultiplier = unitMultipliers.get(unit);

        if (unitMultiplier == null) {
            throw new TimeParseException("Unknown time unit \"" + unit + "\"");
        }

        return magnitude * unitMultiplier;
    }

    public long toMilliseconds() {
        return this.milliseconds;
    }

    public double toTicks() {
        return this.milliseconds / (double) TICK_MS;
    }

    public double toSeconds() {
        return this.milliseconds / (double) SECOND_MS;
    }

    public double toMinutes() {
        return this.milliseconds / (double) MINUTE_MS;
    }

    public double toHours() {
        return this.milliseconds / (double) HOUR_MS;
    }

    public double toDays() {
        return this.milliseconds / (double) DAY_MS;
    }

    public double toYears() {
        return this.milliseconds / (double) YEAR_MS;
    }

    @Override
    public String toString() {
        StringBuilder timeString = new StringBuilder();

        long time = this.milliseconds;

        time = this.appendTime(time, YEAR_MS, "years", timeString);
        time = this.appendTime(time, DAY_MS, "days", timeString);
        time = this.appendTime(time, HOUR_MS, "hours", timeString);
        time = this.appendTime(time, MINUTE_MS, "minutes", timeString);
        time = this.appendTime(time, SECOND_MS, "seconds", timeString);

        if (time != 0) {
            timeString.append(", ").append(time).append(" ms");
        }

        if (timeString.length() == 0) {
            return "0 seconds";
        }

        return timeString.substring(2);
    }

    private long appendTime(long time, long unitInMS, String name, StringBuilder builder) {
        long timeInUnits = (time - (time % unitInMS)) / unitInMS;

        if (timeInUnits > 0) {
            builder.append(", ").append(timeInUnits).append(' ').append(name);
        }

        return time - timeInUnits * unitInMS;
    }

    @SuppressWarnings("serial")
    public static class TimeParseException extends RuntimeException {

        public TimeParseException(String reason) {
            super(reason);
        }

        public TimeParseException(String reason, Throwable cause) {
            super(reason, cause);
        }

    }

}