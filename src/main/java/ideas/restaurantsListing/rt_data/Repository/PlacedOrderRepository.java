package ideas.restaurantsListing.rt_data.Repository;

import ideas.restaurantsListing.rt_data.Entity.Customer;
import ideas.restaurantsListing.rt_data.Entity.PlacedOrder;
import ideas.restaurantsListing.rt_data.Entity.Restaurant;
import ideas.restaurantsListing.rt_data.dto.PlacedOrdersByACustomer;
import ideas.restaurantsListing.rt_data.dto.PlacedOrdersByRestaurant;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlacedOrderRepository extends CrudRepository<PlacedOrder,Integer> {
    public List<PlacedOrdersByACustomer> findByCustomer(Customer customer);

    public List<PlacedOrdersByRestaurant> findByRestaurant(Restaurant restaurant);

    @Transactional
    public void deleteByPlacedOrderId(int placedOrderId);
}
