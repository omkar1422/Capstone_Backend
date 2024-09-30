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
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    public List<Restaurant> saveListOfRestaurants(List<Restaurant> restaurants) {
        try {
            return restaurantRepository.saveAll(restaurants);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save the list of restaurants", e);
        }
    }

    public List<Restaurant> getAllRestaurants() {
        try {
            return restaurantRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve the list of restaurants", e);
        }
    }

    public Restaurant saveRestaurant(Restaurant restaurant) {
        try {
            return restaurantRepository.save(restaurant);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save restaurant", e);
        }
    }

    public List<Restaurant> getAllRestaurantsPaging(int pageNo, int pageSize) {
        try {
            Pageable pageable = PageRequest.of(pageNo, pageSize);
            Page<Restaurant> pageResult = restaurantRepository.findAll(pageable);
            return pageResult.getContent();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve paginated list of restaurants", e);
        }
    }

    public Optional<RestaurantById> getRestaurantById(int id) {
        try {
            Optional<RestaurantById> restaurant = restaurantRepository.findById(id);
            if (!restaurant.isPresent()) {
                throw new RestaurantNotFoundException("Restaurant with id " + id + " doesn't exist");
            }
            return restaurant;
        } catch (RestaurantNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve restaurant by ID", e);
        }
    }

    public void deleteRestaurantById(int id) {
        try {
            if (!restaurantRepository.existsById(id)) {
                throw new RestaurantNotFoundException("Restaurant with id " + id + " doesn't exist");
            }
            restaurantRepository.deleteById(id);
        } catch (RestaurantNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete restaurant by ID", e);
        }
    }
}
