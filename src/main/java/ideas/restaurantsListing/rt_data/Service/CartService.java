package ideas.restaurantsListing.rt_data.Service;

import ideas.restaurantsListing.rt_data.Entity.Cart;
import ideas.restaurantsListing.rt_data.Entity.Customer;
import ideas.restaurantsListing.rt_data.Repository.CartRepository;
import ideas.restaurantsListing.rt_data.dto.cart.CartItemsByCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public List<CartItemsByCustomer> getCartByCustomer(int customerId) {
        return cartRepository.findByCustomer(
                new Customer(customerId,null,null,null,null,null)
        );
    }

    public Double getTotalPriceByCustomerId(int customerId) {
        return cartRepository.findTotalPriceByCustomerId(customerId);
    }
}
