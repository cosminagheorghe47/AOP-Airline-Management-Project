package service;
import service.*;
import model.Client;
import model.Employee;
import model.Flight;

import java.util.ArrayList;
import java.util.Scanner;

public class EmployeeService {
    public void removeEmployee(Employee employee) {
        Airline.getInstance().getEmployees().remove(employee);
    }
    public static Employee findEmployee(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write the Employee id: ");
        int id = scanner.nextInt();
        for(Employee c : Airline.getInstance().getEmployees()){
            if(c!=null && c.getIdPerson()==id){
                return c;
            }
        }
        return null;
    }

}
