package domain;
import org.joda.time.DateTime;
import org.joda.time.Period;

public class CheckOut {

    private Child child;
    private DateTime startTime;
    private DateTime endTime;
    private Period p;
    private int hours;

    public CheckOut(Child c){
        child = c;
        startTime = new DateTime();
        endTime = new DateTime();
        p = new Period(startTime, endTime);
        hours = p.getHours();
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    public DateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(DateTime startTime) {
        this.startTime = startTime;
    }

    public DateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(DateTime endTime) {
        this.endTime = endTime;
    }

    public Period getP() {
        return p;
    }

    public void setP(Period p) {
        this.p = p;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }
}
