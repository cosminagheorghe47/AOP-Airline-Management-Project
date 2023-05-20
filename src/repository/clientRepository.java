package repository;

import config.DatabaseConnection;
import model.Pilot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.*;

public class clientRepository {
    private static clientRepository single_instance = null;
    public clientRepository(){}
    public static synchronized clientRepository getInstance(){
        if(single_instance == null){
            single_instance = new clientRepository();
        }
        return single_instance;
    }
    public void addClientDB(Client client) {
        String sql = "INSERT INTO clients (id, lastName, firstName, gender, age, nationality) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try {
            Connection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, client.getIdPerson());
            statement.setString(2, client.getLastName());
            statement.setString(3, client.getFirstName());
            statement.setString(4, client.getGender());
            statement.setInt(5, client.getAge());
            statement.setString(6, client.getNationality());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Client> findAllClients() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM clients";
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
                Client client = new Client(employeeId, lastName, firstName, gender, age, nationality);
                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    public Client findClientById(int id) {
        String sql = "SELECT * FROM clients WHERE id = ?";
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
                return new Client(employeeId, lastName, firstName, gender, age, nationality);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void updateClientsAge(Client client) {
        String sql = "UPDATE clients SET  age = ? WHERE id = ?";
        try {
            Connection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, client.getAge());
            statement.setInt(2, client.getIdPerson());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Coupon[] findCouponsByClientId(int clientId) {
        List<Coupon> coupons = new ArrayList<>();
        String sql = "SELECT idCoupon, discountPercentage, expirationDate FROM coupons WHERE idClient = ?";
        try {
            Connection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt( 1, clientId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int idCoupon = resultSet.getInt("idCoupon");
                int discountPercentage = resultSet.getInt("discountPercentage");
                String expirationDate = resultSet.getString("expirationDate");
                Coupon coupon = new Coupon(idCoupon, clientId, discountPercentage, expirationDate);
                coupons.add(coupon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return coupons.toArray(Coupon[]::new);
    }


    public void deleteClients(int id) {
        String sql = "DELETE FROM clients WHERE id = ?";
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
