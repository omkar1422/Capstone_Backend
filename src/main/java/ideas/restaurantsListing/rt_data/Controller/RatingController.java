package ideas.restaurantsListing.rt_data.Controller;

import ideas.restaurantsListing.rt_data.Entity.Rating;
import ideas.restaurantsListing.rt_data.Exception.rating.RatingAlreadySubmitted;
import ideas.restaurantsListing.rt_data.Service.RatingService;
import ideas.restaurantsListing.rt_data.dto.rating.RatingByRestaurat;
import ideas.restaurantsListing.rt_data.dto.rating.RatingOfRestaurantByCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/restaurantListings/api/rating")
public class RatingController {

    @Autowired
    public RatingService ratingService;

    @PostMapping("/saveListOfRatings")
    public List<Rating> saveListOfRatings(@RequestBody List<Rating> ratings) {
        try {
            return ratingService.saveListOfRatings(ratings);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PostMapping
    public Rating saveRating(@RequestBody Rating rating) {

            if(ratingService.getRatingByRestaurantAndCustomer(rating.getRestaurant().getRestaurantId(), rating.getCustomer().getCustomerId()) != null) {
                throw new RatingAlreadySubmitted("rating for the restaurant with id " +
                        rating.getRestaurant().getRestaurantId() + "already exists by customer with id "  + rating.getCustomer().getCustomerId());
            }
            return ratingService.saveRating(rating);
    }

    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_ADMIN')")
    @GetMapping("/{restaurantId}/{customerId}")
    public RatingOfRestaurantByCustomer getRatingByRestaurantAndCustomer(@PathVariable("restaurantId") int restaurantId,
                                                                         @PathVariable("customerId")int customerId) {
        try {
            return ratingService.getRatingByRestaurantAndCustomer(
                    restaurantId, customerId
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/averageRating/{restaurantId}")
    public Double getAverageRatingByRestaurant(@PathVariable("restaurantId") int restaurantId) {
        try {
            return ratingService.getAverageRatingOfARestaurant(restaurantId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/allRatings/{restaurantId}")
    public List<RatingByRestaurat> getAllRatings(@PathVariable("restaurantId") int restaurantId) {
        try {
            return ratingService.getRatingsByRestaurant(restaurantId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
