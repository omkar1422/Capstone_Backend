package ideas.restaurantsListing.rt_data.Service;

import ideas.restaurantsListing.rt_data.Entity.Customer;
import ideas.restaurantsListing.rt_data.Entity.Rating;
import ideas.restaurantsListing.rt_data.Entity.Restaurant;
import ideas.restaurantsListing.rt_data.Repository.RatingRepository;
import ideas.restaurantsListing.rt_data.dto.rating.RatingByRestaurat;
import ideas.restaurantsListing.rt_data.dto.rating.RatingOfRestaurantByCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    public List<RatingByRestaurat> getRatingsByRestaurant(int restaurantId) {
        try {
            return ratingRepository.findByRestaurant(
                    new Restaurant(restaurantId,null,null,null,null,null,null,null)
            );
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Rating> saveListOfRatings(List<Rating> ratings) {
        return ratingRepository.saveAll(ratings);
    }

    public Rating saveRating(Rating rating) {
        try {
            return ratingRepository.save(rating);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public RatingOfRestaurantByCustomer getRatingByRestaurantAndCustomer(int restaurantId, int customerId) {
        try {
            return ratingRepository.findByRestaurantAndCustomer(
                    new Restaurant(restaurantId,null,null,null,null,null,null,null),
                    new Customer(customerId,null,null,null,null,null,null)
            );
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Double getAverageRatingOfARestaurant(int restaurantId) {
        try {
            return ratingRepository.findAverageRatingByRestaurantId(restaurantId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
