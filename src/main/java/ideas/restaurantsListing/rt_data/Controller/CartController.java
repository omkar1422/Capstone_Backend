package ideas.restaurantsListing.rt_data.Controller;

import ideas.restaurantsListing.rt_data.Entity.Cart;
import ideas.restaurantsListing.rt_data.Service.CartService;
import ideas.restaurantsListing.rt_data.dto.cart.CartItemsByCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/restaurantListings/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PostMapping
    public Cart saveCart(@RequestBody Cart cart) {
        return cartService.saveCart(cart);
    }

    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_ADMIN')")
    @GetMapping("/cartItems/{customerId}")
    public List<CartItemsByCustomer> getCartByCustomer(@PathVariable("customerId") int customerId) {
        return cartService.getCartByCustomer(customerId);
    }

    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_ADMIN')")
    @GetMapping("/totalPrice/{customerId}")
    public Double getTotalPriceByCustomerId(@PathVariable("customerId") int customerId) {
        return cartService.getTotalPriceByCustomerId(customerId);
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @DeleteMapping("/deleteCartItem/{cartId}")
    public void deleteCartItemByCartId(@PathVariable("cartId") int cartId) {
        cartService.deleteCartItemByCartId(cartId);
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @DeleteMapping("/deleteCart/{customerId}")
    public void deleteCartByCustomerId(@PathVariable("customerId") int customerId) {
        cartService.deleteByCustomerId(customerId);
    }


}
