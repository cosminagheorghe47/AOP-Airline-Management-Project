package model;

import java.util.Arrays;

public class Client extends Person{
    //lista cupoane, zboruri

    private Coupon[] coupons=new Coupon[10]; //compozitie

    public Client(){
        super();
    }
    public Client(int id,String lastName, String firstName, String gender, int age, String nationality) {
        super(id,lastName, firstName, gender, age, nationality);
        /*
        for(int i=coupons.length-1;i>=0;i--)
        {
            Coupon coupon = new Coupon(coupons[i]);
            this.coupons[i]=coupon;
        }*/
    }

    public Coupon[] getCoupons() {
        return coupons;
    }

    public void setCoupons(Coupon[] coupons) {
        this.coupons = coupons;
    }

    @Override
    public String toString() {
        return "Client"  +
                super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client client)) return false;
        if (!super.equals(o)) return false;
        return Arrays.equals(coupons, client.coupons);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Arrays.hashCode(coupons);
        return result;
    }

    @Override
    public boolean isEmployee()
    {
        return false;
    }
}
