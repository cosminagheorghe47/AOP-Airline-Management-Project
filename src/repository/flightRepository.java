package repository;

import config.DatabaseConnection;
import model.City;
import model.Flight;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.*;

public class flightRepository {
    private static flightRepository instance;

    public flightRepository() {
    }

    public static synchronized flightRepository getInstance() {
        if (instance == null) {
            instance = new flightRepository();
        }
        return instance;
    }

    public void addFlight(Flight flight) {
        String sql = "INSERT INTO flights (idFlight,aircraftId, departureTime, departureDate, departureCityId, " +
                "arrivalTime, arrivalDate, arrivalCityId, distance, soldSeatsEconomy, soldSeatsFirstClass) " +
                "VALUES (?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, flight.getIdFlight());
            statement.setInt(2, flight.getAircraft().getIdAircraft());
            statement.setString(3, flight.getDepartureTime());
            statement.setString(4, flight.getDepartureDate());
            statement.setInt(5, flight.getDepartureCity().getIdCity());
            statement.setString(6, flight.getArrivalTime());
            statement.setString(7, flight.getArrivalDate());
            statement.setInt(8, flight.getArrivalCity().getIdCity());
            statement.setInt(9, flight.getDistance());
            statement.setInt(10, flight.getSoldSeatsEconomy());
            statement.setInt(11, flight.getSoldSeatsFirstClass());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Flight> findAllFlights() {
        String sql = "SELECT idFlight,aircraftId, departureTime, departureDate, departureCityId, " +
                "arrivalTime, arrivalDate, arrivalCityId, distance, soldSeatsEconomy, soldSeatsFirstClass " +
                "FROM flights";
        try {
            Connection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            List<Flight> flights = new ArrayList<>();
            while (resultSet.next()) {
                flights.add(Flight.fromResultSet(resultSet));
            }
            return flights;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Flight findFlightById(int id) {
        String sql = "SELECT idFlight,aircraftId, departureTime, departureDate, departureCityId, " +
                "arrivalTime, arrivalDate, arrivalCityId, distance, soldSeatsEconomy, soldSeatsFirstClass " +
                "FROM flights WHERE idFlight = ?";
        try {
            Connection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Flight.fromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateFlight(Flight flight) {
        String sql = "UPDATE flights SET aircraftId=?, departureTime=?, departureDate=?, " +
                "arrivalTime=?, arrivalDate=?,  soldSeatsEconomy=?, soldSeatsFirstClass=? " +
                "WHERE idFlight=?";
        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, flight.getAircraft().getIdAircraft());
            statement.setString(2, flight.getDepartureTime());
            statement.setString(3, flight.getDepartureDate());
            statement.setString(4, flight.getArrivalTime());
            statement.setString(5, flight.getArrivalDate());
            statement.setInt(6, flight.getSoldSeatsEconomy());
            statement.setInt(7, flight.getSoldSeatsFirstClass());
            statement.setInt(8, flight.getIdFlight());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<City> getCitiesByFlightId(int idFlight) {
        List<City> stops = new ArrayList<>();
        String sql="SELECT c.* FROM cities c " +
        "JOIN FlightCity fc ON fc.idCity = c.idCity " +
        "WHERE fc.idFlight = ?";
        try  {
            Connection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idFlight);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int idCity = resultSet.getInt("idCity");
                String cityName = resultSet.getString("cityName");

                City city = new City(idCity, cityName);
                stops.add(city);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stops;
    }
    public void addFlightCity(int flightId, int cityId) {
        String sql = "INSERT INTO FlightCity (idFlight, idCity) VALUES (?, ?)";
        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, flightId);
            statement.setInt(2, cityId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<Booking> findBookingsByFlightId(int flightId) {
        ArrayList<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM economybookings WHERE flightId = ?";
        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, flightId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int bookingId = resultSet.getInt("idBooking");
                int clientId = resultSet.getInt("clientId");
                int seat = resultSet.getInt("seat");
                int row = resultSet.getInt("row");
                int nrOfBaggages = resultSet.getInt("nrOfBaggages");

                    boolean hasPriority = resultSet.getBoolean("hasPriority");
                    Flight flight = flightRepository.getInstance().findFlightById(flightId);
                    Client client = clientRepository.getInstance().findClientById(clientId);
                    EconomyBooking economyBooking = new EconomyBooking(bookingId, flight, client, seat, row, nrOfBaggages,hasPriority);
                    bookings.add(economyBooking);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql2 = "SELECT * FROM firstclassbookings WHERE flightId = ?";
        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement statement = connection.prepareStatement(sql2)) {
            statement.setInt(1, flightId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int bookingId = resultSet.getInt("idBooking");
                int clientId = resultSet.getInt("clientId");
                int seat = resultSet.getInt("seat");
                int row = resultSet.getInt("row");
                int nrOfBaggages = resultSet.getInt("nrOfBaggages");
                    boolean isVegetarian = resultSet.getBoolean("isVegetarian");
                    Flight flight = flightRepository.getInstance().findFlightById(flightId);
                    Client client = clientRepository.getInstance().findClientById(clientId);
                    FirstClassBooking firstClassBooking = new FirstClassBooking(bookingId, flight, client, seat, row, nrOfBaggages, isVegetarian);
                    bookings.add(firstClassBooking);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }
    public void increaseSoldSeatsEconomy(int flightId ) {
        String sql = "UPDATE flights SET soldSeatsEconomy = soldSeatsEconomy + 1 WHERE idFlight = ?";
        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, flightId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void increaseSoldSeatsFirstClass(int flightId) {
        String sql = "UPDATE flights SET soldSeatsFirstClass = soldSeatsFirstClass + 1 WHERE idFlight = ?";
        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, flightId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void decreaseSoldSeatsEconomy(int flightId ) {
        String sql = "UPDATE flights SET soldSeatsEconomy = soldSeatsEconomy -1 WHERE idFlight = ?";
        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, flightId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void decreaseSoldSeatsFirstClass(int flightId) {
        String sql = "UPDATE flights SET soldSeatsFirstClass = soldSeatsFirstClass -1 WHERE idFlight = ?";
        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, flightId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteFlight(int flightId) {
        String sql = "DELETE FROM flights WHERE idFlight=?";
        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, flightId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}