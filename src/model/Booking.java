package model;

public abstract class Booking {
    private int idBooking;
    private Flight flight;
    private Client client;
    private int seat;
    private int row;
    //private int finalPrice;
    private int nrOfBaggages;

    public Booking(int idBooking,Flight flight, Client client, int seat, int row, int nrOfBaggages) {
        this.idBooking=idBooking;
        this.flight = flight;
        this.client = client;
        this.seat = seat;
        this.row = row;
        this.nrOfBaggages = nrOfBaggages;

    }

    public int getIdBooking() {
        return idBooking;
    }


    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }


    public int getNrOfBaggages() {
        return nrOfBaggages;
    }

    public void setNrOfBaggages(int nrOfBaggages) {
        this.nrOfBaggages = nrOfBaggages;
    }

    public abstract double price();

    @Override
    public String toString() {
        return  idBooking + "\n"+
                " \t " + flight + "\n"+
                " \t Client Details: " + client +
                ", seat=" + seat +
                ", row=" + row +
                ", nrOfBaggages=" + nrOfBaggages ;
    }
}
