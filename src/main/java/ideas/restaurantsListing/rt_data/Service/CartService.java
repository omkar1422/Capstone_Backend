package ideas.restaurantsListing.rt_data.Service;

import ideas.restaurantsListing.rt_data.Entity.Cart;
import ideas.restaurantsListing.rt_data.Entity.Customer;

import ideas.restaurantsListing.rt_data.Exception.cart.CartNotFoundException;
import ideas.restaurantsListing.rt_data.Exception.cart.CartSaveException;
import ideas.restaurantsListing.rt_data.Exception.customer.CustomerNotFoundException;
import ideas.restaurantsListing.rt_data.Repository.CartRepository;
import ideas.restaurantsListing.rt_data.dto.cart.CartItemsByCustomer;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public Cart saveCart(Cart cart) {
        try {
            return cartRepository.save(cart);
        } catch (Exception e) {
            throw new CartSaveException("Failed to save cart.", e);
        }
    }

    public Cart getByCartId(int cartId) {
        try {
            return cartRepository.findByCartId(cartId);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    public List<CartItemsByCustomer> getCartByCustomer(int customerId) {
        List<CartItemsByCustomer> cartItems = cartRepository.findByCustomer(
                new Customer(customerId, null, null, null, null, null, null)
        );
        if (cartItems.isEmpty()) {
            throw new CustomerNotFoundException("No cart items found for customer with ID " + customerId);
        }
        return cartItems;
    }

    public Double getTotalPriceByCustomerId(int customerId) {
        Double totalPrice = cartRepository.findTotalPriceByCustomerId(customerId);
        if (totalPrice == null) {
            throw new CustomerNotFoundException("No total price found for customer with ID " + customerId);
        }
        return totalPrice;
    }

    @Transactional
    public void deleteCartItemByCartId(int cartId) {
        if (cartRepository.findById(cartId).isEmpty()) {
            throw new CartNotFoundException("Cart with ID " + cartId + " not found.");
        }
        cartRepository.deleteByCartId(cartId);
    }

    @Transactional
    public void deleteByCustomerId(int customerId) {
        if (cartRepository.findByCustomer(new Customer(customerId, null, null, null, null, null, null)).isEmpty()) {
            throw new CustomerNotFoundException("No cart items found for customer with ID " + customerId);
        }
        cartRepository.deleteByCustomer(
                new Customer(customerId, null, null, null, null, null, null)
        );
    }

    public Cart updateCartQty(int cartId, int newQty) {
        Optional<Cart> cartOptional = cartRepository.findById(cartId);
        if (cartOptional.isPresent()) {
            Cart cart = cartOptional.get();
            cart.setQty(newQty);
            return cartRepository.save(cart);
        } else {
            throw new CartNotFoundException("Cart with ID " + cartId + " not found.");
        }
    }
}
