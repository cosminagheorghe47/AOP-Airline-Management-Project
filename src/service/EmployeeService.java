package service;
import service.*;
import model.Client;
import model.Employee;
import model.Flight;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class EmployeeService {
    public void removeEmployee(Employee employee) {
        Airline.getInstance().getEmployees().remove(employee);
    }
    public static Employee findEmployee(){
        try{
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write the Employee id: ");
        int id = scanner.nextInt();
        for(Employee c : Airline.getInstance().getEmployees()){
            if(c!=null && c.getIdPerson()==id){
                return c;
            }
        }}
        catch(InputMismatchException a){
            System.out.println("Wrong id.");
            return findEmployee();}
        System.out.println("Wrong id.");
        return findEmployee();
    }

}
