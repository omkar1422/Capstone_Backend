package ideas.restaurantsListing.rt_data.Exception.customer;

public class CustomerNotFoundException extends RuntimeException{

    public CustomerNotFoundException(String message) {
        super(message);
    }
}
