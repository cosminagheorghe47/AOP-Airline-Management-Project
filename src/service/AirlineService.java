package service;
import exception.TooManyCoupons;
import exception.TooManyAircrafts;
import service.*;
import model.*;

public class AirlineService {
    private FlightService flightService=new FlightService();

    public void addFlight(Airline airline, Flight flight) {
        int nextAvailableIndex = getNumberOfFlights(airline);
        airline.getFlights().add(nextAvailableIndex, flight);
        System.out.println("The Flight " + flight.getDepartureCity().getCityName() + "-"
                + flight.getArrivalCity().getCityName() + " was added to the airline");
    }
    public void addAircraft(Airline airline, Aircraft aircraft) {
        int nextAvailableIndex = getNumberOfAircrafts(airline);
        if(nextAvailableIndex<airline.getAircrafts().length){
            airline.getAircrafts()[nextAvailableIndex] = aircraft;
            System.out.println("The Aircraft " + aircraft.getName() + " was added to the airline");
        }
        else{
            throw new TooManyAircrafts("Too many flights!");
        }
    }
    public void removeAircraft(Airline airline, Aircraft aircraft)
    {
        Aircraft[] result = new Aircraft[200];
        int i=0;
        for(Aircraft a : airline.getAircrafts()) {
            if (a !=null && !a.equals(aircraft)) {
                result[i] = a;
                i++;
            }
        }
        airline.setAircrafts(result);
    }
    public void removeFlight(Airline airline, Flight flight)
    {
        airline.getFlights().remove(flight);
    }

    private int getNumberOfAircrafts(Airline airline) {
        int numberOfAircrafts = 0;
        for (Aircraft a : airline.getAircrafts()) {
            if (a != null) {
                numberOfAircrafts++;
            }
        }
        return numberOfAircrafts;
    }

    private int getNumberOfFlights(Airline airline) {
        int numberOfFlights = 0;
        for (Flight a : airline.getFlights()) {
            if (a != null) {
                numberOfFlights++;
            }
        }
        return numberOfFlights;
    }


    public void printAircraftsDetails() {
        for (Aircraft a : Airline.getInstance().getAircrafts()) {
            if (a != null) {
                System.out.println(a);
            }
        }
    }
    public void printFlightsDetails() {
        for (Flight a : Airline.getInstance().getFlights()) {
            if (a != null) {
                System.out.println(a);
            }
        }
    }

    public void searchFlight(Airline airline, City departure, City arrival, String departureDate) {
        for (Flight f : airline.getFlights()) {
            if (f != null && departureDate.equals("Anytime") && f.getDepartureCity().equals(departure)
                    && f.getArrivalCity().equals(arrival) && flightService.nrOfEmptySeats(f)[0] > 0) {
                    System.out.println(f);
            } else if (f != null && f.getDepartureCity().equals(departure) && f.getArrivalCity().equals(arrival)
                        && f.getDepartureDate().equals(departureDate) && flightService.nrOfEmptySeats(f)[0] > 0) {
                    System.out.println(f);
                }
            else{
                System.out.println("We didn't find an empty flight for you.");
            }
        }

    }
    public void searchReturn(Flight flight, int timeStay){
        for (Flight f : Airline.getInstance().getFlights()){
            if(timeStay==0) {
                if(f!=null && f.getArrivalCity().equals(flight.getDepartureCity()) && f.getDepartureCity().equals(flight.getArrivalCity())
                        && flight.compare(flight, f)==1){
                    if (flightService.nrOfEmptySeats(f)[0] > 0) {
                        System.out.println(f.toString());
                    }
                }
            }
            else{
                if(f!=null && f.getArrivalCity().equals(flight.getDepartureCity()) && f.getDepartureCity().equals(flight.getArrivalCity())
                        && flightService.timeOfStay(f,flight) == timeStay && flight.compare(flight, f)==1){
                    if (flightService.nrOfEmptySeats(f)[0] > 0) {
                        System.out.println(f.toString());
                    }
                }
            }

        }
    }
}