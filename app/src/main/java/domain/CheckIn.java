package domain;
import org.joda.time.DateTime;

public class CheckIn {

    private Child child;
    private DateTime date;

    public CheckIn(Child c){
        child = c;
        date = new DateTime();
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }


}
