package repository;

import config.DatabaseConnection;
import model.Coupon;
import model.Flight;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import repository.*;

public class couponRepository {
    private static couponRepository instance;

    private couponRepository() {
        // Private constructor to enforce singleton pattern
    }

    public static couponRepository getInstance() {
        if (instance == null) {
            instance = new couponRepository();
        }
        return instance;
    }

    public void addCoupon(Coupon coupon) {
        String sql = "INSERT INTO coupons (idCoupon,idClient, discountPercentage, expirationDate) VALUES (?,?,?,?)";
        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, coupon.getIdCoupon());
            statement.setInt(2, coupon.getIdClient());
            statement.setInt(3, coupon.getDiscountPercentage());
            statement.setString(4, coupon.getExpirationDate());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Coupon> findAllCoupons() {
        String sql = "SELECT idCoupon,idClient, discountPercentage, expirationDate,flightUsedONId FROM coupons";
        try {
            Connection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            List<Coupon> coupons = new ArrayList<>();
            while (resultSet.next()) {
                int idCoupon = resultSet.getInt("idCoupon");
                int idClient= resultSet.getInt("idClient");
                int discountPercentage = resultSet.getInt("discountPercentage");
                String expirationDate = resultSet.getString("expirationDate");
                int flightUsedONId = resultSet.getInt("flightUsedONId");
                Flight flightUsedON= flightRepository.getInstance().findFlightById(flightUsedONId);
                Coupon coupon = new Coupon(idCoupon,idClient, discountPercentage, expirationDate,flightUsedON);
                coupons.add(coupon);
            }
            return coupons;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public Coupon findCouponById(int id) {
        try {
            String sql = "SELECT idCoupon,idClient, discountPercentage, expirationDate,flightUsedONId FROM coupons WHERE idCoupon = ?";
            Connection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int discountPercentage = resultSet.getInt("discountPercentage");
                int idClient= resultSet.getInt("idClient");
                String expirationDate = resultSet.getString("expirationDate");
                int flightUsedONId = resultSet.getInt("flightUsedONId");
                Flight flightUsedON= flightRepository.getInstance().findFlightById(flightUsedONId);
                Coupon coupon = new Coupon(id,idClient, discountPercentage, expirationDate,flightUsedON);
                return coupon;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateCoupon(Coupon coupon) {
        String sql = "UPDATE coupons SET discountPercentage = ?, expirationDate = ? WHERE idCoupon = ?";
        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, coupon.getDiscountPercentage());
            statement.setString(2, coupon.getExpirationDate());
            statement.setInt(3, coupon.getIdCoupon());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCoupon(int id) {
        String sql = "DELETE FROM coupons WHERE idCoupon = ?";
        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void assignFlightToCoupon(int couponId, Flight flight) {
        String sql = "UPDATE coupons SET flightUsedONId = ? WHERE idCoupon = ?";
        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, flight.getIdFlight());
            statement.setInt(2, couponId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Flight findFlightByCouponId(int couponId) {
        try {
            String sql = "SELECT flightUsedONId FROM coupons WHERE idCoupon = ?";
            Connection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, couponId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int flightUsedONId = resultSet.getInt("flightUsedONId");
                return flightRepository.getInstance().findFlightById(flightUsedONId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}