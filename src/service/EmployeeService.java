package service;

import model.Client;
import model.Employee;

public class EmployeeService {
    public void removeEmployee(Employee employee){
        Airline.getInstance().getEmployees().remove(employee);
    }
}
