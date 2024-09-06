package ideas.restaurantsListing.rt_data.Controller;

import ideas.restaurantsListing.rt_data.Entity.PlacedOrder;
import ideas.restaurantsListing.rt_data.Service.PlacedOrderService;
import ideas.restaurantsListing.rt_data.dto.placedOrders.PlacedOrdersByACustomer;
import ideas.restaurantsListing.rt_data.dto.placedOrders.PlacedOrdersByRestaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/customer/{id}")
    public List<PlacedOrdersByACustomer> getPlacedOrdersByCustomer(@PathVariable("id") int id) {
        return placedOrderService.placedOrdersByACustomer(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    @GetMapping("/restaurant/{id}")
    public List<PlacedOrdersByRestaurant> getPlacedOrdersByRestaurant(@PathVariable("id") int id) {
        return placedOrderService.placedOrdersByRestaurant(id);
    }
}
