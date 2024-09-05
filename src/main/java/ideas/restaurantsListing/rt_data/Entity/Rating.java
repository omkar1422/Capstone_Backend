package ideas.restaurantsListing.rt_data.Entity;

import jakarta.persistence.*;

@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ratingId;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    private float rating;

    private String reviewText;

    public Rating() {

    }

    public Rating(int ratingId, Customer customer, Restaurant restaurant, float rating, String reviewText) {
        this.ratingId = ratingId;
        this.customer = customer;
        this.restaurant = restaurant;
        this.rating = rating;
        this.reviewText = reviewText;
    }

    public int getRatingId() {
        return ratingId;
    }

    public void setRatingId(int ratingId) {
        this.ratingId = ratingId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "ratingId=" + ratingId +
                ", customer=" + customer +
                ", restaurant=" + restaurant +
                ", rating=" + rating +
                ", reviewText=" + reviewText +
                '}';
    }
}
