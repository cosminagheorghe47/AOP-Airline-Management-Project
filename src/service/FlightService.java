package service;

import exception.TooManyCoupons;
import model.*;
import service.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import repository.*;

public class FlightService {

    public boolean profitSumFlight(Flight flight){
        int pretTotal=0;
        for(Booking booking : flightRepository.getInstance().findBookingsByFlightId(flight.getIdFlight())) {
            if (booking != null) {
                int ok=0;
                for(Coupon c:couponRepository.getInstance().findAllCoupons()){
                    if(c.getFlightUsedON()!=null && ok==0 && c != null && c.getFlightUsedON().equals(flight) && c.getIdClient()==booking.getClient().getIdPerson()){
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
        for(Coupon c: clientRepository.getInstance().findCouponsByClientId(booking.getClient().getIdPerson()))
        {
            if(c!=null && couponRepository.getInstance().findFlightByCouponId(c.getIdCoupon())==null )
            {
                System.out.println("aici");
                couponRepository.getInstance().assignFlightToCoupon(c.getIdCoupon(),flight);
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
                ref.incSoldEconomy(flight);
                flightRepository.getInstance().increaseSoldSeatsEconomy(flight.getIdFlight());
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
                ref.incSoldFirstClass(flight);
                flightRepository.getInstance().increaseSoldSeatsFirstClass(flight.getIdFlight());
                System.out.println( "Your reservation has been successfully made!");
            }
        }
        if (nrOfEmptySeats(flight)[0]+1<2)
        {
            int i=0;
            for(Coupon c: clientRepository.getInstance().findCouponsByClientId(booking.getClient().getIdPerson())){
                if( c!=null){
                    i=i+1;
                }
            }
            if(i<10)
            {
                booking.getClient().getCoupons()[i]=new Coupon(booking.getClient().getIdPerson()+100+i,
                        booking.getClient().getIdPerson(),
                        20+i,"30/12/2023");
                couponRepository.getInstance().addCoupon(booking.getClient().getCoupons()[i]);
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
        Collections.sort(airline.getFlights());
        System.out.println("The flights have been sorted.");
    }
    public void editFlight(Flight flight){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Edit Flight (write the new value for the specific parameter or '0' if you don't want to change it):");
        System.out.println("Aircraft id:");
        int idAir = Integer.parseInt(scanner.next());
        System.out.println("Departure time:");
        String dt = scanner.next();
        System.out.println("Departure date:");
        String dd = scanner.next();
        System.out.println("Arrival time:");
        String at = scanner.next();
        System.out.println("Arrival date:");
        String ad = scanner.next();
        System.out.println("Sold seats economy:");
        int se = Integer.parseInt(scanner.next());
        System.out.println("Sold seats first class;:");
        int sf = Integer.parseInt(scanner.next());
        AirlineService.updateFlightDetails(flight, idAir,dt,dd,at,ad,se,sf);
    }
}
