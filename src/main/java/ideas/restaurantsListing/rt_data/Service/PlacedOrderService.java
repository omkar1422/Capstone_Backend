package ideas.restaurantsListing.rt_data.Service;

import ideas.restaurantsListing.rt_data.Entity.Customer;
import ideas.restaurantsListing.rt_data.Entity.Menu;
import ideas.restaurantsListing.rt_data.Entity.PlacedOrder;
import ideas.restaurantsListing.rt_data.Entity.Restaurant;
import ideas.restaurantsListing.rt_data.Exception.placedOrder.PlacedOrderNotFound;
import ideas.restaurantsListing.rt_data.Exception.restaurant.RestaurantNotFoundException;
import ideas.restaurantsListing.rt_data.Repository.PlacedOrderRepository;
import ideas.restaurantsListing.rt_data.dto.placedOrders.PlacedOrdersByACustomer;
import ideas.restaurantsListing.rt_data.dto.placedOrders.PlacedOrdersByRestaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlacedOrderService {

    @Autowired
    private PlacedOrderRepository placedOrderRepository;

    public PlacedOrder savePlacedOrder(PlacedOrder placedOrder) {
        return placedOrderRepository.save(placedOrder);
    }

    public List<PlacedOrdersByACustomer> placedOrdersByACustomer(int id) {
        return placedOrderRepository.findByCustomer(
                new Customer(id,null,null,null,null,null)
        );
    }

    public List<PlacedOrdersByRestaurant> placedOrdersByRestaurant(int id) {
        return placedOrderRepository.findByRestaurant(
                new Restaurant(id,null,null,null,null)
        );
    }

    public Integer deleteByPlacedOrderId(int id) {
        if( !placedOrderRepository.existsById(id))
            throw new PlacedOrderNotFound("Order with id " + id + "not found");

        return placedOrderRepository.deleteByPlacedOrderId(id);
    }
}
