package service;

import exception.TooManyCoupons;
import model.Aircraft;
import model.City;
import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static java.lang.Integer.valueOf;

public class BuildService {
    private FlightService flightService=new FlightService();

    public City buildAddCity(String cityDetails, Airline airline){
        //String[] attributes = cityDetails.split("/");
        String name=cityDetails;//attributes[0];
        City city= new City(new Random().nextInt(100),name);
        int nextAvailableIndex = getNumberOfCities(airline);
        System.out.println(nextAvailableIndex);
        airline.getCities().add(city);
        System.out.println("The City " + city.getCityName() + " was added to the airline");
        return city;
    }

    public Client buildClient(String clientDetails){
        String[] attributes = clientDetails.split("/");
        int id=valueOf(attributes[0]);
        String lName=attributes[1];
        String fName=attributes[2];
        String gender=attributes[3];
        int age=valueOf(attributes[4]);
        String nationality=attributes[5];
        Client client=new Client(id,lName,fName,gender,age,nationality);
        Airline.getInstance().getClients().add(client);
        return client;
    }
    public Employee buildEmployee(String employeeDetails) {
        Employee employee;
        String[] attributes = employeeDetails.split("/");
        String lName = attributes[1];
        String fName = attributes[2];
        String gender = attributes[3];
        int age = valueOf(attributes[4]);
        String nationality = attributes[5];
        String hireDate = attributes[6];
        int salary = valueOf(attributes[7]);
        if (attributes[0].equals("Pilot")) {
            int years = valueOf(attributes[8]);
            employee = new Pilot(new Random().nextInt(1000), lName, fName, gender, age, nationality, hireDate, salary, years);
        }
        else {
            int nrFlights = valueOf(attributes[8]);
            employee = new FlightAttendant(new Random().nextInt(1000), lName, fName, gender, age, nationality, hireDate, salary, nrFlights);
        }

        Airline.getInstance().getEmployees().add(employee);
        return employee;
    }
    private int getNumberOfCities(Airline airline) {
        int numberOfCities = 0;
        if(airline.getCities()!=null)
        {
            for (City c : airline.getCities()) {
                if (c != null) {
                    numberOfCities++;
                }
            }
        }
        return numberOfCities;
    }
    public Flight buildFlight(String flightDetails) {
        String[] attributes = flightDetails.split("/");
        int id=valueOf(attributes[0]);
        int idAircraft = valueOf(attributes[1]);
        Aircraft aircraft=new Aircraft();
        for(Aircraft a : Airline.getInstance().getAircrafts()){
            if(a!=null && a.getIdAircraft()==idAircraft){
                aircraft=a;
            }
        }
        City departureCity=new City();
        String departureTime = attributes[2];
        String departureDate = attributes[3];
        String city=attributes[4];
        for(City c : Airline.getInstance().getCities()){
            if(c!=null && c.getCityName().equals(city)){
                departureCity=c;
            }
        }
        String arrivalTime = attributes[5];
        String arrivalDate = attributes[6];
        City arrivalCity=new City();
        city=attributes[7];
        for(City c : Airline.getInstance().getCities()){
            if(c!=null && c.getCityName().equals(city)){
                arrivalCity=c;
            }
        }
        int distance=valueOf(attributes[8]);
        int nrStops=valueOf(attributes[9]); //sa cer nr of stops
        List<City> stops=new ArrayList<>();
        for(int i=0;i<nrStops;i++){
            for(City c : Airline.getInstance().getCities()){
                if(c!=null && c.getCityName().equals(attributes[10+i])){
                    stops.add(c);
                }
            }
        }
        return new Flight.Builder()
                .withId(id)
                .withAircraft(aircraft)
                .withDepartureTime(departureTime)
                .withDepartureDate(departureDate)
                .withDepartureCity(departureCity)
                .withArrivalTime(arrivalTime)
                .withArrivalDate(arrivalDate)
                .withArrivalCity(arrivalCity)
                .withDistance(distance)
                .withStops(stops)
               // .withBookings()
                .build();
    }
    public Aircraft buildAircraft(){    System.out.println("Add Aircraft info ");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Name: ");
        String name = scanner.nextLine();
        System.out.println("Number of seats in Economy Class: ");
        int nrSeats = scanner.nextInt();
        System.out.println("Number of seats in First Class: ");
        int nrSeatsFirst = scanner.nextInt();
        System.out.println("Maximum speed: ");
        int max = scanner.nextInt();
        System.out.println("Weight (kilograms): ");
        int weight = scanner.nextInt();
        System.out.println("Wing span (meters): ");
        int wing = scanner.nextInt();
        Aircraft aircraft=new Aircraft(new Random().nextInt(200),name,nrSeats,nrSeatsFirst,
                max,weight,wing);
        return aircraft;
    }
    public Booking buildBooking(String bookingDetails){
        String[] attributes = bookingDetails.split("/");
        String tip=attributes[0];
        Booking booking;
        Flight flight=new Flight();
        int flightId=valueOf(attributes[1]);
        for(Flight f:Airline.getInstance().getFlights()){
            if(f.getIdFlight()==flightId){
                flight=f;
            }
        }
        Client client=new Client();
        int clientId=valueOf(attributes[2]);
        for(Client c: Airline.getInstance().getClients()){
            if(c.getIdPerson()==clientId){
                client=c;
            }
        }
        int seat=valueOf(attributes[3]);
        int row=valueOf(attributes[4]);
        int nrBaggages=valueOf(attributes[5]);
        if(tip.equals("Economy")){
            int hasPriority= valueOf(attributes[6]);
            boolean hasP=false;
            if(hasPriority==1){
                hasP=true;
            }

            booking= new EconomyBooking(new Random().nextInt(200),flight,client,seat,row,nrBaggages,hasP);
        }
        else{
            boolean isVeggie= Boolean.parseBoolean((attributes[6]));
            booking= new FirstClassBooking(new Random().nextInt(200),flight,client,seat,row,nrBaggages,isVeggie);
        }

        try {
            flightService.addBooking(flight,booking);
        } catch (TooManyCoupons e) {
            System.out.println(e.getMessage());
            e.getStackTrace();
        } catch (NullPointerException e) {
            System.out.println("The reference does not exist");
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) { //multi-catch
            System.out.println("Invalid inputs for product creation. The booking was not added to the airline.");
        }
        return booking;
    }
}
