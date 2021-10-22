package com.github.manafia.factions.util.timer;

import com.github.manafia.factions.Conf;
import org.apache.commons.lang.time.FastDateFormat;

import java.text.DecimalFormat;
import java.time.ZoneId;
import java.util.Locale;
import java.util.TimeZone;


public final class DateTimeFormats {
    public static final TimeZone SERVER_TIME_ZONE = TimeZone.getTimeZone(Conf.serverTimeZone);
    public static final ZoneId SERVER_ZONE_ID = SERVER_TIME_ZONE.toZoneId();
    public static final FastDateFormat DAY_MTH_HR_MIN_SECS = FastDateFormat.getInstance("dd/MM HH:mm:ss", SERVER_TIME_ZONE, Locale.ENGLISH);
    public static final FastDateFormat HR_MIN_AMPM = FastDateFormat.getInstance("hh:mma", SERVER_TIME_ZONE, Locale.ENGLISH);
    public static final FastDateFormat HR_MIN = FastDateFormat.getInstance("hh:mm", SERVER_TIME_ZONE, Locale.ENGLISH);
    public static final FastDateFormat MIN_SECS = FastDateFormat.getInstance("mm:ss", SERVER_TIME_ZONE, Locale.ENGLISH);
    public static final ThreadLocal<DecimalFormat> REMAINING_SECONDS = ThreadLocal.withInitial(() -> new DecimalFormat("0.#"));
    public static final ThreadLocal<DecimalFormat> REMAINING_SECONDS_TRAILING = ThreadLocal.withInitial(() -> new DecimalFormat("0.0"));
}
