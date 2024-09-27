package ideas.restaurantsListing.rt_data.Service;

import ideas.restaurantsListing.rt_data.Entity.Restaurant;
import ideas.restaurantsListing.rt_data.Exception.restaurant.RestaurantNotFoundException;
import ideas.restaurantsListing.rt_data.Repository.RestaurantRepository;
import ideas.restaurantsListing.rt_data.dto.restaurant.RestaurantById;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService{

    @Autowired
    RestaurantRepository restaurantRepository;

    public List<Restaurant> saveListOfRestaurants(List<Restaurant> restaurants) {
        return restaurantRepository.saveAll(restaurants);
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public Restaurant saveRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public List<Restaurant> getAllRestaurantsPaging(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Restaurant> pageResult = restaurantRepository.findAll(pageable);
        return pageResult.getContent();
    }

    public Optional<RestaurantById> getRestaurantById(int id) {
        Optional<RestaurantById> restaurant = restaurantRepository.findById(id);
        if(restaurant == null)
            throw new RestaurantNotFoundException("Restaurant with id " + id + "doesn't exist");
        return restaurant;
    }

    public void deleteRestaurantById(int id) {
        restaurantRepository.deleteById(id);
    }
}
