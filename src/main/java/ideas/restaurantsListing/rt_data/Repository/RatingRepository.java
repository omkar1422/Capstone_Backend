package ideas.restaurantsListing.rt_data.Repository;

import ideas.restaurantsListing.rt_data.Entity.Customer;
import ideas.restaurantsListing.rt_data.Entity.Rating;
import ideas.restaurantsListing.rt_data.Entity.Restaurant;
import ideas.restaurantsListing.rt_data.dto.RatingOfRestaurantByCustomer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RatingRepository extends CrudRepository<Rating, Integer> {

    public RatingOfRestaurantByCustomer findByRestaurantAndCustomer(Restaurant restaurant, Customer customer);

    @Query(value = "SELECT AVG(r.rating) FROM Rating r WHERE r.restaurant_id = :restaurantId", nativeQuery = true)
    Double findAverageRatingByRestaurantId(@Param("restaurantId") int restaurantId);
}
