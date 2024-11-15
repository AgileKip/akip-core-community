package org.akip.dashboard.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class BusinessHoursCalendarUtil {

    private Properties calendarProperties = new Properties();

    public BusinessHoursCalendarUtil(String calendarPropertiesAsString) {
        try {
            calendarProperties.load(new ByteArrayInputStream(calendarPropertiesAsString.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public long daysBetween(LocalDateTime dt1, LocalDateTime dt2) {
        LocalDate d1 = dt1.toLocalDate();
        LocalDate d2 = dt2.toLocalDate();
        return ChronoUnit.DAYS.between(d1, d2) + 1;
    }

    public List<LocalDateTime> businessHoursSlots(LocalDateTime day) {
        if (isHoliday(day)) {
            return Collections.emptyList();
        }
        String businessHoursAsString = calendarProperties.getProperty("weekday." + day.getDayOfWeek().toString().toLowerCase(), "08:00-12:00 & 14:00-18:00");
        if (businessHoursAsString.isEmpty()) {
            return Collections.emptyList();
        }
        String[] slots =  businessHoursAsString.split("&");
        List<LocalDateTime> businessHours = new ArrayList<>();
        for (int i=0; i < slots.length; i++) {
            String slot = slots[i];
            String[] startAndEndHourMinute = slot.trim().split("-");
            String[] startHourMinute = startAndEndHourMinute[0].split(":");
            String[] endHourMinute = startAndEndHourMinute[1].split(":");
            int startHour = Integer.valueOf(startHourMinute[0]);
            int startMinute = Integer.valueOf(startHourMinute[1]);
            int endHour = Integer.valueOf(endHourMinute[0]);
            int endMinute = Integer.valueOf(endHourMinute[1]);
            businessHours.add(day.withHour(startHour).withMinute(startMinute).withSecond(0));
            businessHours.add(day.withHour(endHour).withMinute(endMinute).withSecond(0));
        }
        return businessHours;
    }

    public boolean isHoliday(LocalDateTime day) {
        for (LocalDate holiday: getHolidays()) {
            if (day.toLocalDate().equals(holiday)) {
                return true;
            }
        }
        return false;
    }

    public List<LocalDate> getHolidays() {
        List<String> holidayKeys = Collections
                .list(calendarProperties.keys())
                .stream()
                .map(o -> o.toString())
                .filter(key -> key.startsWith("holiday"))
                .collect(Collectors.toList());

        return holidayKeys
                .stream()
                .map(key -> {
                    String[] values = calendarProperties.getProperty(key).split("#")[0].split("/");
                    int day = Integer.valueOf(values[0].trim());
                    int month = Integer.valueOf(values[1].trim());
                    int year = Integer.valueOf(values[2].trim());
                    return LocalDate.of(year, month, day);
                }).collect(Collectors.toList());
    }
}
