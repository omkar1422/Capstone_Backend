package ideas.restaurantsListing.rt_data.Repository;

import ideas.restaurantsListing.rt_data.Entity.Customer;
import ideas.restaurantsListing.rt_data.Entity.Rating;
import ideas.restaurantsListing.rt_data.Entity.Restaurant;
import ideas.restaurantsListing.rt_data.dto.rating.RatingOfRestaurantByCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RatingRepository extends JpaRepository<Rating, Integer> {

    public RatingOfRestaurantByCustomer findByRestaurantAndCustomer(Restaurant restaurant, Customer customer);

    @Query(value = "SELECT AVG(r.rating) FROM Rating r WHERE r.restaurant_id = :restaurantId", nativeQuery = true)
    Double findAverageRatingByRestaurantId(@Param("restaurantId") int restaurantId);
}
