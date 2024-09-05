package ideas.restaurantsListing.rt_data.Repository;

import ideas.restaurantsListing.rt_data.Entity.Menu;
import ideas.restaurantsListing.rt_data.Entity.Restaurant;
import ideas.restaurantsListing.rt_data.dto.MenusByRestaurant;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MenuRepository extends CrudRepository<Menu, Integer> {
    public List<MenusByRestaurant> findByRestaurant(Restaurant restaurant);
}
