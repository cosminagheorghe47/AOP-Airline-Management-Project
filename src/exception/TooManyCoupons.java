package exception;

public class TooManyCoupons extends RuntimeException{
    public TooManyCoupons() {
        super();
    }


    public TooManyCoupons(String message) {
        super(message);
    }
}
