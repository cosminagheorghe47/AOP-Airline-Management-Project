package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
//Aici am Builder
public class Flight implements Comparator<Flight>{
    private int idFlight;
    private Aircraft aircraft;
    private String departureTime;
    private String departureDate;
    private City departureCity;
    private String arrivalTime;
    private String arrivalDate;
    private City arrivalCity;
    private int distance;
    private static int soldSeatsEconomy;
    private static int soldSeatsFirstClass;

    private List<City> stops=new ArrayList<>();
    private List<Booking> bookingArrayList=new ArrayList<>(); //ordonez alfabetic dupa client o pun doar la celelalte ++ nr clienti


    private Flight(int idFlight, Aircraft aircraft, String departureTime, String departureDate, City departureCity, String arrivalTime, String arrivalDate, City arrivalCity, int distance, List<City> stops, List<Booking> bookingArrayList) {
        this.idFlight = idFlight;
        this.aircraft = aircraft;
        this.departureTime = departureTime;
        this.departureDate = departureDate;
        this.departureCity = departureCity;
        this.arrivalTime = arrivalTime;
        this.arrivalDate = arrivalDate;
        this.arrivalCity = arrivalCity;
        this.distance = distance;
        this.stops = stops;
        this.bookingArrayList = bookingArrayList;
    }
    public Flight(){

    }
/*
    protected static void incSoldEconomy()
    {
        soldSeatsEconomy++;
    }
    protected static void incSoldFirst()
    {
        soldSeatsFirstClass++;
    }*/
    public int getIdFlight() {
        return idFlight;
    }

    public void setIdFlight(int idFlight) {
        this.idFlight = idFlight;
    }

    public List<Booking> getBookingArrayList() {
        return bookingArrayList;
    }

    public void setBookingArrayList(ArrayList<Booking> bookingArrayList) {
        this.bookingArrayList = bookingArrayList;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public City getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(City departureCity) {
        this.departureCity = departureCity;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public City getArrivalCity() {
        return arrivalCity;
    }

    public void setArrivalCity(City arrivalCity) {
        this.arrivalCity = arrivalCity;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getSoldSeatsEconomy() {
        return soldSeatsEconomy;
    }

    public int getSoldSeatsFirstClass() {
        return soldSeatsFirstClass;
    }

    public void setSoldSeatsEconomy(int soldSeatsEconomy) {
        this.soldSeatsEconomy = soldSeatsEconomy;
    }

    public void setSoldSeatsFirstClass(int soldSeatsFirstClass) {
        this.soldSeatsFirstClass = soldSeatsFirstClass;
    }

    public List<City> getStops() {
        return stops;
    }

    public void setStops(List<City> stops) {
        this.stops = stops;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Flight flight)) return false;
        return idFlight == flight.idFlight && aircraft.equals(flight.aircraft);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idFlight, aircraft);
    }

    @Override
    public String toString() {
        return "➙Flight{" +
                "idFlight=" + idFlight +
                ", aircraft=" + aircraft +
                ", departureTime='" + departureTime + '\'' +
                ", departureDate='" + departureDate + '\'' +
                ", departureCity=" + departureCity +
                ", arrivalTime='" + arrivalTime + '\'' +
                ", arrivalDate='" + arrivalDate + '\'' +
                ", arrivalCity=" + arrivalCity +
                ", distance=" + distance +
                ", soldSeatsEconomy=" + soldSeatsEconomy +
                ", soldSeatsFirstClass=" + soldSeatsFirstClass +
                ", stops=" + stops +
                '}';
    }

    @Override
    public int compare(Flight o1, Flight o2) {
        int comp= 0;
        try {
            comp = compareDates(o1.departureDate,o2.departureDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        if(comp==1){
            return 1;
        } else if (comp==0) {
            int comp2= 0;
            try {
                comp2 = compareTime(o1.departureTime,o2.departureTime);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            if(comp2>=0){
                return 1;
            }
            return -1;
        }
        return -1;
    }
    public int compareDates(String date1, String date2) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date d1 = simpleDateFormat.parse(date1);
        Date d2 = simpleDateFormat.parse(date2);
        return  d2.compareTo(d1);
    }
    public int compareTime(String time1, String time2) throws ParseException{
        SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
        Date t1 = parser.parse(time1);
        Date t2 = parser.parse(time2);

        if(t2.after(t1)){
            return 1;
        }
        else if(t2.before(t1)){
            return -1;
        }
        else{
            return 0;
        }
    }

    public static class Builder {
        private Flight flight = new Flight();

        public Builder withId(int id) {
            flight.setIdFlight(id);
            return this;
        }
        public Builder withAircraft(Aircraft aircraft){
            flight.setAircraft(aircraft);
            return this;
        }
        public Builder withDepartureTime(String departureTime) {
            flight.setDepartureTime(departureTime);
            return this;
        }
        public Builder withDepartureDate(String departureDate) {
            flight.setDepartureDate(departureDate);
            return this;
        }
        public Builder withDepartureCity(City departureCity) {
            flight.setDepartureCity(departureCity);
            return this;
        }
        public Builder withArrivalTime(String arrivalTime) {
            flight.setArrivalTime(arrivalTime);
            return this;
        }
        public Builder withArrivalDate(String arrivalDate) {
            flight.setArrivalDate(arrivalDate);
            return this;
        }
        public Builder withArrivalCity(City arrivalCity) {
            flight.setArrivalCity(arrivalCity);
            return this;
        }

        public Builder withDistance(int distance) {
            flight.setDistance(distance);
            return this;
        }

        public Builder withSoldSeatsEconomy(int soldSeatsEconomy) {
            flight.setSoldSeatsEconomy(soldSeatsEconomy);
            return this;
        }
        public Builder withSoldSeatsFirstClass(int soldSeatsFirstClass) {
            flight.setSoldSeatsFirstClass(soldSeatsFirstClass);
            return this;
        }
        public Builder withStops(List<City> stops) {
            flight.setStops(stops);
            return this;
        }
        /*
        public Builder withBookings(ArrayList<Booking> bookingArrayList) {
            flight.setBookingArrayList(bookingArrayList);
            return this;
        }*/
        public Flight build() {
            return this.flight;
        }
    }
}
