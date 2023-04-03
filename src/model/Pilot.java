package model;

public class Pilot extends Employee{
    private int yearsOfExperience;
    //lista aeronave

    public Pilot(int id, String lastName, String firstName, String gender, int age, String nationality, String hireDate, int salary, int yearsOfExperience) {
        super(id,lastName, firstName, gender, age, nationality, hireDate, salary);
        this.yearsOfExperience = yearsOfExperience;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    @Override
    public String toString() {
        return super.toString() +
                " as a Pilot, having "+yearsOfExperience+" years of experience.";
    }
}
