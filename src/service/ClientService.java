package service;
import model.*;

import java.util.Arrays;

public class ClientService {
    /*
    public static void addClient(Client client){
        int nextAvailableIndex = getNumberOfClients(Airline.getInstance());
        Airline.getInstance().getClients().add(nextAvailableIndex, client);
        System.out.println("The Client " + client.getFirstName() +
                " was added to the airline");
    }*/
    private int getNumberOfClients(Airline airline) {
        int numberOfClients = 0;
        for (Client a : airline.getClients()) {
            if (a != null) {
                numberOfClients++;
            }
        }
        return numberOfClients;
    }
    public void sortCoupons(Client client){
        int i=0;
        for(Coupon c:client.getCoupons()){
            if(c!=null){
                i++;
            }
        }
        if(i>0){
            Arrays.sort(client.getCoupons(),0,i);
        }
    }
    public void printAllClients(Airline airline) {
        for (Client a : airline.getClients()) {
            if (a != null) {
                System.out.println(a);
            }
        }
    }
    public void printAllEmployees(Airline airline) {
        for (Employee a : airline.getEmployees()) {
            if (a != null) {
                System.out.println(a);
            }
        }
    }
    public void showBookings(Client client,Airline airline){
        int count=0;
        for(Flight flight : airline.getFlights()) {
            if (flight != null) {
                for (Booking booking : flight.getBookingArrayList()) {
                    if (booking!=null && booking.getClient().equals(client)) {
                        count++;
                        System.out.println(booking);
                    }
                }
            }
        }
        if(count==0){
            System.out.println("This client has no bookings.");
        }
    }
    public void removeCoupon(Client client, Coupon coupon){
        Coupon[] result = new Coupon[10];
        int i=0;
        for(Coupon coup : client.getCoupons()) {
            if (!coupon.equals(coup)) {
                result[i] = coup;
                i++;
            }
        }
        client.setCoupons(result);
    }
    public void removeClient(Client client){
        Airline.getInstance().getClients().remove(client);
    }
    public static void printCoupons(Client client){
        for (Coupon c : client.getCoupons()) {
            if (c != null) {
                System.out.println(c);
            }
        }
    }
}
