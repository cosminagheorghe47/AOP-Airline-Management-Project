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
public class economyBookingRepository {
    private static economyBookingRepository instance;

    private economyBookingRepository() {
        // Private constructor to enforce singleton pattern
    }

    public static economyBookingRepository getInstance() {
        if (instance == null) {
            instance = new economyBookingRepository();
        }
        return instance;
    }

    public void addEconomyBooking(EconomyBooking economyBooking) {
        String sql = "INSERT INTO economybookings (idBooking, flightId, clientId, seat, row, nrOfBaggages, hasPriority) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, economyBooking.getIdBooking());
            statement.setInt(2, economyBooking.getFlight().getIdFlight());
            statement.setInt(3, economyBooking.getClient().getIdPerson());
            statement.setInt(4, economyBooking.getSeat());
            statement.setInt(5, economyBooking.getRow());
            statement.setInt(6, economyBooking.getNrOfBaggages());
            statement.setBoolean(7, economyBooking.getHasPriority());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<EconomyBooking> findAllEconomyBookings() {
        String sql = "SELECT * FROM economybookings";
        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            List<EconomyBooking> economyBookings = new ArrayList<>();
            while (resultSet.next()) {
                int idBooking = resultSet.getInt("idBooking");
                int flightId = resultSet.getInt("flightId");
                int clientId = resultSet.getInt("clientId");
                int seat = resultSet.getInt("seat");
                int row = resultSet.getInt("row");
                int nrOfBaggages = resultSet.getInt("nrOfBaggages");
                boolean hasPriority = resultSet.getBoolean("hasPriority");
                Flight flight = flightRepository.getInstance().findFlightById(flightId);
                Client client = clientRepository.getInstance().findClientById(clientId);
                EconomyBooking economyBooking = new EconomyBooking(idBooking, flight, client, seat, row, nrOfBaggages, hasPriority);
                economyBookings.add(economyBooking);
            }
            return economyBookings;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public EconomyBooking findEconomyBookingById(int id) {
        String sql = "SELECT * FROM economybookings WHERE idBooking = ?";
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
                boolean hasPriority = resultSet.getBoolean("hasPriority");
                Flight flight = flightRepository.getInstance().findFlightById(flightId);
                Client client = clientRepository.getInstance().findClientById(clientId);
                return new EconomyBooking(id, flight, client, seat, row, nrOfBaggages, hasPriority);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void updateEconomyBooking(EconomyBooking economyBooking) {
        String sql = "UPDATE economybookings SET  seat = ?, row = ?, nrOfBaggages = ?, hasPriority = ? " +
                "WHERE idBooking = ?";
        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, economyBooking.getSeat());
            statement.setInt(2, economyBooking.getRow());
            statement.setInt(3, economyBooking.getNrOfBaggages());
            statement.setBoolean(4, economyBooking.getHasPriority());
            statement.setInt(5, economyBooking.getIdBooking());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteEconomyBooking(int id) {
        String sql = "DELETE FROM economybookings WHERE idBooking = ?";
        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
