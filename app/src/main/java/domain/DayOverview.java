package domain;

import org.joda.time.DateTime;

/**
 * Created by Jeroen on 7/08/2015.
 */
public class DayOverview {

    private DateTime day;
    private int totalHours;
    private Child child;

    //Dayoverview gets generated on checkout
    public DayOverview(CheckOut checkout){


        day = checkout.getEndTime();
        totalHours = checkout.getHours();
        child = checkout.getChild();


    }

}
