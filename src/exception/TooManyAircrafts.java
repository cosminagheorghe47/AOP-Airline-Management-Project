package exception;

public class TooManyAircrafts extends RuntimeException{
    public TooManyAircrafts() {
        super();
    }


    public TooManyAircrafts(String message) {
        super(message);
    }
}
