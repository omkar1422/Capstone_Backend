package ideas.restaurantsListing.rt_data.Exception.restaurant;

public class RestaurantNotFoundException extends RuntimeException{
    public RestaurantNotFoundException(String message) {
        super(message);
    }
}
