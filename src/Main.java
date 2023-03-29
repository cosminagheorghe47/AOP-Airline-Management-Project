import model.*;
import service.*;

import java.text.ParseException;
import java.util.*;
public class Main {
    public static void main(String[] args) throws ParseException {
        Airline airline =new Airline();
        /*
        Coupon cup1=new Coupon(1,30,"30/08/2024");
        Coupon cup2=new Coupon(2,30,"30/09/2024");
        Coupon[] cupon1= {cup1,cup2};

        Client client1= new Client(1,"Gheorghe","Cosmina","female",20,"romana",cupon1);
        System.out.println(client1);
        ClientService.removeCoupon(client1,cup1);
        System.out.println(client1);
        Aircraft aircraft2=new Aircraft(1,"A12345",30,13,460,500,12);
        Aircraft aircraft1=new Aircraft(2,"A123",30,13,460,500,12);
        Aircraft aircraft3=new Aircraft(3,"A1234567",30,13,460,500,12);
        Aircraft aircraft4=new Aircraft(1,"A12345",30,13,460,500,12);
        //System.out.println(aircraft2.equals(aircraft4));
        Aircraft[] aircraftsFirst={aircraft2,aircraft1,null};
        Airline airline1=new Airline("CosAir",aircraftsFirst,new ArrayList<>(),new ArrayList<>());
        //Airline airline1=new Airline("CosAir2");
        //AirlineService.addAircraft(airline1,aircraft1);
        //AirlineService.addAircraft(airline1,aircraft2);
        AirlineService.addAircraft(airline1,aircraft3);
        AirlineService.printAircraftsDetails(airline1);
        //compareDates("02 07 2023","02 07 2023"); data mai veche e mai mare
        //System.out.println(compareTime("13:45","11:57"));
        System.out.println(airline1.toString());
        City city1=BuildService.buildAddCity("Bucharest",airline1);
        BuildService.buildAddCity("Cluj",airline1);
        BuildService.buildAddCity("Rome",airline1);
        City city2=BuildService.buildAddCity("Paris",airline1);
        BuildService.buildAddCity("Barcelona",airline1);
        BuildService.buildAddCity("London",airline1);

        Flight flight1=BuildService.buildFlight("1/12:45/12-12-2023/Paris/22:44/15-12-2023/Bucharest/100/2/Cluj/Rome",airline1);
        AirlineService.addFlight(airline1,flight1);
        Flight flight2=BuildService.buildFlight("2/12:44/12-12-2023/Paris/22:44/14-12-2023/Bucharest/100/2/Cluj/Rome",airline1);
        AirlineService.addFlight(airline1,flight2);
        System.out.println(airline1.toString());
        FlightService.sortFlightsList(airline1);  //mergeee
        System.out.println(airline1.toString());
        FlightService.timeOfStay(flight1);  //mergeee
        AirlineService.searchFlight(airline1,city2,city1,"Anytime","Anytime",3);
        AirlineService.removeFlight(airline1,flight1);
        AirlineService.removeAircraft(airline1,aircraft2);
        System.out.println(airline1.toString());
        */
        airline.setup();
        while(true) {
            System.out.println("\n1: Actions on flights and aircrafts.✈️");
            System.out.println("2: Actions on persons.☺");
            System.out.println("3: Exit.❌");
            Scanner scanner = new Scanner(System.in);
            System.out.println("Pick an option : ");
            int optiune = scanner.nextInt();
            switch (optiune) {
                case 1: {
                    airline.menuFligthsAircrafts();
                    break;
                }

                case 2: {
                    airline.menuPersons();
                    break;
                }

                case 3: {
                    System.exit(0);
                }
                default: {
                    System.out.println("⟲Select an available option!\n");
                }
            }



//
        }



    }

}