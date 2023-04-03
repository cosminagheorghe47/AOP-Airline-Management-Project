package model;

import java.util.Objects;

public abstract class Person {
    protected int idPerson;
    protected String lastName;
    protected String firstName;
    protected String gender;
    protected int age;
    protected String nationality;

    protected Person(){}

    protected Person(int id,String lastName, String firstName, String gender, int age, String nationality) {
        this.idPerson=id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.gender = gender;
        this.age = age;
        this.nationality=nationality;
    }

    public int getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(int idPerson) {
        this.idPerson = idPerson;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public abstract boolean isEmployee();

    @Override
    public String toString() {
        return  "➙"+idPerson + ") "+ lastName + ' ' +
                firstName +
                " is a : " + gender +
                " having the age of " + age + " years";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person person)) return false;
        return idPerson == person.idPerson && lastName.equals(person.lastName) && firstName.equals(person.firstName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPerson, lastName, firstName);
    }
}
