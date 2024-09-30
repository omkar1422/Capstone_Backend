package ideas.restaurantsListing.rt_data.Controller;

import ideas.restaurantsListing.rt_data.Entity.PlacedOrder;
import ideas.restaurantsListing.rt_data.Service.PlacedOrderService;
import ideas.restaurantsListing.rt_data.dto.placedOrders.PlacedOrdersByACustomer;
import ideas.restaurantsListing.rt_data.dto.placedOrders.PlacedOrdersByRestaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/restaurantListings/api/placedOrder")
public class PlacedOrderController {

    @Autowired
    private PlacedOrderService placedOrderService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    @PostMapping
    public PlacedOrder savePlacedOrder(@RequestBody PlacedOrder placedOrder) {
        try {
            return placedOrderService.savePlacedOrder(placedOrder);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    @GetMapping("/customer/{customerId}")
    public List<PlacedOrdersByACustomer> getPlacedOrdersByCustomer(@PathVariable("customerId") int customerId) {
        try {
            return placedOrderService.placedOrdersByACustomer(customerId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    @GetMapping("/getByRestaurant/{id}")
    public List<PlacedOrdersByRestaurant> getPlacedOrdersByRestaurant(@PathVariable("id") int id) {
        try {
            return placedOrderService.placedOrdersByRestaurant(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @DeleteMapping("/deleteById/{id}")
    public Integer deleteRestaurantById(@PathVariable("id") int id) {
        try {
            return placedOrderService.deleteByPlacedOrderId(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @GetMapping("/placeOrderFromCart/{customerId}")
    public void placeOrderFromCart(@PathVariable("customerId") int customerId) {
        try {
            placedOrderService.placeOrderAndClearCart(customerId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
