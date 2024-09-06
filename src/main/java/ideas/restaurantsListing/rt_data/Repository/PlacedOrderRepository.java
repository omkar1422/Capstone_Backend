package ideas.restaurantsListing.rt_data.Repository;

import ideas.restaurantsListing.rt_data.Entity.Customer;
import ideas.restaurantsListing.rt_data.Entity.PlacedOrder;
import ideas.restaurantsListing.rt_data.Entity.Restaurant;
import ideas.restaurantsListing.rt_data.dto.placedOrders.PlacedOrdersByACustomer;
import ideas.restaurantsListing.rt_data.dto.placedOrders.PlacedOrdersByRestaurant;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlacedOrderRepository extends JpaRepository<PlacedOrder,Integer> {
    public List<PlacedOrdersByACustomer> findByCustomer(Customer customer);

    public List<PlacedOrdersByRestaurant> findByRestaurant(Restaurant restaurant);

    @Transactional
    public boolean deleteByPlacedOrderId(int placedOrderId);
}
