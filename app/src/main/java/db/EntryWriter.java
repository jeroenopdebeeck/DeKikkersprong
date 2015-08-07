package db;


import domain.CheckIn;
import domain.CheckOut;

public interface EntryWriter {

    public void writeCheckIn(CheckIn checkIn);
    public void writeCheckOut(CheckOut checkOut);

}
