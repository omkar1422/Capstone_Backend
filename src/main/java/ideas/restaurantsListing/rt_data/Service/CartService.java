package ideas.restaurantsListing.rt_data.Service;

import ideas.restaurantsListing.rt_data.Entity.Cart;
import ideas.restaurantsListing.rt_data.Entity.Customer;
import ideas.restaurantsListing.rt_data.Repository.CartRepository;
import ideas.restaurantsListing.rt_data.dto.cart.CartItemsByCustomer;
import jakarta.transaction.Transactional;
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

    public Cart getByCartId(int cartId) {
        return cartRepository.findByCartId(cartId);
    }

    public List<CartItemsByCustomer> getCartByCustomer(int customerId) {
        return cartRepository.findByCustomer(
                new Customer(customerId,null,null,null,null,null,null)
        );
    }

    public Double getTotalPriceByCustomerId(int customerId) {
        return cartRepository.findTotalPriceByCustomerId(customerId);
    }

    @Transactional
    public void deleteCartItemByCartId(int cartId) {
        cartRepository.deleteByCartId(cartId);
    }

    @Transactional
    public void deleteByCustomerId(int customerId) {
        cartRepository.deleteByCustomer(
                new Customer(customerId,null,null,null,null,null,null)
        );
    }
}
