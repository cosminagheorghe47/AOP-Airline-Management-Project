package repository;
import config.DatabaseConnection;
import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import repository.*;
public class firstClassBookingRepository {
    private static firstClassBookingRepository instance;

    private firstClassBookingRepository() {
        // Private constructor to enforce singleton pattern
    }

    public static firstClassBookingRepository getInstance() {
        if (instance == null) {
            instance = new firstClassBookingRepository();
        }
        return instance;
    }

    public void addFirstClassBooking(FirstClassBooking firstClassBooking) {
        String sql = "INSERT INTO firstclassbookings (idBooking, flightId, clientId, seat, row, nrOfBaggages, isVegetarian) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, firstClassBooking.getIdBooking());
            statement.setInt(2, firstClassBooking.getFlight().getIdFlight());
            statement.setInt(3, firstClassBooking.getClient().getIdPerson());
            statement.setInt(4, firstClassBooking.getSeat());
            statement.setInt(5, firstClassBooking.getRow());
            statement.setInt(6, firstClassBooking.getNrOfBaggages());
            statement.setBoolean(7, firstClassBooking.getVegetarian());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<FirstClassBooking> findAllFirstClassBookings() {
        String sql = "SELECT * FROM firstclassbookings";
        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            List<FirstClassBooking> firstClassBookings = new ArrayList<>();
            while (resultSet.next()) {
                int idBooking = resultSet.getInt("idBooking");
                int flightId = resultSet.getInt("flightId");
                int clientId = resultSet.getInt("clientId");
                int seat = resultSet.getInt("seat");
                int row = resultSet.getInt("row");
                int nrOfBaggages = resultSet.getInt("nrOfBaggages");
                boolean isVegetarian = resultSet.getBoolean("isVegetarian");
                Flight flight = flightRepository.getInstance().findFlightById(flightId);
                Client client = clientRepository.getInstance().findClientById(clientId);
                FirstClassBooking firstClassBooking = new FirstClassBooking(idBooking, flight, client, seat, row, nrOfBaggages, isVegetarian);
                firstClassBookings.add(firstClassBooking);
            }
            return firstClassBookings;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    public FirstClassBooking findFirstClassBookingById(int id) {
        String sql = "SELECT * FROM firstclassbookings WHERE idBooking = ?";
        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int flightId = resultSet.getInt("flightId");
                int clientId = resultSet.getInt("clientId");
                int seat = resultSet.getInt("seat");
                int row = resultSet.getInt("row");
                int nrOfBaggages = resultSet.getInt("nrOfBaggages");
                boolean isVegetarian = resultSet.getBoolean("isVegetarian");
                Flight flight = flightRepository.getInstance().findFlightById(flightId);
                Client client = clientRepository.getInstance().findClientById(clientId);
                return new FirstClassBooking(id, flight, client, seat, row, nrOfBaggages, isVegetarian);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateFirstClassBooking(FirstClassBooking firstClassBooking) {
        String sql = "UPDATE firstclassbookings SET seat = ?, row = ?, nrOfBaggages = ?, isVegetarian = ? " +
                "WHERE idBooking = ?";
        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, firstClassBooking.getSeat());
            statement.setInt(2, firstClassBooking.getRow());
            statement.setInt(3, firstClassBooking.getNrOfBaggages());
            statement.setBoolean(4, firstClassBooking.getVegetarian());
            statement.setInt(5, firstClassBooking.getIdBooking());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteFirstClassBooking(int id) {
        String sql = "DELETE FROM firstclassbookings WHERE idBooking = ?";
        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
