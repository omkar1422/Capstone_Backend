package ideas.restaurantsListing.rt_data.Exception.cart;

public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException(String message) {
        super(message);
    }
}
