package model;

public class EconomyBooking extends Booking{
    private boolean hasPriority;
    private static double priceMultiply=0.1;

    public EconomyBooking(int id, Flight flight, Client client, int seat, int row, int nrOfBaggages, boolean hasPriority) {
        super(id,flight, client, seat, row, nrOfBaggages);
        this.hasPriority = hasPriority;
        //flight.incSoldEconomy();
    }
    public void incSoldEconomy(Flight flight)
    {
        flight.setSoldSeatsEconomy(flight.getSoldSeatsEconomy()+1);
    }
    public boolean getHasPriority() {
        return hasPriority;
    }

    public void setHasPriority(boolean hasPriority) {
        this.hasPriority = hasPriority;
    }

    public double price()
    {
        if(hasPriority==false)
        {
            return this.getFlight().getDistance()*priceMultiply;
        }
        else
        {
            return this.getFlight().getDistance()*priceMultiply+200;
        }
    }

    @Override
    public String toString() {
        return "EconomyBooking " +//super.toString()+
                " "+
                super.toString() +
                " hasPriority=" + hasPriority +
                '.';
    }
}
