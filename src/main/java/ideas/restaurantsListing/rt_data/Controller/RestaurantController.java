package ideas.restaurantsListing.rt_data.Controller;

import ideas.restaurantsListing.rt_data.Entity.Restaurant;
import ideas.restaurantsListing.rt_data.Service.RestaurantService;
import ideas.restaurantsListing.rt_data.dto.restaurant.RestaurantById;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/restaurantListings/api/restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping("/saveRestaurantsList")
    public List<Restaurant> saveListOfRestaurants(@RequestBody List<Restaurant> restaurants) {
        return restaurantService.saveListOfRestaurants(restaurants);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Restaurant> saveRestaurant(@RequestBody Restaurant restaurant) {
        Restaurant savedRestaurant = restaurantService.saveRestaurant(restaurant);
        return ResponseEntity.ok(savedRestaurant);
    }

//    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_ADMIN')")
    @GetMapping("/getAllRestaurants")
    public List<Restaurant> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }

//    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_ADMIN')")
    @GetMapping("/getAllRestaurants/{pageNo}/{pageSize}")
    public ResponseEntity<List<Restaurant>> getRestaurantsByPaging(@PathVariable("pageNo") int pageNo,
                                                                       @PathVariable("pageSize") int pageSize) {
        return ResponseEntity.ok(restaurantService.getAllRestaurantsPaging(pageNo, pageSize));
    }

//    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_ADMIN')")
    @GetMapping("/getById/{id}")
    public ResponseEntity<Optional<RestaurantById>> getRestaurantById(@PathVariable("id") int id) {
        return ResponseEntity.ok(restaurantService.getRestaurantById(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/deleteById/{id}")
    public void deleteRestaurantById(@PathVariable("id") int id) {
        restaurantService.deleteRestaurantById(id);
    }
}
