package db;


import domain.CheckIn;
import domain.CheckOut;
import domain.Overview;

public interface EntryWriter {

    public void writeAllVisits();
    public void writeAllChildren();


}
