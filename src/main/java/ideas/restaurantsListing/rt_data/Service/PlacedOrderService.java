package ideas.restaurantsListing.rt_data.Service;

import ideas.restaurantsListing.rt_data.Entity.*;
import ideas.restaurantsListing.rt_data.Exception.placedOrder.PlacedOrderNotFound;
import ideas.restaurantsListing.rt_data.Repository.CartRepository;
import ideas.restaurantsListing.rt_data.Repository.PlacedOrderRepository;
import ideas.restaurantsListing.rt_data.dto.cart.CartItemsByCustomer;
import ideas.restaurantsListing.rt_data.dto.placedOrders.PlacedOrdersByACustomer;
import ideas.restaurantsListing.rt_data.dto.placedOrders.PlacedOrdersByRestaurant;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlacedOrderService {

    @Autowired
    private PlacedOrderRepository placedOrderRepository;

    @Autowired
    private CartRepository cartRepository;

    public PlacedOrder savePlacedOrder(PlacedOrder placedOrder) {
        try {
            return placedOrderRepository.save(placedOrder);
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<PlacedOrdersByACustomer> placedOrdersByACustomer(int id) {
        try {
            return placedOrderRepository.findByCustomer(
                    new Customer(id,null,null,null,null,null,null)
            );
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<PlacedOrdersByRestaurant> placedOrdersByRestaurant(int id) {
        try {
            return placedOrderRepository.findByRestaurant(
                    new Restaurant(id,null,null,null,null,null,null,null)
            );
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Integer deleteByPlacedOrderId(int id) {
        try{
            if( !placedOrderRepository.existsById(id))
                throw new PlacedOrderNotFound("Order with id " + id + "not found");

            return placedOrderRepository.deleteByPlacedOrderId(id);
        } catch (Exception e) {
            throw new PlacedOrderNotFound("Order with id " + id + "not found");
        }
    }

    @Transactional
    public void placeOrderAndClearCart(int customerId) {

        try {
            // Fetch all cart items for the customer
            List<CartItemsByCustomer> cartItems = cartRepository.findByCustomer(
                    new Customer(customerId, null, null, null, null, null, null)
            );

            // Insert each cart item into PlacedOrder and then delete it from Cart
            for (CartItemsByCustomer cartItem : cartItems) {
                PlacedOrder placedOrder = new PlacedOrder();

                Menu menu = new Menu();
                menu.setMenuId(cartItem.getMenu().getMenuId());
                menu.setMenuName(cartItem.getMenu().getMenuName());
                menu.setMenuPrice(cartItem.getMenu().getMenuPrice());
                menu.setMenuImage(cartItem.getMenu().getMenuImage());
                placedOrder.setMenu(menu);

                Customer customer = new Customer();
                customer.setCustomerId(cartItem.getCustomer().getCustomerId());
                placedOrder.setCustomer(customer);

                Restaurant restaurant = new Restaurant();
                restaurant.setRestaurantId(cartItem.getMenu().getRestaurant().getRestaurantId());
                restaurant.setRestaurantName(cartItem.getMenu().getRestaurant().getRestaurantName());
                restaurant.setRestaurantAddress(cartItem.getMenu().getRestaurant().getRestaurantAddress());
                restaurant.setRestaurantEmail(cartItem.getMenu().getRestaurant().getRestaurantEmail());
                placedOrder.setRestaurant(restaurant);// Assuming `Menu` has a `Restaurant`

                placedOrder.setPlacedOrderPrice(cartItem.getMenu().getMenuPrice());
                placedOrder.setPlacedOrderQty(cartItem.getQty());
                placedOrder.setDelivery("Pending");

                // Save the placed order
                placedOrderRepository.save(placedOrder);

                Cart cart = cartRepository.findByCartId(cartItem.getCartId());

                // Delete the cart item
                cartRepository.delete(cart);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
