package model;

public class Employee extends Person {
    private String hireDate;
    private int salary;

    public Employee(int id,String lastName, String firstName, String gender, int age, String nationality, String hireDate, int salary) {
        super(id,lastName, firstName, gender, age, nationality);
        this.hireDate = hireDate;
        this.salary = salary;
    }
    public String getHireDate() {
        return hireDate;
    }

    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "The employee " + super.toString() + " started to work on " + hireDate+ " and has a salary of " + salary + "EUR";
    }
    @Override
    public boolean isEmployee()
    {
        return true;
    }
}