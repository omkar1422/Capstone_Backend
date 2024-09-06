package ideas.restaurantsListing.rt_data.Repository;

import ideas.restaurantsListing.rt_data.Entity.Cart;
import ideas.restaurantsListing.rt_data.Entity.Customer;
import ideas.restaurantsListing.rt_data.dto.cart.CartItemsByCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart,Integer> {

    public List<CartItemsByCustomer> findByCustomer(Customer customer);

    @Query(value = "SELECT SUM(c.qty * m.menu_price) AS total_price " +
            "FROM Cart c " +
            "JOIN Menu m ON c.menu_id = m.menu_id " +
            "WHERE c.customer_id = :customerId", nativeQuery = true)
    public Double findTotalPriceByCustomerId(@Param("customerId") int customerId);
}
