package ideas.restaurantsListing.rt_data.Controller;

import ideas.restaurantsListing.rt_data.Entity.Restaurant;
import ideas.restaurantsListing.rt_data.Exception.restaurant.RestaurantNotFoundException;
import ideas.restaurantsListing.rt_data.Service.RestaurantService;
import ideas.restaurantsListing.rt_data.dto.restaurant.RestaurantById;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<Restaurant>> saveListOfRestaurants(@RequestBody List<Restaurant> restaurants) {
        try {
            List<Restaurant> savedRestaurants = restaurantService.saveListOfRestaurants(restaurants);
            return ResponseEntity.ok(savedRestaurants);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<?> saveRestaurant(@RequestBody Restaurant restaurant) {
        try {
            Restaurant savedRestaurant = restaurantService.saveRestaurant(restaurant);
            return ResponseEntity.ok(savedRestaurant);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to save restaurant");
        }
    }

    @GetMapping("/getAllRestaurants")
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        try {
            List<Restaurant> restaurants = restaurantService.getAllRestaurants();
            return ResponseEntity.ok(restaurants);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @GetMapping("/getAllRestaurants/{pageNo}/{pageSize}")
    public ResponseEntity<List<Restaurant>> getRestaurantsByPaging(@PathVariable("pageNo") int pageNo,
                                                                   @PathVariable("pageSize") int pageSize) {
        try {
            List<Restaurant> restaurants = restaurantService.getAllRestaurantsPaging(pageNo, pageSize);
            return ResponseEntity.ok(restaurants);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<?> getRestaurantById(@PathVariable("id") int id) {
        try {
            Optional<RestaurantById> restaurant = restaurantService.getRestaurantById(id);
            if (!restaurant.isPresent()) {
                throw new RestaurantNotFoundException("Restaurant with id " + id + " not found");
            }
            return ResponseEntity.ok(restaurant);
        } catch (RestaurantNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to retrieve restaurant");
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<?> deleteRestaurantById(@PathVariable("id") int id) {
        try {
            restaurantService.deleteRestaurantById(id);
            return ResponseEntity.ok("Restaurant deleted successfully");
        } catch (RestaurantNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete restaurant");
        }
    }
}
