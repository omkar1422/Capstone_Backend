package ideas.restaurantsListing.rt_data.Exception.jwt;

public class TokenExpiredException extends RuntimeException {
    public TokenExpiredException(String message) {
        super(message);
    }
}
