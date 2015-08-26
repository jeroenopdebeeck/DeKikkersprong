package domain;

import org.joda.time.DateTime;
import org.joda.time.Period;

/**
 * Created by Jeroen on 23/08/2015.
 */
public class Visit {

    private int day;
    private int month;
    private DateTime startTime;
    private DateTime endTime;
    private Period p;
    private int hours;

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

    public int getHours() {

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



}
