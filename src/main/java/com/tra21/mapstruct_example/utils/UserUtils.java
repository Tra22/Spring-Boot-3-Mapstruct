package com.tra21.mapstruct_example.utils;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class UserUtils {
    public static int getAge(Date birthDate){
        return Period.between(DateUtils.convertToLocalDateViaInstant(birthDate), DateUtils.convertToLocalDateViaInstant(new Date())).getYears();
    }
    public static Date getAgeAsDate(int age, boolean isEOY){
        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear() - age;
        if(!isEOY) year -= 1;
        return Date.from(
                LocalDate.of(
                        year, isEOY ? 12 : currentDate.getMonthValue(),
                        isEOY ? DateUtils.getLastDayOfMonthUsingCalendar(12, year) : currentDate.getDayOfMonth()
                ).atStartOfDay(ZoneId.systemDefault()).toInstant()
        );
    }

}
