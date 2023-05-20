package service;
import exception.TooManyCoupons;
import exception.TooManyAircrafts;
import service.*;
import model.*;
import repository.*;

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
    public void addAircraftDB(Aircraft aircraft) {
        aircraftRepository.addAircraftDB(aircraft);
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

    public void removeCity(Airline airline,City city){
        airline.getCities().remove(city);
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

    public void searchFlight(Airline airline, City departure, City arrival, String departureDate) {
        int ok=0;
        for (Flight f : airline.getFlights()) {
            if (f != null && departureDate.equals("Anytime") && f.getDepartureCity().equals(departure)
                    && f.getArrivalCity().equals(arrival) && flightService.nrOfEmptySeats(f)[0] > 0) {
                    System.out.println(f);
                    ok=1;
            } else if (f != null && f.getDepartureCity().equals(departure) && f.getArrivalCity().equals(arrival)
                        && f.getDepartureDate().equals(departureDate) && flightService.nrOfEmptySeats(f)[0] > 0) {
                    System.out.println(f);
                    ok=1;
                }
        }
        if(ok==0){
            System.out.println("We haven't found an empty flight for you.");

        }
    }
    public void searchReturn(Flight flight, int timeStay){
        for (Flight f : Airline.getInstance().getFlights()){
            if(timeStay==0) {
                if(f!=null && f.getArrivalCity().equals(flight.getDepartureCity()) &&
                        f.getDepartureCity().equals(flight.getArrivalCity())
                        && f.compareTo(flight)==1){
                    if (flightService.nrOfEmptySeats(f)[0] > 0) {
                        System.out.println(f.toString());
                    }
                }
            }
            else{
                if(f!=null && f.getArrivalCity().equals(flight.getDepartureCity()) &&
                        f.getDepartureCity().equals(flight.getArrivalCity())
                        && flightService.timeOfStay(flight,f) == timeStay && f.compareTo(flight)==1){
                    if (flightService.nrOfEmptySeats(f)[0] > 0) {
                        System.out.println(f.toString());
                    }
                }
            }

        }
    }
    public void updateAircraftDetails(int aircraftId, String name, int nrOfSeatsFirstClass, int nrOfSeatsEconomy) {
        Aircraft aircraft = aircraftRepository.getInstance().findAircraftByID(aircraftId);
        if (aircraft != null) {
            if (!name.equals("0")) {
                aircraft.setName(name);
            }
            if (nrOfSeatsFirstClass > 0) {
                aircraft.setNrOfSeatsFirstClass(nrOfSeatsFirstClass);
            }
            if (nrOfSeatsEconomy > 0) {
                aircraft.setNrOfSeatsEconomy(nrOfSeatsEconomy);
            }
            // Update the aircraft in the database
            aircraftRepository.getInstance().updateAircraft(aircraft);
        } else {
            System.out.println("Aircraft not found.");
        }
    }
    public static void updateFlightDetails(Flight flight, int idair, String dt, String dd, String at, String ad, int soldec, int soldfirst) {
        if (flight != null) {
            if (idair > 0) {
                flight.setAircraft(aircraftRepository.getInstance().findAircraftByID(idair));
            }
            if (!dt.equals("0")) {
                flight.setDepartureTime(dt);
            }
            if (!dd.equals("0")) {
                flight.setDepartureDate(dd);
            }
            if (!at.equals("0")) {
                flight.setArrivalTime(at);
            }
            if (!ad.equals("0")) {
                flight.setArrivalDate(ad);
            }
            if (soldfirst > 0) {
                flight.setSoldSeatsFirstClass(soldfirst);
            }
            if (soldec > 0) {
                flight.setSoldSeatsEconomy(soldec);
            }
            // Update the aircraft in the database
            flightRepository.getInstance().updateFlight(flight);
        } else {
            System.out.println("Aircraft not found.");
        }
    }
    public void updateCityDetails(String name, String newname) {
        City city= cityRepository.getInstance().findCityByName(name);
        if (city != null) {
            if (name != null) {
                city.setCityName(newname);
            }
            cityRepository.getInstance().updateCity(city);
        } else {
            System.out.println("Aircraft not found.");
        }
    }
}