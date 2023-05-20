package repository;

import config.DatabaseConnection;
import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class pilotRepository {
    private static pilotRepository single_instance = null;
    public pilotRepository(){}
    public static synchronized pilotRepository getInstance(){
        if(single_instance == null){
            single_instance = new pilotRepository();
        }
        return single_instance;
    }
    public void addPilotDB(Pilot pilot) {
        String sql = "INSERT INTO pilots (id, lastName, firstName, gender, age, nationality, hireDate, salary,yearsOfExperience) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)";
        try {
            Connection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, pilot.getIdPerson());
            statement.setString(2, pilot.getLastName());
            statement.setString(3, pilot.getFirstName());
            statement.setString(4, pilot.getGender());
            statement.setInt(5, pilot.getAge());
            statement.setString(6, pilot.getNationality());
            statement.setString(7, pilot.getHireDate());
            statement.setInt(8, pilot.getSalary());
            statement.setInt(9, pilot.getYearsOfExperience());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Pilot> findAllPilots() {
        List<Pilot> pilots = new ArrayList<>();
        String sql = "SELECT * FROM pilots";
        try {
            Connection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int employeeId = resultSet.getInt("id");
                String lastName = resultSet.getString("lastName");
                String firstName = resultSet.getString("firstName");
                String gender = resultSet.getString("gender");
                int age = resultSet.getInt("age");
                String nationality = resultSet.getString("nationality");
                String hireDate = resultSet.getString("hireDate");
                int salary = resultSet.getInt("salary");
                int yearsOfExperience = resultSet.getInt("yearsOfExperience");
                Pilot pilot = new Pilot(employeeId, lastName, firstName, gender, age, nationality, hireDate, salary, yearsOfExperience);
                pilots.add(pilot);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pilots;
    }

    public Pilot findPilotById(int id) {
        String sql = "SELECT * FROM pilots WHERE id = ?";
        try {
            Connection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int employeeId = resultSet.getInt("id");
                String lastName = resultSet.getString("lastName");
                String firstName = resultSet.getString("firstName");
                String gender = resultSet.getString("gender");
                int age = resultSet.getInt("age");
                String nationality = resultSet.getString("nationality");
                String hireDate = resultSet.getString("hireDate");
                int salary = resultSet.getInt("salary");
                int yearsOfExperience = resultSet.getInt("yearsOfExperience");
                return new Pilot(employeeId, lastName, firstName, gender, age, nationality, hireDate, salary,yearsOfExperience);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void updatePilots(Pilot pilot) {
        String sql = "UPDATE pilots SET  age = ?,  salary = ?,yearsOfExperience=? WHERE id = ?";
        try {
            Connection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, pilot.getAge());
            statement.setInt(2, pilot.getSalary());
            statement.setInt(3, pilot.getYearsOfExperience());
            statement.setInt(4, pilot.getIdPerson());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePilots(int id) {
        String sql = "DELETE FROM pilots WHERE id = ?";
        try {
            Connection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


