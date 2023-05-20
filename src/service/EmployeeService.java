package service;
import model.*;
import service.*;

import java.util.*;

import repository.*;
public class EmployeeService {

    pilotRepository pilotRepository = new pilotRepository();
    flightAttendantRepository flightAttendantRepository = new flightAttendantRepository();

    public void removeEmployee(Employee employee) {
        Airline.getInstance().getEmployees().remove(employee);
    }

    public void addEmployee(Employee employee) {
        Airline.getInstance().getEmployees().add(employee);
    }

    public Employee findEmployee(String yes) {

            Scanner scanner = new Scanner(System.in);
            System.out.println("Write the Employee id: ");
            int id = scanner.nextInt();
            if (yes.equals("y")) {
                return pilotRepository.findPilotById(id);
            } else {
                FlightAttendant f=flightAttendantRepository.findFlightAttendantById(id);
                if(f!=null)
                {
                    return f;
                }
            }
            System.out.println("Wrong id.");
            return findEmployee(yes);


    }
    public void updateEmployeeDetails(Employee employee,int age, int salary, int years_nr,String yes) {
        if (employee != null) {
            if (age > 0) {
                employee.setAge(age);
            }
            if (salary > 0) {
                employee.setSalary(salary);
            }
            if (years_nr > 0) {
                if(yes.equals("y")){
                    ((Pilot) employee).setYearsOfExperience(years_nr);
                    pilotRepository.updatePilots((Pilot)employee);
                }
                else{
                    ((FlightAttendant) employee).setNrOfFlights(years_nr);
                    flightAttendantRepository.updateFlightAttendants((FlightAttendant) employee);
                }
            }
        } else {
            System.out.println("Employee not found.");
        }
    }



}
