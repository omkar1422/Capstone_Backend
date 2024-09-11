package ideas.restaurantsListing.rt_data.Controller;

import ideas.restaurantsListing.rt_data.Entity.Customer;
import ideas.restaurantsListing.rt_data.Entity.Rating;
import ideas.restaurantsListing.rt_data.Entity.Restaurant;
import ideas.restaurantsListing.rt_data.Exception.rating.RatingAlreadySubmitted;
import ideas.restaurantsListing.rt_data.Service.RatingService;
import ideas.restaurantsListing.rt_data.dto.rating.RatingOfRestaurantByCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurantListings/api/rating")
public class RatingController {

    @Autowired
    public RatingService ratingService;

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
        return ratingService.getRatingByRestaurantAndCustomer(
                restaurantId, customerId
        );
    }

    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_ADMIN')")
    @GetMapping("/averageRating/{restaurantId}")
    public Double getAverageRatingByRestaurant(@PathVariable("restaurantId") int restaurantId) {
        return ratingService.getAverageRatingOfARestaurant(restaurantId);
    }
}
