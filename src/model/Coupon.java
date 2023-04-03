package model;

import java.util.Objects;

public class Coupon implements Comparable<Coupon>{
    private int idCoupon;
    private int discountPercentage;
    private String expirationDate;
    private Flight flightUsedON=null;

    public Coupon(int idCoupon,int discountPercentage, String expirationDate) {
        this.idCoupon=idCoupon;
        this.discountPercentage = discountPercentage;
        this.expirationDate = expirationDate;
    }
    public Coupon(Coupon another)
    {
        this(another.idCoupon,another.discountPercentage,another.expirationDate);
    }

    public int getIdCoupon() {
        return idCoupon;
    }

    public void setIdCoupon(int idCoupon) {
        this.idCoupon = idCoupon;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public Flight getFlightUsedON() {
        return flightUsedON;
    }

    public void setFlightUsedON(Flight flightUsedON) {
        this.flightUsedON = flightUsedON;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return "Coupon➙" +
                "discountPercentage=" + discountPercentage +
                ", expirationDate='" + expirationDate + '\'' +
                '.';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coupon coupon)) return false;
        return idCoupon == coupon.idCoupon && discountPercentage == coupon.discountPercentage && Objects.equals(expirationDate, coupon.expirationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCoupon, discountPercentage, expirationDate);
    }

    @Override
    public int compareTo(Coupon o) {
        if(this.discountPercentage<o.discountPercentage){
            return 1;
        }
        else if(this.discountPercentage==o.discountPercentage) {
            return 0;
        }
        else{
            return -1;
        }
    }

}
