package service;

import exception.TooManyCoupons;
import model.*;
import service.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.Integer.parseInt;
import static java.lang.Integer.valueOf;

public class FlightService {
    private ClientService clientService=new ClientService();
    //calculez suma totala castigata de un zbor, verific daca are in profit
    public boolean profitSumFlight(Flight flight){
        int pretTotal=0;
        for(Booking booking : flight.getBookingArrayList()) {
            if (booking != null) {
                int ok=0;
                for(Coupon c:booking.getClient().getCoupons()){
                    if(ok==0 && c != null && c.getFlightUsedON().equals(flight)){
                        System.out.println(c);
                        double suma=booking.price()-((double) c.getDiscountPercentage()/100*booking.price());
                        pretTotal += suma;
                        ok=1;
                    }
                }
                if(ok==0){
                    pretTotal += booking.price();
                }

            }
        }
        System.out.println("The total price is: "+pretTotal);
        if(pretTotal>flight.getDistance()*20)
            return true;
        return false;
    }

    public int[] nrOfEmptySeats(Flight flight)
    {
        int economy=flight.getSoldSeatsEconomy();
        int firstClass=flight.getSoldSeatsFirstClass();
        int seatsEconomy=flight.getAircraft().getNrOfSeatsEconomy();
        int seatsFirstClass=flight.getAircraft().getNrOfSeatsFirstClass();
        int[] pair= {(seatsEconomy+seatsFirstClass)-(economy+firstClass),seatsFirstClass-firstClass,seatsEconomy-economy};
        return pair;
    }

    public void addBooking(Flight flight,Booking booking)
    {
        //urm index valabil este suma din numarul de locuri ocupate
        //int nextAvailableIndex = booking.getFlight().getSoldSeatsEconomy()
                                //+ booking.getFlight().getSoldSeatsFirstClass();
        for(Coupon c: booking.getClient().getCoupons())
        {
            if(c!=null && c.getFlightUsedON()==null)
            {
                c.setFlightUsedON(flight);
                break;
            }
        }
        if(booking instanceof EconomyBooking){
            if(flight.getSoldSeatsEconomy()==flight.getAircraft().getNrOfSeatsEconomy()){
                System.out.println( "There aren't any empty economy seats.");
            }
            else{
                flight.getBookingArrayList().add(booking);
                EconomyBooking ref=(EconomyBooking) booking;
                //EconomyBooking ref=(EconomyBooking) flight.getBookingArrayList().get(flight.getBookingArrayList().size());
                ref.incSoldEconomy(flight);
                //clientService.removeCoupon(booking.getClient(),booking.getClient().getCoupons()[0]);
                System.out.println( "Your reservation has been successfully made!");
            }
        }
        if(booking instanceof FirstClassBooking){
            if(flight.getSoldSeatsFirstClass()==flight.getAircraft().getNrOfSeatsFirstClass()){
                System.out.println( "There aren't any empty first class seats.");
            }
            else{
                flight.getBookingArrayList().add(booking);
                FirstClassBooking ref=(FirstClassBooking) booking;
                //FirstClassBooking ref=(FirstClassBooking) flight.getBookingArrayList().get(nextAvailableIndex);
                ref.incSoldFirstClass(flight);
                System.out.println( "Your reservation has been successfully made!");
            }
        }
        if (nrOfEmptySeats(flight)[0]+1<2) //fac +1 ca deja am pus la numarare bookingul cand l-am creat
        {
            //adaug cupon la ultimii 3
            int i=0;
            for(Coupon c: booking.getClient().getCoupons()){
                if( c!=null){
                    i=i+1;
                }
            }
            if(i<10)
            {
                booking.getClient().getCoupons()[i]=new Coupon(booking.getClient().getIdPerson()+100+i,
                        20+i,"30/12/2023");
                ClientService.sortCoupons(booking.getClient());
            }
            else{
                throw new TooManyCoupons("Too many coupons, use some of them!");

            }
        }
    }
    public long timeOfStay(Flight flight1,Flight flight2)
    {
        SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");
        String inputString1 = flight1.getDepartureDate();
        String inputString2 = flight2.getDepartureDate();

        try {
            Date date1 = myFormat.parse(inputString1);
            Date date2 = myFormat.parse(inputString2);
            long diff = date2.getTime() - date1.getTime();
            System.out.println ("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
            return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public void sortFlightsList(Airline airline) {
        //List<Flight> flights= airline.getFlights();
        Collections.sort(airline.getFlights());
        System.out.println("The flights have been sorted.");
        /*
        for (int i = 0; i < flights.size()-1; i++) {
            for (int j = i + 1; j < flights.size(); j++) {
                if(flights.get(i) !=null && flights.get(j) !=null){
                    if(flights.get(i).compare(flights.get(i), flights.get(j))<0){
                        Flight aux= flights.get(i);
                        flights.set(i, flights.get(j));
                        flights.set(j, aux);
                    }
                }
            }
        }*/
    }
}
