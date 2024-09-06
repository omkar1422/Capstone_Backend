package ideas.restaurantsListing.rt_data.dto.rating;

public interface RatingOfRestaurantByCustomer {

    public int getRatingId();
    public Customer getCustomer();
    public Restaurant getRestaurant();
    public float getRating();
    public String getReviewText();

    public interface Customer {
        public int getCustomerId();
        public String getCustomerName();
        public String getCustomerEmail();
    }

    public interface Restaurant {
        public int getRestaurantId();
        public String getRestaurantName();
        public String getRestaurantAddress();
    }
}
