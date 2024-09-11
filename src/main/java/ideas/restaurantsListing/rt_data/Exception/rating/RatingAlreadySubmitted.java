package ideas.restaurantsListing.rt_data.Exception.rating;

public class RatingAlreadySubmitted extends RuntimeException {
    public RatingAlreadySubmitted(String message) {
        super(message);
    }
}
