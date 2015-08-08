package db;


import domain.CheckIn;
import domain.CheckOut;
import domain.Overview;

public interface EntryWriter {

    public void writeCheckIn(CheckIn checkIn);
    public void writeCheckOut(CheckOut checkOut);
    public void writeOverview(Overview overview);

}
