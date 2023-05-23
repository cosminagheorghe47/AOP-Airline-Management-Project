package service;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import exception.TooManyCoupons;
import exception.TooManyAircrafts;
import model.Aircraft;
import model.City;
import model.*;
import repository.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Airline {
    private AirlineService airlineService=new AirlineService();
    private BuildService buildService=new BuildService();
    private ClientService clientService=new ClientService();
    private EmployeeService employeeService=new EmployeeService();
    private FlightService flightService=new FlightService();
    private static String nameAirline="CosAir";
    private Aircraft[] aircrafts =new Aircraft[200];
    private List<Flight> flights=new ArrayList<>();
    private Set<City> cities=new HashSet<>();
    private List<Client> clients=new ArrayList<>();
    private List<Employee> employees=new ArrayList<>();
    private List<EconomyBooking> economyBookings=new ArrayList<>();
    private List<FirstClassBooking> firstClassBookings=new ArrayList<>();
    private Map<Employee, List<Flight>> employeeFlights = new HashMap();
    private static Airline single_instance = null;
    public Airline(){
    }
    public static synchronized Airline getInstance(){
        if(single_instance == null){
            single_instance = new Airline();
        }
        return single_instance;
    }


    public List<EconomyBooking> getEconomyBookings() {
        return economyBookings;
    }

    public void setEconomyBookings(List<EconomyBooking> economyBookings) {
        this.economyBookings = economyBookings;
    }

    public List<FirstClassBooking> getFirstClassBookings() {
        return firstClassBookings;
    }



    public Aircraft[] getAircrafts() {
        return aircrafts;
    }

    public void setAircrafts(Aircraft[] aircrafts) {
        this.aircrafts = aircrafts;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public Set<City> getCities() {
        return cities;
    }

    public List<Client> getClients() {
        return clients;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    aircraftRepository repositoryAircraft = new aircraftRepository();
    cityRepository repositoryCity = new cityRepository();
    static flightRepository flightRepository=new flightRepository();
    pilotRepository pilotRepository=new pilotRepository();
    flightAttendantRepository flightAttendantRepository=new flightAttendantRepository();
    @Override
    public String toString() {
        return "Airline{" +
                "nameAirline='" + nameAirline + '\'' +
                ", aircrafts=" + Arrays.toString(aircrafts) +
                ", flights=" + flights.toString() +
                ", cities=" + cities.toString() +
                '}';
    }
    //              MENU FLIGTHS AND AIRCRAFTS
    public void menuFligthsAircrafts() throws IOException {
        int ok=1;
        while(ok==1){
        System.out.println("❀Actions on flights and aircrafts:");
        System.out.println("1.Add Aircraft");
        System.out.println("2.Remove Aircraft");
        System.out.println("3.Add Flight");
        System.out.println("4.Delete a Flight");
        System.out.println("5.Show Flights");
        System.out.println("6.Show Aircrafts");
        System.out.println("7.Search Flights");
        System.out.println("8.Find Return");
        System.out.println("9.Check if a flight has empty seats");
        System.out.println("10.Airline profit after a flight");
        System.out.println("11.Sort flights");
        System.out.println("12.Add a city");
        System.out.println("13.Go to menu.");
        System.out.println("14.Delete a city");
        System.out.println("15.Edit an aircraft.");
        System.out.println("16.Edit a city");
        System.out.println("17.Edit a flight");
        System.out.print("->What operation you choose?(Write the number):");

        Scanner scanner = new Scanner(System.in);
        int op = scanner.nextInt();
        switch (op) {
            case 1: {
                //addAircraft
                Aircraft aircraft=buildService.buildAircraft();
                try {
                    airlineService.addAircraft(getInstance(),aircraft);
                    airlineService.addAircraftDB(aircraft);
                } catch (TooManyAircrafts e) {
                    System.out.println(e.getMessage());
                    e.getStackTrace();
                } catch (NullPointerException e) {
                    System.out.println("The reference does not exist");
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) { //multi-catch
                    System.out.println("Invalid inputs for aircraft creation. The aircraft was not added to the airline.");
                }
                Audit.logAction("addAircraft");
                break;
            }
            case 2: {
                //remove Aicraft
                Aircraft aircraft=findAircraft();
                for(Flight f: getInstance().getFlights()){
                    if (f.getAircraft().equals(aircraft)){
                        System.out.println("Edit the flight on which this aircraft is chosen");
                        flightService.editFlight(f);
                    }
                }
                aircraftRepository.deleteAircraftDB(aircraft.getIdAircraft());
                airlineService.removeAircraft(getInstance(),aircraft);
                Audit.logAction("removeAircraft");
                break;
            }
            case 3: {
                //addFlight
                System.out.println("Add Flight details like this (Flight id/Aircraft id/Departure time/Departure Date/" +
                        "Departure City/Departure time/Departure Date/Departure City/distance in km" +
                        "/Number of stops/Stops separated by '/'): ");
                String info = scanner.next();
                try{
                    Flight flight=buildService.buildFlight(info);
                    flightRepository.addFlight(flight);
                    airlineService.addFlight(getInstance(),flight);
                    for(City c: flight.getStops()){
                        flightRepository.addFlightCity(flight.getIdFlight(),c.getIdCity() );
                    }
                }
                catch (NullPointerException e) {
                    System.out.println("The reference does not exist");
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) { //multi-catch
                    System.out.println("Invalid inputs for flight creation. The flight was not added to the airline.");
                } catch(MySQLIntegrityConstraintViolationException e){
                    System.out.println("The id has already been used, try again.");
                }
                Audit.logAction("addFlight");
                break;
            }
            case 4: {
                //remove Flight
                Flight flight=findFlight();
                if (flight!=null){
                    flightRepository.deleteFlight(flight.getIdFlight());
                    airlineService.removeFlight(getInstance(),flight);
                }
                Audit.logAction("removeFlight");
                break;

            }
            case 5: {
                //show Flights
                List<Flight> flights=flightRepository.findAllFlights();
                for(Flight f : flights){
                    if(f!=null){
                        System.out.println(f);
                    }
                }
                //airlineService.printFlightsDetails();
                Audit.logAction("showFlights");
                break;
            }
            case 6: {
                //show Aircrafts
                Aircraft [] aircrafts2 = repositoryAircraft.findAllAircrafts();
                for (Aircraft a : aircrafts2) {
                    if (a != null) {
                        System.out.println(a);
                    }
                }
                //airlineService.printAircraftsDetails();
                Audit.logAction("showAircrafts");
                break;
            }
            case 7: {
                //search Flights
                System.out.println("Add the departure city : ");
                City city1=findCity();
                System.out.println("Add the arrival city : ");
                City city2=findCity();
                System.out.println("Add the departure date ('Anytime' if unknown): ");
                String dateDep = scanner.next();
                if(city1!=null && city2!= null){
                    airlineService.searchFlight(getInstance(),city1,city2,dateDep);
                }
                Audit.logAction("searchFlights");
                break;
            }
            case 8:{
                //search Return
                Flight flight=findFlight();
                System.out.println("Time of stay('0' if unknown): ");
                int info = scanner.nextInt();
                airlineService.searchReturn(flight,info);
                Audit.logAction("searchReturn");
                break;
            }
            case 9: {
                //check empty seats
                Flight flight=findFlight();
                int[] pair=flightService.nrOfEmptySeats(flight);
                System.out.println("We have "+pair[0]+": "+pair[1]+" first class seats, "+pair[2]+" economy class seats.");
                Audit.logAction("checkEmptySeats");
                break;
            }
            case 10: {
                //profit
                Flight flight=findFlight();
                boolean hasProfit=flightService.profitSumFlight(flight);
                if (!hasProfit){
                    System.out.println("This flight is not profitable.");
                }
                else{
                    System.out.println("This flight is profitable.");
                }
                Audit.logAction("checkProfitFlight");
                break;
            }
            case 11: {
                //sort Flights
                flightService.sortFlightsList(getInstance());
                Audit.logAction("sortFlights");
                break;
            }
            case 12: {
                //add city
                System.out.println("Add City name : ");
                String info = scanner.next();

                try{
                    City city=buildService.buildAddCity(info,getInstance());
                    repositoryCity.addCityDB(city);
                }
                catch (NullPointerException e) {
                    System.out.println("The reference does not exist");
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) { //multi-catch
                    System.out.println("Invalid inputs for adding a city. ");
                }
                Audit.logAction("AddCity");
                break;
            }
            case 13: {
                ok=0;
                break;
            }
            case 14: {
                //remove City
                City city=findCity();
                cityRepository.deleteCityDB(city.getIdCity());
                airlineService.removeCity(getInstance(),city);
                Audit.logAction("removeCity");
                break;
            }
            case 15:{
                scanner = new Scanner(System.in);
                System.out.println("Write the Aircraft id!");
                int id = Integer.parseInt(scanner.next());
                System.out.println("Edit Aircraft (write the new value for the specific parameter or '0' if you don't want to change it):");
                System.out.println("Name:");
                String name = scanner.next();
                System.out.println("Number of seats in economy class:");
                int n1 = Integer.parseInt(scanner.next());
                System.out.println("Number of seats in first class:");
                int n2 = Integer.parseInt(scanner.next());
                airlineService.updateAircraftDetails(id,name,n2,n1);
                Audit.logAction("editAircraft");
                break;
            }
            case 16:{
                //edit city
                City city=findCity();
                scanner = new Scanner(System.in);
                System.out.println("Write the new name!");
                String name = scanner.next();
                airlineService.updateCityDetails(city.getCityName(),name);
                Audit.logAction("editCity");
                break;
            }
            case 17:{
                Flight flight =findFlight();
                flightService.editFlight(flight);
                Audit.logAction("editFlight");
                break;
            }
            default: {
                System.out.println("⟲Select an available option!\n");
            }
        }
        }

    }
    public void menuPersons() throws IOException {
        int ok=1;
        while(ok==1){
        System.out.println("❀Actions on Clients and Employees:");
        System.out.println("1.Add Client");
        System.out.println("2.Remove Client");
        System.out.println("3.Add Employee");
        System.out.println("4.Remove Employee");
        System.out.println("5.Add Booking");
        System.out.println("6.Show all the bookings a client has");
        System.out.println("7.Show coupons");
        System.out.println("8.Sort coupons");
        System.out.println("9.Remove a coupon");
        System.out.println("10.Show all clients");
        System.out.println("11.Show all employees");
        System.out.println("12.Show all bookings");
        System.out.println("13.Remove an employee.");
        System.out.println("14.Edit an employee.");
        System.out.println("15.Edit a client's age.");
        System.out.println("16.Edit a booking.");
        System.out.println("17.Delete a booking.");
        System.out.println("18.Select the personal for a flight.");
        System.out.println("19.Show all the flights an employee has.");
        System.out.println("20.Go to menu.");
        System.out.print("->What operation you choose?(Write the number):");

        Scanner scanner = new Scanner(System.in);
        int op = scanner.nextInt();
        switch (op) {
            case 1: {
                //add client
                System.out.println("Add Client detailes (id/Last Name/First Name/gender/age/nationality):");
                String clientDetailes=scanner.next();
                try{
                    Client client= buildService.buildClient(clientDetailes);
                    clientRepository.getInstance().addClientDB(client);
                }
                catch (NullPointerException e) {
                    System.out.println("The reference does not exist");
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) { //multi-catch
                    System.out.println("Invalid inputs for adding a client. ");
                }catch(MySQLIntegrityConstraintViolationException e){
                    System.out.println("The id has already been used, try again.");
                }
                Audit.logAction("addClient");
                break;
            }
            case 2: {
                //remove Client
                Client client=findClient();
                if (client!=null){
                    clientRepository.getInstance().deleteClients(client.getIdPerson());
                    Coupon[] coupons= clientRepository.getInstance().findCouponsByClientId(client.getIdPerson());
                    for(Coupon c : coupons){
                        couponRepository.getInstance().deleteCoupon(c.getIdCoupon());
                    }
                    clientService.removeClient(client);
                }
                Audit.logAction("removeClient");
                break;
            }
            case 3: {
                System.out.println("Add Employee detailes ('FlightAttendant' or 'Pilot'/Last Name/First Name/" +
                        "gender/age/nationality/hire date/salary/number of flights or years of exp):");
                String employeeDet=scanner.next();
                try{
                    buildService.buildEmployee(employeeDet);
                }
                catch (NullPointerException e) {
                    System.out.println("The reference does not exist");
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) { //multi-catch
                    System.out.println("Invalid inputs for adding an employee. ");
                }
                Audit.logAction("addEmployee");
                break;
            }
            case 4: {
                //remove Employee
                System.out.println("Remove a pilot y/n:");
                String yes=scanner.next();
                Employee employee=employeeService.findEmployee(yes);
                if(yes.equals("y")) {
                    pilotRepository.getInstance().deletePilots(employee.getIdPerson());
                    System.out.println("intra");
                }
                else{
                    flightAttendantRepository.getInstance().deleteFlightAttendants(employee.getIdPerson());
                }
                if (employee!=null){
                    employeeService.removeEmployee(employee);
                }
                Audit.logAction("removeEmployee");
                break;
            }
            case 5: {
                //add booking
                System.out.println("Add Booking detailes ('Economy or FirstClass'/Flight Id/" +
                        "Client Id/seat/row/number of baggages/'1' if it has priority or if it is vegetarian):");
                String bookingDetailes=scanner.next();
                try{
                    Booking b=buildService.buildBooking(bookingDetailes);
                    String[] bookingParts = bookingDetailes.split("/");
                    String bookingType = bookingParts[0].trim();
                    if (bookingType.equalsIgnoreCase("Economy")){
                        economyBookings.add((EconomyBooking) b);
                        economyBookingRepository.getInstance().addEconomyBooking((EconomyBooking) b);
                    }
                    else{
                        firstClassBookings.add((FirstClassBooking) b);
                        firstClassBookingRepository.getInstance().addFirstClassBooking((FirstClassBooking) b);
                    }
                }
                catch (NullPointerException e) {
                    System.out.println("The reference does not exist");
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) { //multi-catch
                    System.out.println("Invalid inputs for adding a booking. ");
                }
                Audit.logAction("addBooking");
                break;
            }
            case 6:{
                //show bookings client
                Client client=findClient();
                clientService.showBookings(client,getInstance());
                Audit.logAction("showBookingsForClient");
                break;
            }
            case 7: {
                //show coupons
                Client client=findClient();
                for (Coupon c : client.getCoupons()) {
                    if (c != null) {
                        System.out.println(c);
                    }
                }
                Audit.logAction("showCoupons");
                break;
            }
            case 8: {
                //sortare cupoane dupa procent desc
                Client client=findClient();
                clientService.sortCoupons(client);
                Audit.logAction("sortCoupons");
                break;
            }
            case 9: {
                //remove coupon
                Client client=findClient();
                clientService.sortCoupons(client);
                Coupon coupon=client.getCoupons()[0];
                clientService.removeCoupon(client,coupon);
                Audit.logAction("removeCoupon");
                break;
            }
            case 10: {
                List<Client> clientss=clientRepository.getInstance().findAllClients();
                clientService.printAllClients(clientss);
                Audit.logAction("showClients");
                break;
            }
            case 11: {
                clientService.printAllEmployees(getInstance());
                Audit.logAction("showEmployees");
                break;
            }
            case 12:{
                for(Flight f: getInstance().getFlights()){
                    if(f!=null){
                        //System.out.println(f);
                        for(Booking b:f.getBookingArrayList()){
                            if(b!=null){
                                System.out.println(b);
                            }
                        }
                    }
                }
                Audit.logAction("showBookings");
                break;
            }
            case 13: {
                System.out.println("Remove a pilot y/n:");
                String yes=scanner.next();
                Employee employee=employeeService.findEmployee(yes);
                if (employee!=null){
                    if (employeeFlights.containsKey(employee)){
                        System.out.println("The employee: " + employee.getLastName()+" "+
                                employee.getFirstName()+" is assigned to the next flights: ");
                        for(Flight f : employeeFlights.get(employee)){
                            if(f!=null){
                                System.out.println("°"+f.toString());
                            }
                        }
                    }
                    else{
                        System.out.println("This employee is not assigned to any flight.");
                    }
                }
                Audit.logAction("removeEmployee");
                break;
            }
            case 14: {
                System.out.println("Edit a pilot y/n:");
                String yes=scanner.next();
                Employee employee=employeeService.findEmployee(yes);
                System.out.println("Edit Employee (write the new value for the specific parameter or '0' if you don't want to change it):");
                System.out.println("Age:");
                int age = Integer.parseInt(scanner.next());
                System.out.println("Salary:");
                int salary = Integer.parseInt(scanner.next());
                if(yes.equals("y"))System.out.println("Years of experience:");
                else System.out.println("Number of flights:");
                int years_nr = Integer.parseInt(scanner.next());
                employeeService.updateEmployeeDetails(employee,age,salary,years_nr,yes);
                Audit.logAction("editEmployee");
                break;
            }
            case 15:{
                Client client=findClient();
                System.out.println("New Age:");
                int age = Integer.parseInt(scanner.next());
                clientService.updateClientDetails( client, age);
                Audit.logAction("editClient");
                break;
            }
            case 16:{
                System.out.println("Edit a booking! For economy bookings write 'Economy',else 'FirstClass':");
                String yes=scanner.next();
                Booking booking=findBooking(yes);
                System.out.println("Edit Booking (write the new value for the specific parameter or '0' if you don't want to change it):");
                System.out.println("Seat:");
                int seat = Integer.parseInt(scanner.next());
                System.out.println("Row:");
                int row = Integer.parseInt(scanner.next());
                System.out.println("Number of baggages:");
                int nr = Integer.parseInt(scanner.next());
                if(yes.equals("Economy"))System.out.println("Has priority:");
                else System.out.println("Is vegetarian:");
                boolean booll = Boolean.parseBoolean(scanner.next());
                clientService.updateBookingDetails(yes,booking,seat,row,nr,booll);
                Audit.logAction("editBooking");
                break;
            }
            case 17:{
                //remove booking
                System.out.println("Delete a booking! For economy bookings write 'Economy',else 'FirstClass':");
                String yes=scanner.next();
                Booking booking=findBooking(yes);
                booking.getFlight().getBookingArrayList().remove(booking);
                if(yes.equals("Economy")){
                    flightRepository.decreaseSoldSeatsEconomy(booking.getFlight().getIdFlight());
                    economyBookingRepository.getInstance().deleteEconomyBooking(booking.getIdBooking());
                    getInstance().getEconomyBookings().remove(booking);
                }
                else{
                    flightRepository.decreaseSoldSeatsFirstClass(booking.getFlight().getIdFlight());
                    firstClassBookingRepository.getInstance().deleteFirstClassBooking(booking.getIdBooking());
                    getInstance().getFirstClassBookings().remove(booking);
                }
                Audit.logAction("removeBooking");
                break;
            }
            case 18: {
                // Assign flight attendants and pilot to a flight
                System.out.println("Enter Flight ID:");
                int flightId = scanner.nextInt();
                Flight f= repository.flightRepository.getInstance().findFlightById(flightId);

                System.out.println("Enter Pilot ID:");
                int pilotId = scanner.nextInt();

                System.out.println("Enter Flight Attendant 1 ID:");
                int flightAttendantId1 = scanner.nextInt();

                System.out.println("Enter Flight Attendant 2 ID:");
                int flightAttendantId2 = scanner.nextInt();

                // Add the flight to employee's employeeflights HashMap
                Employee pilot = pilotRepository.findPilotById(pilotId);
                Employee flightAttendant1 = flightAttendantRepository.findFlightAttendantById(flightAttendantId1);
                Employee flightAttendant2 = flightAttendantRepository.findFlightAttendantById(flightAttendantId2);

                if (pilot == null || flightAttendant1 == null || flightAttendant2 == null) {
                    System.out.println("Invalid employee IDs");
                } else {
                    employeeFlights.computeIfAbsent(pilot, k -> new ArrayList<>()).add(f);
                    employeeFlights.computeIfAbsent(flightAttendant1, k -> new ArrayList<>()).add(f);
                    employeeFlights.computeIfAbsent(flightAttendant2, k -> new ArrayList<>()).add(f);
                    System.out.println("Flight assigned successfully!");
                }
                Audit.logAction("assignFlightToEmployee");
                break;
            }
            case 19:{
                for (Map.Entry<Employee, List<Flight>> entry : employeeFlights.entrySet()) {
                    Employee employee = entry.getKey();
                    List<Flight> flights = entry.getValue();

                    System.out.println("Employee ID: " + employee.getIdPerson());
                    System.out.println("Employee Name: " + employee.getLastName());
                    System.out.println("Flights:");

                    for (Flight flight : flights) {
                        System.out.println("Flight ID: " + flight.getIdFlight());
                        System.out.println("Departure City: " + flight.getDepartureCity().getCityName());
                        System.out.println("Arrival City: " + flight.getArrivalCity().getCityName());
                        System.out.println("-------------------------");
                    }

                    System.out.println("-------------------------");
                }
                Audit.logAction("showEmployeeFlights");
                break;
            }

            case 20: {
                ok=0;
                break;
            }
            default: {
                System.out.println("⟲Select an available option!\n");
            }
        }
        }
    }

    public static Aircraft findAircraft(){

        System.out.println("Write the Aircraft id: ");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Id: ");
        int id = scanner.nextInt();
        Aircraft a= aircraftRepository.getInstance().findAircraftByID(id);
        if(a!=null)return aircraftRepository.getInstance().findAircraftByID(id);

        System.out.println("Wrong id.");
        return findAircraft();
    }
    public static Flight findFlight(){

        System.out.println("Write the Flight id: ");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Id: ");
        int id = scanner.nextInt();
        Flight f= flightRepository.findFlightById(id) ;
        if(f!=null){
            return f;
        }
        System.out.println("Wrong id.");
        return findFlight();
    }
    public static Booking findBooking(String yes){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Booking id:");
        int id = scanner.nextInt();
        if(yes.equals("Economy")){
            Booking b=economyBookingRepository.getInstance().findEconomyBookingById(id);
            if(b!=null)return b;
        }
        else{
            Booking b= firstClassBookingRepository.getInstance().findFirstClassBookingById(id);
            if(b!=null)return b;
        }
        System.out.println("Wrong id.");
        return findBooking(yes);
    }
    public static City findCity(){
        System.out.println("City name:");
        Scanner scanner = new Scanner(System.in);
        String city = scanner.next();
            City c= cityRepository.getInstance().findCityByName(city);
            if(c!=null){
            return c;}
        System.out.println("Wrong name.");
        return findCity();
    }
    public static Client findClient(){

        Scanner scanner = new Scanner(System.in);
        System.out.println("Write the Client id: ");
        int id = scanner.nextInt();
        Coupon[] cup=clientRepository.getInstance().findCouponsByClientId(id);
        Client client=clientRepository.getInstance().findClientById(id);
        client.setCoupons(cup);
        if(client!=null)return client;
        System.out.println("Wrong id.");
        return findClient();
    }

    public void setup(){
        List<City> cities = repositoryCity.findAllCities();
        for (City a : cities) {
            if (a != null) {
                buildService.addCity(a,getInstance());
                System.out.println(a);
            }
        }


        Aircraft [] aircrafts2 = repositoryAircraft.findAllAircrafts();
        for (Aircraft a : aircrafts2) {
            if (a != null) {
                airlineService.addAircraft(getInstance(),a);
            }
        }

        List<Flight> flightss=flightRepository.findAllFlights();
        for (Flight f : flightss) {
            if (f != null) {
                airlineService.addFlight(getInstance(),f);
            }
        }

        List<Client> clients=clientRepository.getInstance().findAllClients();
        for(Client c: clients){
            if(c!=null){
                Airline.getInstance().getClients().add(c);
            }
        }
        for(Client c: Airline.getInstance().getClients()){
            Coupon[] clist=clientRepository.getInstance().findCouponsByClientId(c.getIdPerson());
            c.setCoupons(clist);
        }



        List<Pilot> pilots = pilotRepository.findAllPilots();
        for (Pilot a : pilots) {
            if (a != null) {
                employeeService.addEmployee(a);
                System.out.println(a);
            }
        }
        List<FlightAttendant> flightAttendants = flightAttendantRepository.findAllFlightAttendants();
        for (FlightAttendant a : flightAttendants) {
            if (a != null) {
                employeeService.addEmployee(a);
                System.out.println(a);
            }
        }

        List<EconomyBooking> ebookings=economyBookingRepository.getInstance().findAllEconomyBookings();
        for(EconomyBooking ec: ebookings){
            getInstance().getEconomyBookings().add(ec);
        }
        List<FirstClassBooking> fbookings=firstClassBookingRepository.getInstance().findAllFirstClassBookings();
        for(FirstClassBooking ec: fbookings){
            getInstance().getFirstClassBookings().add(ec);
        }
        for(Flight f: getInstance().getFlights()){
            f.setBookingArrayList(repository.flightRepository.getInstance().findBookingsByFlightId(f.getIdFlight()));
        }
        for(Flight f: getInstance().getFlights()){
            for(Booking b: f.getBookingArrayList()){
                System.out.println(b);
            }
        }

    }
}
