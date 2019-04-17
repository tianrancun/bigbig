package com.bigbig.util;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * author : vn0k7xd
 */

@Component
@Slf4j
public class DateTimeUtil {

    public LocalDateTime currentUtcTime() {
        return LocalDateTime.now().atOffset(ZoneOffset.UTC).toLocalDateTime();
    }

    public static LocalDateTime getCurrentDateTimeInUTC() {
        LocalDateTime localDateTimeInUTC = ZonedDateTime.ofInstant(ZonedDateTime.now().toInstant(), ZoneId.of("UTC")).toLocalDateTime().withNano(0);

        log.debug("getCurrentDateTimeInUTC {} ", localDateTimeInUTC);
        return localDateTimeInUTC;
    }

    public LocalDate getCurrentDateInUTC() {
        LocalDate localDateInUTC = ZonedDateTime.ofInstant(ZonedDateTime.now().toInstant(), ZoneId.of("UTC")).toLocalDate();

        log.debug("getCurrentDateInUTC {} ", localDateInUTC);
        return localDateInUTC;
    }

    public LocalDate convertUtilDatetoDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public LocalDateTime getTimeInTimezone(Entry<String, String> timeZone) {
        /*
         * EST:'America/New_York' // 7-19 CST:'America/Chicago' // 8-19
         * MST:'America/Denver' // 9-21 PST:'America/Los_Angeles'
         * AST:'America/Anchorage' HST:'America/Adak' // 10-22
         */
        return timeZone.getKey().matches("AST|HST|PST") ? LocalDateTime.now(ZoneId.of("America/Los_Angeles"))
                : LocalDateTime.now(ZoneId.of(timeZone.getValue()));
    }

    public static LocalDate DateToLocaleDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDate();

     }
}
