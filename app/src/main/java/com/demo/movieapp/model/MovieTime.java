package com.demo.movieapp.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MovieTime {

    String weekDay;
    int dayOfMonth;
    int month;
    boolean isDateSelected;

    public MovieTime(String weekDay, int dayOfMonth, int month, boolean isDateSelected) {
        this.weekDay = weekDay;
        this.dayOfMonth = dayOfMonth;
        this.month = month;
        this.isDateSelected = isDateSelected;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public boolean isDateSelected() {
        return isDateSelected;
    }

    public void setDateSelected(boolean dateSelected) {
        isDateSelected = dateSelected;
    }

    public static List<MovieTime> getScheduleFromToday() {
        List<MovieTime> schedule = new ArrayList<>();
        for(int i = 0; i < 5; i ++) {
            Date date = new Date();

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, i);


            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            String weekday = "";
            switch (dayOfWeek) {
                case Calendar.SUNDAY:
                    weekday = "Sunday";
                    break;
                case Calendar.MONDAY:
                    weekday = "Monday";
                    break;
                case Calendar.TUESDAY:
                    weekday = "Tuesday";
                    break;
                case Calendar.WEDNESDAY:
                    weekday = "Wednesday";
                    break;
                case Calendar.THURSDAY:
                    weekday = "Thursday";
                    break;
                case Calendar.FRIDAY:
                    weekday = "Friday";
                    break;
                case Calendar.SATURDAY:
                    weekday = "Saturday";
                    break;
            }

            boolean isSelected = false;

            if(i == 0) {
                weekday = "Today";
                isSelected = true;
            }

            // Get the day of the month
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);

            schedule.add(new MovieTime(weekday, dayOfMonth, month, isSelected));
        }

        return schedule;
    }
}
