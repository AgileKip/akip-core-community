package org.akip.dashboard.util;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class BusinessHoursDurationUtil {

    private BusinessHoursCalendarUtil businessHoursCalendarUtil;

    public BusinessHoursDurationUtil(String processDefinitionId) {
        this.businessHoursCalendarUtil = new BusinessHoursCalendarUtil(processDefinitionId);
    }

    public static Duration meanDuration(List<Duration> durations) {
        if (durations.isEmpty()) {
            return null;
        }
        Duration meanDuration = null;
        for (Duration duration: durations) {
            if (duration == null) {
                continue;
            }
            if (meanDuration == null) {
                meanDuration = duration;
                continue;
            }
            meanDuration = meanDuration.plus(duration);
        }
        return meanDuration.dividedBy(durations.size());
    }

    public static String humanReadableDuration(Duration duration) {
        if (duration == null) {
            return null;
        }
        return duration.truncatedTo(ChronoUnit.SECONDS)
                .toString()
                .substring(2)
                .replaceAll("(\\d[HMS])(?!$)", "$1 ")
                .toLowerCase();
    }

    public static BigDecimal durationInSeconds(Duration duration) {
        if (duration == null) {
            return null;
        }
        return new BigDecimal(duration.toSeconds());
    }

    public Duration businessHourDuration(Instant d1, Instant d2) {
        return businessHourDuration(LocalDateTime.ofInstant(d1, ZoneOffset.UTC), LocalDateTime.ofInstant(d2, ZoneOffset.UTC));
    }

    public Duration businessHourDuration(LocalDateTime d1, LocalDateTime d2) {
        long qtdDays = businessHoursCalendarUtil.daysBetween(d1, d2);

        if (qtdDays == 1) {
            return durationSameDay(d1, d2);
        }

        Duration duration = durationFirstDay(d1);
        duration = duration.plus(durationLastDay(d2));

        if (qtdDays == 2) {
            return duration;
        }

        for (int i = 1; i < qtdDays - 1; i++) {
            duration = duration.plus(durationFullDay(d1.plusDays(i)));
        }

        return duration;
    }



    private Duration durationSameDay(LocalDateTime dt1, LocalDateTime dt2) {
        List<LocalDateTime> businessHoursSlots = businessHoursCalendarUtil.businessHoursSlots(dt1);

        if (businessHoursSlots.isEmpty()) {
           return Duration.ZERO;
        }

        Duration duration = Duration.ZERO;
        for (int i = 0; i < businessHoursSlots.size(); i += 2) {
            LocalDateTime bh1 = businessHoursSlots.get(i);
            LocalDateTime bh2 = businessHoursSlots.get(i+1);

            if (dt1.isBefore(bh1) && dt2.isBefore(bh1)) {
                return duration;
            }

            if (dt1.isAfter(bh1) && dt2.isBefore(bh2)) {
                return Duration.between(dt1, dt2);
            }

            if (dt1.isBefore(bh1) && dt2.isAfter(bh2)) {
                duration = duration.plus(Duration.between(bh1, bh2));
                continue;
            }

            if (dt1.isAfter(bh1) && dt1.isBefore(bh2) && dt2.isAfter(bh2)) {
                duration = duration.plus(Duration.between(dt1, bh2));
                continue;
            }

            if (dt1.isBefore(bh1) && dt2.isAfter(bh1) && dt2.isBefore(bh2)) {
                duration = duration.plus(Duration.between(bh1, dt2));
                continue;
            }
        }
        return duration;
    }

    private Duration durationFirstDay(LocalDateTime dt1) {
        List<LocalDateTime> businessHours = businessHoursCalendarUtil.businessHoursSlots(dt1);

        if (businessHours.isEmpty()) {
            return Duration.ZERO;
        }

        Duration duration = Duration.ZERO;
        for (int i = 0; i < businessHours.size(); i += 2) {
            LocalDateTime bh1 = businessHours.get(i);
            LocalDateTime bh2 = businessHours.get(i+1);

            if (dt1.isBefore(bh1)) {
                duration = duration.plus(Duration.between(bh1, bh2));
                continue;
            }

            if (dt1.isAfter(bh2)) {
                continue;
            }

            duration = duration.plus(Duration.between(dt1, bh2));
        }
        return duration;
    }

    private Duration durationLastDay(LocalDateTime dt2) {
        List<LocalDateTime> businessHours = businessHoursCalendarUtil.businessHoursSlots(dt2);

        if (businessHours.isEmpty()) {
            return Duration.ZERO;
        }

        Duration duration = Duration.ZERO;
        for (int i = 0; i < businessHours.size(); i += 2) {
            LocalDateTime bh1 = businessHours.get(i);
            LocalDateTime bh2 = businessHours.get(i+1);

            if (dt2.isAfter(bh2)) {
                duration = duration.plus(Duration.between(bh1, bh2));
                continue;
            }

            if (dt2.isBefore(bh1)) {
                return duration;
            }

            duration = duration.plus(Duration.between(bh1, dt2));
        }
        return duration;
    }

    private Duration durationFullDay(LocalDateTime day) {
        List<LocalDateTime> businessHours = businessHoursCalendarUtil.businessHoursSlots(day);
        if (businessHours.isEmpty()) {
            return Duration.ZERO;
        }
        Duration duration = Duration.ZERO;
        for (int i = 0; i < businessHours.size(); i+=2) {
            LocalDateTime bh1 = businessHours.get(i);
            LocalDateTime bh2 = businessHours.get(i+1);
            duration = duration.plus(Duration.between(bh1, bh2));
        }
        return duration;

    }

}
