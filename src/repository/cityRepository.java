package repository;

import config.DatabaseConnection;
import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class cityRepository {
    private static cityRepository single_instance = null;
    public cityRepository(){}
    public static synchronized cityRepository getInstance(){
        if(single_instance == null){
            single_instance = new cityRepository();
        }
        return single_instance;
    }
    public static void addCityDB(City city) {
        String sql = "INSERT INTO cities (idCity, cityName) VALUES (?,?)";
        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, city.getIdCity());
            statement.setString(2, city.getCityName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<City> findAllCities() {
        String sql = "SELECT c.idCity, c.cityName FROM cities c";
        try {
            Connection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            List<City> cities = new ArrayList<>();
            while (resultSet.next()) {
                cities.add(City.fromResultSet(resultSet));
            }
            return cities;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public City findCityByID(int id) {
        try {
            String sql = "SELECT a.idCity, a.cityName FROM cities a WHERE a.idCity = ?";
            Connection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                return City.fromResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public City findCityByName(String name) {
        try {
            String sql = "SELECT a.idCity, a.cityName FROM cities a WHERE LOWER(a.cityName) = LOWER(?) ";
            Connection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                return City.fromResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void updateCity(City city) {
        String sql = "UPDATE cities SET cityName = ? WHERE idCity= ?";
        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, city.getCityName());
            statement.setInt(2, city.getIdCity());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void deleteCityDB(int id) {
        String sql = "DELETE FROM cities WHERE idCity=?";
        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
