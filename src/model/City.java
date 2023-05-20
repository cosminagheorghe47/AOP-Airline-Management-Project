package model;

import java.util.Objects;
import java.sql.ResultSet;
import java.sql.SQLException;

public class City {
    private int idCity;
    private String cityName;

    public City(){}
    public City(int idCity,String cityName)
    {
        this.cityName=cityName;
        this.idCity=idCity;
    }

    public int getIdCity() {
        return idCity;
    }

    public void setIdCity(int idCity) {
        this.idCity = idCity;
    }

    public String getCityName() {
        return cityName;
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof City city)) return false;
        return Objects.equals(cityName, city.cityName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cityName);
    }

    @Override
    public String toString() {
        return
                 cityName;
    }
    public static City fromResultSet(ResultSet resultSet) throws SQLException {
        return new City(resultSet.getInt(1), resultSet.getString(2));
    }
}
