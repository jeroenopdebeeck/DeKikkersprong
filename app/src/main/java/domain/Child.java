package domain;


import java.util.ArrayList;

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
        return "Child{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
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
        }

        else if(present){
            Visit visit = visits.get(visits.size()-1);
            visit.setEndTime();
            visit.setP();
            visit.setHours();
            present = false;
        }
    }
}
