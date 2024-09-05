package ideas.restaurantsListing.rt_data.Repository;

import ideas.restaurantsListing.rt_data.Entity.Restaurant;
import org.springframework.data.repository.CrudRepository;

public interface RestaurantRepository extends CrudRepository<Restaurant, Integer> {
}
