package com.cactus.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * periodcactus
 * Class: DateUtil
 * Created by hapo on 2019-12-02.
 * Description:
 */
public class DateUtil {

    public static LocalDateTime toLocalDateTime(Calendar calendar) {
        if (calendar == null) {
            return null;
        }
        TimeZone tz = calendar.getTimeZone();
        ZoneId zid = tz == null ? ZoneId.systemDefault() : tz.toZoneId();
        return LocalDateTime.ofInstant(calendar.toInstant(), zid);
    }

    public static LocalDateTime getNextDate(LocalDateTime date, Long freq, String cycle)
    {
        switch(cycle) {
            case 	"Y" : date = date.plusYears(freq); 		break;
            case	"M" : date = date.plusMonths(freq); 		break;
            case 	"W" : date = date.plusWeeks(freq); 	break;
            case 	"D" : date = date.plusDays(freq); 		break;
            case 	"H" : date = date.plusHours(freq); 		break;
            case   "MM" : date = date.plusMinutes(freq); 		break;
            default 	: break;
        }

        return date;
    }
}
