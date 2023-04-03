package model;

import java.util.*;
public class FlightAttendant extends Employee{
    private int nrOfFlights;

    public FlightAttendant(int id, String lastName, String firstName, String gender, int age,
                           String nationality, String hireDate, int salary, int nrOfFlights) {
        super(id, lastName, firstName, gender, age, nationality, hireDate, salary);
        this.nrOfFlights = nrOfFlights;
    }

    public int getNrOfFlights() {
        return nrOfFlights;
    }

    public void setNrOfFlights(int nrOfFlights) {
        this.nrOfFlights = nrOfFlights;
    }

     @Override
    public String toString() {
        return super.toString() +
                " as a Flight Attendant, having "+nrOfFlights+" flights.";
    }
}
