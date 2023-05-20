package repository;

import config.DatabaseConnection;
import model.*;
import service.Airline;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class aircraftRepository {
    private static aircraftRepository single_instance = null;
    public aircraftRepository(){}
    public static synchronized aircraftRepository getInstance(){
        if(single_instance == null){
            single_instance = new aircraftRepository();
        }
        return single_instance;
    }
    public static void addAircraftDB(Aircraft aircraft) {
        String sql = "INSERT INTO aircrafts (idAircraft, name, nrOfSeatsEconomy, nrOfSeatsFirstClass, maxSpeed, weight, wingSpan) VALUES (?,?,?,?,?,?,?)";
        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, aircraft.getIdAircraft());
            statement.setString(2, aircraft.getName());
            statement.setInt(3, aircraft.getNrOfSeatsEconomy());
            statement.setInt(4, aircraft.getNrOfSeatsFirstClass());
            statement.setInt(5, aircraft.getMaxSpeed());
            statement.setInt(6, aircraft.getWeight());
            statement.setInt(7, aircraft.getWingSpan());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Aircraft[] findAllAircrafts() {
        String sql = "SELECT a.idAircraft, a.name, a.nrOfSeatsEconomy, a.nrOfSeatsFirstClass, " +
                            "a.maxSpeed, a.weight, a.wingSpan FROM aircrafts a";
        try {
            Connection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            List<Aircraft> aircrafts = new ArrayList<>();
            while (resultSet.next()) {
                aircrafts.add(Aircraft.fromResultSet(resultSet));
            }
            return aircrafts.toArray(Aircraft[]::new);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Aircraft[0];
    }

    public Aircraft findAircraftByID(int id) {
        try {
            String sql = "SELECT a.idAircraft, a.name, a.nrOfSeatsEconomy, a.nrOfSeatsFirstClass, " +
                    "a.maxSpeed, a.weight, a.wingSpan FROM aircrafts a WHERE a.idAircraft = ?";
            Connection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                return Aircraft.fromResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void updateAircraft(Aircraft aircraft) {
        String sql = "UPDATE aircrafts SET name = ?, nrOfSeatsEconomy = ?, nrOfSeatsFirstClass = ? WHERE idAircraft = ?";
        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, aircraft.getName());
            statement.setInt(2, aircraft.getNrOfSeatsEconomy());
            statement.setInt(3, aircraft.getNrOfSeatsFirstClass());
            statement.setInt(4, aircraft.getIdAircraft());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteAircraftDB(int aircraftId) {
        String sql = "DELETE FROM aircrafts WHERE idAircraft=?";
        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, aircraftId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
