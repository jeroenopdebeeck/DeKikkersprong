package domain;


import java.util.ArrayList;
import java.util.HashMap;

public class Child {

    private long id;
    private String firstName;
    private String lastName;
    private ArrayList<Visit> visits = new ArrayList<Visit>();
    private boolean present = false;

    public Child(){

    }

    public Child(long id, String firstName, String lastName){
        setId(id);
        setFirstName(firstName);
        setLastName(lastName);
    }

    public Child(String firstName, String lastName){
        setFirstName(firstName);
        setLastName(lastName);
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ArrayList<Visit> getVisits(){
        return visits;
    }

    @Override
    public String toString() {
        return id + " " + firstName + " " + lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Child child = (Child) o;

        return id == child.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    public void scanCard() {
        if(!present){
            Visit visit = new Visit();
            visit.setStartTime();
            visits.add(visit);
            present = true;
            System.out.println("IN");
        }

        else if(present){
            Visit visit = visits.get(visits.size()-1);
            visit.setEndTime();
            visit.setP();
            visit.setHours();
            present = false;
            System.out.println("OUT");
        }
    }

    public HashMap<Integer, Double> generateFacturationPerMonth() {

        //Prijs = 10 euro per uur
        HashMap<Integer,Double> factuurPerMonth = new HashMap<Integer,Double>();

        for (int i = 0; i < visits.size(); i++) {
            Visit visit = visits.get(i);
            int month = visit.getMonth();
            Double hours = visit.getHours();
            Double price = 10 * hours;
            if(factuurPerMonth.get(month) == null) {
                factuurPerMonth.put(month,price);
            } else {
                Double oldprice = factuurPerMonth.get(month);
                Double newprice = oldprice + price;
                factuurPerMonth.put(month,newprice);
            }
        }

        return factuurPerMonth;
    }

    public String generateTotalFacturation() {

        Double price = 0.00;
        //Prijs = 10 euro per uur

        for (int i = 0; i < visits.size(); i++) {

            Visit visit = visits.get(i);
            Double hours = visit.getHours();
            price = price + (10 * hours);

        }

        return "Te betalen voor " + firstName + " " + lastName + ": " + price + " euro.";
    }

    public boolean getPresent(){
        return this.present;
    }


}
