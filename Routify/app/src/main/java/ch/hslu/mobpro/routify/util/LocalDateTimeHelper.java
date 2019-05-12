package ch.hslu.mobpro.routify.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

public class LocalDateTimeHelper {
    static public LocalDateTime getLocalDateTime(Long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp*1000),
                TimeZone.getDefault().toZoneId());
    }
}
