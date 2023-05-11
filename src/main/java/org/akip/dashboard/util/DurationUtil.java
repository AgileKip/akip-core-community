package org.akip.dashboard.util;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class DurationUtil {

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

}
