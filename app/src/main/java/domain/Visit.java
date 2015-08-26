package domain;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;

/**
 * Created by Jeroen on 23/08/2015.
 */
public class Visit {

    private int day;
    private int month;
    private DateTime startTime;
    private DateTime endTime;
    private Period p;
    private double hours;

    public Visit(){

    }

    public DateTime getStartTime() {
        return startTime;
    }

    public void setStartTime() {

        startTime = new DateTime();
        month = startTime.getMonthOfYear();
        day = startTime.getDayOfMonth();
    }

    public DateTime getEndTime() {

        return endTime;
    }

    public void setEndTime() {

        endTime = new DateTime();

    }

    public Period getP() {

        return p;
    }

    public void setP() {

        p = new Period(startTime, endTime);
    }

    public double getHours() {

        return hours;
    }

    public void setHours() {

        hours = p.getHours();
    }

    public int getDay(){

        return day;
    }

    public int getMonth() {

        return month;
    }

    public String toString() {

        DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("HH:mm:ss");

        String begin = "";
        String end = "";

        if(startTime != null) {
            begin = startTime.toString(dateFormatter);
        }
        if(endTime != null) {
            end = endTime.toString(dateFormatter);
        }

        return day + "/" + month + ": " + begin + " " + end + ", " + hours + " hours";
    }

    public String getBegin() {
        DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("HH:mm:ss");

        String begin = "";

        if(startTime != null) {
            begin = startTime.toString(dateFormatter);
        }
        return begin;
    }

    public String getEnd() {

        DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("HH:mm:ss");
        String end = "";

        if(endTime != null) {
            end = endTime.toString(dateFormatter);
        }
        return end;
    }

}
