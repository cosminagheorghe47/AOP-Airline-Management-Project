package service;

import exception.TooManyCoupons;
import exception.TooManyAircrafts;
import model.Aircraft;
import model.City;
import model.*;

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
    private Map<Employee, List<Flight>> employeeFlights = new HashMap();
    private static Airline single_instance = null;
    public Airline(){}
    public static synchronized Airline getInstance(){
        if(single_instance == null){
            single_instance = new Airline();
        }
        return single_instance;
    }
    /*
        public Airline(String nameAirline)
        {
            this.nameAirline = nameAirline;
        }

        public Airline(String nameAirline, Aircraft[] aircrafts, List<Flight> flights, List<City> cities) {
            this.nameAirline = nameAirline;
            this.aircrafts = aircrafts;
            this.flights = flights;
            this.cities=cities;
        }
    */

    public Map<Employee, List<Flight>> getEmployeeFlights() {
        return employeeFlights;
    }

    public void setEmployeeFlights(Map<Employee, List<Flight>> employeeFlights) {
        this.employeeFlights = employeeFlights;
    }

    public String getNameAirline() {
        return nameAirline;
    }

    public void setNameAirline(String nameAirline) {
        this.nameAirline = nameAirline;
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

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    public Set<City> getCities() {
        return cities;
    }

    public void setCities(Set<City> cities) {
        this.cities = cities;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }


    @Override
    public String toString() {
        return "Airline{" +
                "nameAirline='" + nameAirline + '\'' +
                ", aircrafts=" + Arrays.toString(aircrafts) +
                ", flights=" + flights.toString() +
                ", cities=" + cities.toString() +
                '}';
    }
    //              MENIU FLIGTHS AND AIRCRAFTS
    public void menuFligthsAircrafts() {
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
        System.out.print("->What operation you choose?(Write the number):");

        Scanner scanner = new Scanner(System.in);
        int op = scanner.nextInt();
        switch (op) {
            case 1: {
                //addAircraft
                Aircraft aircraft=buildService.buildAircraft();
                try {
                    airlineService.addAircraft(getInstance(),aircraft);
                } catch (TooManyAircrafts e) {
                    System.out.println(e.getMessage());
                    e.getStackTrace();
                } catch (NullPointerException e) {
                    System.out.println("The reference does not exist");
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) { //multi-catch
                    System.out.println("Invalid inputs for aircraft creation. The aircraft was not added to the airline.");
                }
                break;
            }
            case 2: {
                //remove Aicraft
                Aircraft aircraft=findAircraft();
                airlineService.removeAircraft(getInstance(),aircraft);
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
                    airlineService.addFlight(getInstance(),flight);
                }
                catch (NullPointerException e) {
                    System.out.println("The reference does not exist");
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) { //multi-catch
                    System.out.println("Invalid inputs for flight creation. The flight was not added to the airline.");
                }
                break;
            }
            case 4: {
                //remove Flight
                Flight flight=findFlight();
                if (flight!=null){
                    airlineService.removeFlight(getInstance(),flight);
                }
                break;

            }
            case 5: {
                //show Flights
                airlineService.printFlightsDetails();
                break;
            }
            case 6: {
                //show Aircrafts
                airlineService.printAircraftsDetails();
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
                break;
            }
            case 8:{
                //search Return
                Flight flight=findFlight();
                System.out.println("Time of stay('0' if unknown): ");
                int info = scanner.nextInt();
                airlineService.searchReturn(flight,info);
                break;
            }
            case 9: {
                //check empty seats
                Flight flight=findFlight();
                int[] pair=flightService.nrOfEmptySeats(flight);
                System.out.println("We have "+pair[0]+": "+pair[1]+" first class seats, "+pair[2]+" economy class seats.");
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
                break;
            }
            case 11: {
                //sort Flights
                flightService.sortFlightsList(getInstance());
                break;
            }
            case 12: {
                //add city
                System.out.println("Add City name : ");
                String info = scanner.next();
                try{
                    City city=buildService.buildAddCity(info,getInstance());

                }
                catch (NullPointerException e) {
                    System.out.println("The reference does not exist");
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) { //multi-catch
                    System.out.println("Invalid inputs for adding a city. ");
                }
                break;
            }
            case 13: {
                ok=0;
                break;
            }
            default: {
                System.out.println("⟲Select an available option!\n");
            }
        }
        }

    }
    public void menuPersons() {
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
        System.out.println("13.Show all the flights an employee has.");
        System.out.println("14.Go to menu.");
        System.out.print("->What operation you choose?(Write the number):");

        Scanner scanner = new Scanner(System.in);
        int op = scanner.nextInt();
        switch (op) {
            case 1: {
                //add client
                System.out.println("Add Client detailes (id/Last Name/First Name/gender/age/nationality):");
                String clientDetailes=scanner.next();
                try{
                    buildService.buildClient(clientDetailes);

                }
                catch (NullPointerException e) {
                    System.out.println("The reference does not exist");
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) { //multi-catch
                    System.out.println("Invalid inputs for adding a client. ");
                }
                break;
            }
            case 2: {
                //remove Client
                Client client=findClient();
                if (client!=null){
                    clientService.removeClient(client);
                }
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
                break;
            }
            case 4: {
                //remove Employee
                Employee employee=employeeService.findEmployee();
                if (employee!=null){
                    employeeService.removeEmployee(employee);
                }
                break;
            }
            case 5: {
                //add booking
                System.out.println("Add Booking detailes ('Economy or First Class'/Flight Id/" +
                        "Client Id/seat/row/number of baggages/'1' if it has priority or if it is vegetarian):");
                String bookingDetailes=scanner.next();
                try{
                    buildService.buildBooking(bookingDetailes);
                }
                catch (NullPointerException e) {
                    System.out.println("The reference does not exist");
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) { //multi-catch
                    System.out.println("Invalid inputs for adding a booking. ");
                }
                break;
            }
            case 6:{
                //show bookings client
                Client client=findClient();
                clientService.showBookings(client,getInstance());
                break;
            }
            case 7: {
                //show coupons
                Client client=findClient();
                clientService.printCoupons(client);
                break;
            }
            case 8: {
                //sortare cupoane dupa procent desc
                Client client=findClient();
                clientService.sortCoupons(client);
                break;
            }
            case 9: {
                //remove coupon
                Client client=findClient();
                clientService.sortCoupons(client);
                Coupon coupon=client.getCoupons()[0];
                clientService.removeCoupon(client,coupon);
                break;
            }
            case 10: {
                clientService.printAllClients(getInstance());
                break;
            }
            case 11: {
                clientService.printAllEmployees(getInstance());
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
                break;
            }
            case 13: {
                Employee employee=employeeService.findEmployee();
                /*
                Set<Employee> keyset = employeeFlights.keySet();
                for(Employee key : keyset) {
                    System.out.println("\t\tKEY: " + key);
                    List<Flight> finList = employeeFlights.get(key);
                    for(Flight value : finList)
                        System.out.println("VALUE: " + value);

                }
                */
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
                break;
            }
            case 14: {
                ok=0;
                break;
            }
            default: {
                System.out.println("⟲Select an available option!\n");
            }
        }
        }
    }

    public void addFlightToEmployee(Employee employee, Flight flight) {
        if (!employeeFlights.containsKey(employee)) {
            employeeFlights.put(employee, new ArrayList());

        }
        employeeFlights.get(employee).add(flight);

    }
    public static Aircraft findAircraft(){
        try{
        System.out.println("Write the Aircraft id and name: ");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Name: ");
        String name = scanner.next();
        System.out.println("Id: ");
        int id = scanner.nextInt();
        for(Aircraft a : getInstance().aircrafts){
            if(a!=null){
                if( a.getIdAircraft()==id && a.getName().equals(name)){
                    return a;
                }
            }
        }
        }
        catch(InputMismatchException a){
        System.out.println("Wrong name or id.");
        return findAircraft();}
        System.out.println("Wrong name or id.");
        return findAircraft();
    }
    public static Flight findFlight(){
        try{
        System.out.println("Write the Flight id: ");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Id: ");
        int id = scanner.nextInt();
        for(Flight a : getInstance().flights){
            if(a!=null && a.getIdFlight()==id){
                return a;
            }
        }
        }
        catch(InputMismatchException a){
            System.out.println("Wrong id.");
            return findFlight();}
        System.out.println("Wrong id.");
        return findFlight();
    }
    public static City findCity(){
        System.out.println("City details:");
        Scanner scanner = new Scanner(System.in);
        String city = scanner.next();
        try{
        for(City c : getInstance().cities){
            if(c!=null && c.getCityName().equals(city)){
                return c;
            }
        }}
        catch(InputMismatchException a){
            System.out.println("Wrong name.");
            return findCity();}
        System.out.println("Wrong name.");
        return findCity();
    }
    public static Client findClient(){
        try{
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write the Client id: ");
        int id = scanner.nextInt();
        for(Client c : getInstance().clients){
            if(c!=null && c.getIdPerson()==id){
                return c;
            }
        }
        }
        catch(InputMismatchException a){
            System.out.println("Wrong id.");
            return findClient();}
        System.out.println("Wrong id.");
        return findClient();
    }

    public void setup(){
        buildService.buildAddCity("Bucharest",getInstance());
        buildService.buildAddCity("Cluj",getInstance());
        buildService.buildAddCity("Rome",getInstance());
        buildService.buildAddCity("Paris",getInstance());
        buildService.buildAddCity("Barcelona",getInstance());
        buildService.buildAddCity("London",getInstance());

        Aircraft aircraft2=new Aircraft(1,"A12345",40,20,400,500,12);
        Aircraft aircraft1=new Aircraft(2,"A123",60,10,500,500,13);
        Aircraft aircraft3=new Aircraft(3,"A1234567",70,15,700,700,16);
        Aircraft aircraft4=new Aircraft(4,"A12345",20,43,1000,400,14);
        airlineService.addAircraft(getInstance(),aircraft1);
        airlineService.addAircraft(getInstance(),aircraft2);
        airlineService.addAircraft(getInstance(),aircraft3);
        airlineService.addAircraft(getInstance(),aircraft4);

        Flight flight1=buildService.buildFlight("1/1/23:45/12-12-2023/Paris/02:10/13-12-2023/Bucharest/100/2/Cluj/Rome");
        Flight flight2=buildService.buildFlight("2/2/12:44/12-12-2023/Paris/22:44/12-12-2023/Bucharest/100/2/Cluj/Rome");
        Flight flight3=buildService.buildFlight("3/3/17:30/12-04-2023/Cluj/20:30/12-04-2023/London/100/0");
        Flight flight4=buildService.buildFlight("4/2/12:47/14-12-2023/Bucharest/22:44/14-12-2023/Paris/100/0");
        airlineService.addFlight(getInstance(),flight1);
        airlineService.addFlight(getInstance(),flight2);
        airlineService.addFlight(getInstance(),flight3);
        airlineService.addFlight(getInstance(),flight4);
        Flight flight5=buildService.buildFlight("5/1/23:45/12-05-2023/London/02:10/13-05-2023/Cluj/300/1/Rome");
        Flight flight6=buildService.buildFlight("6/3/10:47/11-10-2023/Paris/20:47/11-10-2023/Bucharest/100/2/Cluj/Rome");
        Flight flight7=buildService.buildFlight("7/3/17:30/12-05-2023/Rome/20:30/12-05-2023/London/100/0");
        Flight flight8=buildService.buildFlight("8/2/12:47/14-12-2023/London/22:44/14-12-2023/Rome/100/0");
        airlineService.addFlight(getInstance(),flight5);
        airlineService.addFlight(getInstance(),flight6);
        airlineService.addFlight(getInstance(),flight7);
        airlineService.addFlight(getInstance(),flight8);

        Client client1=buildService.buildClient("1/Gheorghe/Cosmina/female/20/romanian");
        Client client2=buildService.buildClient("2/Georgescu/Elena/female/20/romanian");
        Client client3=buildService.buildClient("3/Neagu/Mara/female/20/romanian");
        Client client4=buildService.buildClient("4/Zaharia/Raluca/female/21/romanian");

        Employee emp1=buildService.buildEmployee("Pilot/Gheorghe/Cosmina/female/20/romanian/22-03-2002/7800/20");
        Employee emp2=buildService.buildEmployee("FlightAttendant/Gheorghe/Rodica/female/40/romanian/05-03-2012/5000/556");
        Employee emp3=buildService.buildEmployee("FlightAttendant/Serban/Ramona-Elena/female/19/romanian/12-10-2019/4000/556");
        Employee emp4=buildService.buildEmployee("FlightAttendant/Oprea/Anca/female/21/romanian/24-11-2022/3000/556");
        Employee emp5=buildService.buildEmployee("Pilot/Chitonu/Stefan-Alin/male/29/romanian/22-03-2002/7700/18");


        int i=0;
        for(Coupon c: client1.getCoupons()){
            if( c!=null){
                i=i+1;
            }
        }
        client1.getCoupons()[i]=new Coupon(client1.getIdPerson()+100+i,
                20,"30/12/2023");
        client1.getCoupons()[i+1]=new Coupon(client1.getIdPerson()+100+i+1,
                30,"30/11/2023");
        //clientService.sortCoupons(client1);

        buildService.buildBooking("Economy/1/2/3/33/1/1");
        /*
        buildService.buildBooking("Economy/1/2/3/33/1/1");
        buildService.buildBooking("Economy/1/2/3/33/1/1");
        buildService.buildBooking("Economy/1/2/3/33/1/1");
        buildService.buildBooking("Economy/1/2/3/33/1/1");
        buildService.buildBooking("Economy/1/2/3/33/1/1");
        buildService.buildBooking("Economy/1/2/3/33/1/1");
        buildService.buildBooking("Economy/1/2/3/33/1/1");
        buildService.buildBooking("Economy/1/2/3/33/1/1");
        buildService.buildBooking("Economy/1/2/3/33/1/1");
        buildService.buildBooking("Economy/1/2/3/33/1/1");
        buildService.buildBooking("Economy/1/2/3/33/1/1");
        buildService.buildBooking("Economy/1/2/3/33/1/1");
        buildService.buildBooking("Economy/1/2/3/33/1/1");
        buildService.buildBooking("Economy/1/2/3/33/1/1");
        buildService.buildBooking("Economy/1/2/3/33/1/1");
        buildService.buildBooking("Economy/1/2/3/33/1/1");
        buildService.buildBooking("Economy/1/2/3/33/1/1");
        buildService.buildBooking("Economy/1/2/3/33/1/1");
        buildService.buildBooking("Economy/1/2/3/33/1/1");
        buildService.buildBooking("Economy/1/2/3/33/1/1");
        buildService.buildBooking("Economy/1/2/3/33/1/1");
        buildService.buildBooking("Economy/1/2/3/33/1/1");
        buildService.buildBooking("Economy/1/2/3/33/1/1");
        buildService.buildBooking("Economy/1/2/3/33/1/1");
        buildService.buildBooking("Economy/1/2/3/33/1/1");
        buildService.buildBooking("Economy/1/2/3/33/1/1");
        buildService.buildBooking("Economy/1/2/3/33/1/1");
        buildService.buildBooking("Economy/1/2/3/33/1/1");
        buildService.buildBooking("Economy/1/2/3/33/1/1");
        buildService.buildBooking("Economy/1/2/3/33/1/1");
        buildService.buildBooking("Economy/1/2/3/33/1/1");
        buildService.buildBooking("Economy/1/2/3/33/1/1");
        buildService.buildBooking("Economy/1/2/3/33/1/1");
        buildService.buildBooking("Economy/1/2/3/33/1/1");
        buildService.buildBooking("Economy/1/2/3/33/1/1");
        buildService.buildBooking("Economy/1/2/3/33/1/1");
        buildService.buildBooking("Economy/1/2/3/33/1/1");
        buildService.buildBooking("Economy/1/2/3/33/1/1");
        buildService.buildBooking("Economy/1/2/3/33/1/1");
        buildService.buildBooking("Economy/1/2/3/33/1/1");
        buildService.buildBooking("FirstClass/1/2/3/33/1/1");
        buildService.buildBooking("FirstClass/1/2/3/33/1/1");
        buildService.buildBooking("FirstClass/1/2/3/33/1/1");
        buildService.buildBooking("FirstClass/1/2/3/33/1/1");
        buildService.buildBooking("FirstClass/1/2/3/33/1/1");
        buildService.buildBooking("FirstClass/1/2/3/33/1/1");
        buildService.buildBooking("FirstClass/1/2/3/33/1/1");
        buildService.buildBooking("FirstClass/1/2/3/33/1/1");
        buildService.buildBooking("FirstClass/1/2/3/33/1/1");
        buildService.buildBooking("FirstClass/1/2/3/33/1/1");
        buildService.buildBooking("FirstClass/1/2/3/33/1/1");
        buildService.buildBooking("FirstClass/1/2/3/33/1/1");
        buildService.buildBooking("FirstClass/1/2/3/33/1/1");
        buildService.buildBooking("FirstClass/1/2/3/33/1/1");
        buildService.buildBooking("FirstClass/1/2/3/33/1/1");
        buildService.buildBooking("FirstClass/1/2/3/33/1/1");
        buildService.buildBooking("FirstClass/1/2/3/33/1/1");
        buildService.buildBooking("FirstClass/1/2/3/33/1/1");
        buildService.buildBooking("FirstClass/1/2/3/33/1/1");
        buildService.buildBooking("FirstClass/1/2/3/33/1/1");
        buildService.buildBooking("FirstClass/1/2/3/33/1/1");
        buildService.buildBooking("FirstClass/1/2/3/33/1/1");
*/

        buildService.buildBooking("Economy/2/1/6/12/0/1");
        buildService.buildBooking("Economy/2/2/6/12/0/0");
        buildService.buildBooking("Economy/3/1/1/10/1/0");

        addFlightToEmployee(emp1,flight1);
        addFlightToEmployee(emp1,flight2);
        addFlightToEmployee(emp1,flight3);
        addFlightToEmployee(emp1,flight4);

    }
}
