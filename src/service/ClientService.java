package service;
import model.*;
import repository.aircraftRepository;

import java.util.Arrays;
import repository.*;
public class ClientService {

    public static void sortCoupons(Client client){
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
                    if (booking!=null && booking.getClient().getIdPerson()==client.getIdPerson()) {
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

    public void updateClientDetails(Client client,int age) {
        if (client != null) {

            if (age > 0) {
                client.setAge(age);
            }
            // Update the client in the database
            clientRepository.getInstance().updateClientsAge(client);
        } else {
            System.out.println("Aircraft not found.");
        }
    }
    public void updateBookingDetails(String yes,Booking booking,int seat,int row,int nr,boolean booll) {
        if (booking != null) {

            if (seat > 0 ) {
                booking.setSeat(seat);
            }
            if (row > 0 ) {
                booking.setRow(row);
            }
            booking.setNrOfBaggages(nr);
            if(yes.equals("Economy")){
                ((EconomyBooking)booking).setHasPriority(booll);
                economyBookingRepository.getInstance().updateEconomyBooking((EconomyBooking) booking);
            }
            else{
                ((FirstClassBooking)booking).setVegetarian(booll);
                firstClassBookingRepository.getInstance().updateFirstClassBooking((FirstClassBooking) booking);
            }

        } else {
            System.out.println("Booking not found.");
        }
    }
}
