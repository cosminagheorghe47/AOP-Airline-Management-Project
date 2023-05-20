package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.UUID;

public class Aircraft {
    private int idAircraft;
    private String name;
    private int nrOfSeatsEconomy;
    private int nrOfSeatsFirstClass;
    private int maxSpeed;
    private int weight;
    private int wingSpan;

    public Aircraft(){}
    public Aircraft(int idAircraft, String name, int nrOfSeatsEconomy, int nrOfSeatsFirstClass, int maxSpeed, int weight, int wingSpan) {
        this.idAircraft = idAircraft;
        this.name = name;
        this.nrOfSeatsEconomy = nrOfSeatsEconomy;
        this.nrOfSeatsFirstClass = nrOfSeatsFirstClass;
        this.maxSpeed = maxSpeed;
        this.weight = weight;
        this.wingSpan = wingSpan;
    }

    public int getIdAircraft() {
        return idAircraft;
    }

    public int getNrOfSeatsEconomy() {
        return nrOfSeatsEconomy;
    }

    public void setNrOfSeatsEconomy(int nrOfSeatsEconomy) {
        this.nrOfSeatsEconomy = nrOfSeatsEconomy;
    }

    public int getNrOfSeatsFirstClass() {
        return nrOfSeatsFirstClass;
    }

    public void setNrOfSeatsFirstClass(int nrOfSeatsFirstClass) {
        this.nrOfSeatsFirstClass = nrOfSeatsFirstClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getMaxSpeed() {
        return maxSpeed;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWingSpan() {
        return wingSpan;
    }

    @Override
    public String toString() {
        return "Aircraft " +
                 name + ", details: " +
                "id=" + idAircraft +
                ", Number of Economy Seats=" + nrOfSeatsEconomy +
                ", and FirstClass=" + nrOfSeatsFirstClass +
                ", maxSpeed=" + maxSpeed +
                ", weight=" + weight +
                ", wingSpan=" + wingSpan ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Aircraft aircraft)) return false;
        return idAircraft == aircraft.idAircraft && name.equals(aircraft.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAircraft, name);
    }

    public static Aircraft fromResultSet(ResultSet resultSet) throws SQLException {
        return new Aircraft(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3),
                resultSet.getInt(4), resultSet.getInt(5), resultSet.getInt(6), resultSet.getInt(7));
    }
}
