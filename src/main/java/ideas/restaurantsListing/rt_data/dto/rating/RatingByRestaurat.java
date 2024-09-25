package ideas.restaurantsListing.rt_data.dto.rating;

import ideas.restaurantsListing.rt_data.Entity.Customer;

public interface RatingByRestaurat {
    public int getRatingId();

    public Customer getCustomer();

    public float getRating();
    public String getReviewText();

    public interface Customer {
        public int getCustomerId();
        public String getCustomerName();
        public String getCustomerEmail();
    }
}
