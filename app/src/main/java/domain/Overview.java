package domain;
import java.util.ArrayList;
import java.util.HashMap;

public class Overview {

    private ArrayList<DayOverview> days;
    private HashMap<Child,ArrayList> overviewPerKid;

    public Overview(){
        days = new ArrayList<>();
        overviewPerKid = new HashMap<Child,ArrayList>();
    }

    //Only called if child is for registered for the first time
    public void addChild(Child child){
        overviewPerKid.put(child,new ArrayList<DayOverview>());
    }

    //Add day overview to day overview arraylist for specific child
    public void addDayEntry(Child child, DayOverview dayOverview){
        overviewPerKid.get(child).add(dayOverview);
    }



}
