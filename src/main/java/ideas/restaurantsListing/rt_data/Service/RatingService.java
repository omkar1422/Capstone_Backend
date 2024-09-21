package ideas.restaurantsListing.rt_data.Service;

import ideas.restaurantsListing.rt_data.Entity.Customer;
import ideas.restaurantsListing.rt_data.Entity.Rating;
import ideas.restaurantsListing.rt_data.Entity.Restaurant;
import ideas.restaurantsListing.rt_data.Repository.RatingRepository;
import ideas.restaurantsListing.rt_data.dto.rating.RatingOfRestaurantByCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    public Rating saveRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    public RatingOfRestaurantByCustomer getRatingByRestaurantAndCustomer(int restaurantId, int customerId) {
        return ratingRepository.findByRestaurantAndCustomer(
                new Restaurant(restaurantId,null,null,null,null,null,null),
                new Customer(customerId,null,null,null,null,null,null)
        );
    }

    public Double getAverageRatingOfARestaurant(int restaurantId) {
        return ratingRepository.findAverageRatingByRestaurantId(restaurantId);
    }
}
