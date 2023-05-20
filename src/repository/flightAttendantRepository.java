package repository;

import config.DatabaseConnection;
import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class flightAttendantRepository {
    private static flightAttendantRepository single_instance = null;
    public flightAttendantRepository(){}
    public static synchronized flightAttendantRepository getInstance(){
        if(single_instance == null){
            single_instance = new flightAttendantRepository();
        }
        return single_instance;
    }
    public void addFlightAttendantDB(FlightAttendant flightAttendant) {
        String sql = "INSERT INTO flightAttendants (id, lastName, firstName, gender, age, nationality, hireDate, salary,nrOfFlights) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)";
        try {
            Connection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, flightAttendant.getIdPerson());
            statement.setString(2, flightAttendant.getLastName());
            statement.setString(3, flightAttendant.getFirstName());
            statement.setString(4, flightAttendant.getGender());
            statement.setInt(5, flightAttendant.getAge());
            statement.setString(6, flightAttendant.getNationality());
            statement.setString(7, flightAttendant.getHireDate());
            statement.setInt(8, flightAttendant.getSalary());
            statement.setInt(9, flightAttendant.getNrOfFlights());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<FlightAttendant> findAllFlightAttendants() {
        List<FlightAttendant> flightAttendants = new ArrayList<>();
        String sql = "SELECT * FROM flightAttendants";
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
                int nrOfFlights = resultSet.getInt("nrOfFlights");
                FlightAttendant flightAttendant = new FlightAttendant(employeeId, lastName, firstName, gender, age, nationality, hireDate, salary, nrOfFlights);
                flightAttendants.add(flightAttendant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flightAttendants;
    }

    public FlightAttendant findFlightAttendantById(int id) {
        String sql = "SELECT * FROM flightAttendants WHERE id = ?";
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
                int nrOfFlights = resultSet.getInt("nrOfFlights");
                return new FlightAttendant(employeeId, lastName, firstName, gender, age, nationality, hireDate, salary,nrOfFlights);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void updateFlightAttendants(FlightAttendant flightAttendant) {
        String sql = "UPDATE flightAttendants SET  age = ?,  salary = ?,nrOfFlight=? WHERE idPerson = ?";
        try {
            Connection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, flightAttendant.getAge());
            statement.setInt(2, flightAttendant.getSalary());
            statement.setInt(3, flightAttendant.getNrOfFlights());
            statement.setInt(4, flightAttendant.getIdPerson());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteFlightAttendants(int id) {
        String sql = "DELETE FROM flightAttendants WHERE id = ?";
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

