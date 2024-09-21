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
        return placedOrderService.savePlacedOrder(placedOrder);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    @GetMapping("/customer/{customerId}")
    public List<PlacedOrdersByACustomer> getPlacedOrdersByCustomer(@PathVariable("customerId") int customerId) {
        return placedOrderService.placedOrdersByACustomer(customerId);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    @GetMapping("/getByRestaurant/{id}")
    public List<PlacedOrdersByRestaurant> getPlacedOrdersByRestaurant(@PathVariable("id") int id) {
        return placedOrderService.placedOrdersByRestaurant(id);
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @DeleteMapping("/deleteById/{id}")
    public Integer deleteRestaurantById(@PathVariable("id") int id) {
        return placedOrderService.deleteByPlacedOrderId(id);
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @GetMapping("/placeOrderFromCart/{customerId}")
    public void placeOrderFromCart(@PathVariable("customerId") int customerId) {
        placedOrderService.placeOrderAndClearCart(customerId);
    }
}
