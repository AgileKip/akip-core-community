package org.akip.dashboard.util;

import org.akip.dashboard.model.DashboardInterval;
import org.akip.dashboard.model.DashboardRequestModel;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class IntervalUtil {

    public static LocalDateTime getStartDateTime(DashboardRequestModel dashboardRequest) {
        if (dashboardRequest.getInterval().equals(DashboardInterval.TODAY)) {
            return LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        }

        if (dashboardRequest.getInterval().equals(DashboardInterval.YESTERDAY)) {
            return LocalDateTime.now().minusDays(1).withHour(0).withMinute(0).withSecond(0);
        }

        if (dashboardRequest.getInterval().equals(DashboardInterval.LAST_2_DAYS)) {
            return LocalDateTime.now().minusDays(2).withHour(0).withMinute(0).withSecond(0);
        }

        if (dashboardRequest.getInterval().equals(DashboardInterval.LAST_7_DAYS)) {
            return LocalDateTime.now().minusDays(7).withHour(0).withMinute(0).withSecond(0);
        }

        if (dashboardRequest.getInterval().equals(DashboardInterval.LAST_15_DAYS)) {
            return LocalDateTime.now().minusDays(15).withHour(0).withMinute(0).withSecond(0);
        }

        if (dashboardRequest.getInterval().equals(DashboardInterval.LAST_30_DAYS)) {
            return LocalDateTime.now().minusDays(30).withHour(0).withMinute(0).withSecond(0);
        }

        if (dashboardRequest.getInterval().equals(DashboardInterval.LAST_90_DAYS)) {
            return LocalDateTime.now().minusDays(90).withHour(0).withMinute(0).withSecond(0);
        }

        if (dashboardRequest.getInterval().equals(DashboardInterval.LAST_180_DAYS)) {
            return LocalDateTime.now().minusDays(180).withHour(0).withMinute(0).withSecond(0);
        }

        if (dashboardRequest.getInterval().equals(DashboardInterval.LAST_365_DAYS)) {
            return LocalDateTime.now().minusDays(365).withHour(0).withMinute(0).withSecond(0);
        }

        if (dashboardRequest.getInterval().equals(DashboardInterval.LAST_WEEK)) {
            return LocalDateTime.now().minusWeeks(1).with(DayOfWeek.MONDAY).withHour(0).withMinute(0).withSecond(0);
        }

        if (dashboardRequest.getInterval().equals(DashboardInterval.LAST_MONTH)) {
            return LocalDateTime.now().minusMonths(1).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        }

        return LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);

    }


    public static LocalDateTime getEndDateTime(DashboardRequestModel dashboardRequest) {

        if (dashboardRequest.getInterval().equals(DashboardInterval.YESTERDAY)) {
            return LocalDateTime.now().minusDays(1).withHour(23).withMinute(59).withSecond(59);
        }

        if (dashboardRequest.getInterval().equals(DashboardInterval.LAST_WEEK)) {
            return LocalDateTime.now().minusWeeks(1).with(DayOfWeek.SUNDAY).withHour(23).withMinute(59).withSecond(59);
        }

        if (dashboardRequest.getInterval().equals(DashboardInterval.LAST_MONTH)) {
            LocalDateTime lastMonth = LocalDateTime.now().minusMonths(1);
            return lastMonth.withDayOfMonth(lastMonth.getMonth().length(false)).withHour(23).withMinute(59).withSecond(59);
        }

        return LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
    }

}
