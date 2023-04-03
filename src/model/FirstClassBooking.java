package model;

public class FirstClassBooking extends Booking{
    public boolean isVegetarian;
    private static double priceMultiply=2;

    public FirstClassBooking(int id, Flight flight, Client client, int seat, int row, int nrOfBaggages, Boolean isVegetarian) {
        super(id,flight, client, seat, row, nrOfBaggages);
        this.isVegetarian = isVegetarian;
        //flight.incSoldFirst();
    }
    public void incSoldFirstClass(Flight flight)
    {
        flight.setSoldSeatsFirstClass(flight.getSoldSeatsFirstClass()+1);
    }
    public Boolean getVegetarian() {
        return isVegetarian;
    }

    public void setVegetarian(Boolean vegetarian) {
        isVegetarian = vegetarian;
    }

    public double price()
    {
        return this.getFlight().getDistance()*priceMultiply;
    }

    @Override
    public String toString() {
        return "FirstClassBooking" + //super.toString()+
                " "+
                super.toString() +
                " isVegetarian=" + isVegetarian +
                '.';
    }
}
