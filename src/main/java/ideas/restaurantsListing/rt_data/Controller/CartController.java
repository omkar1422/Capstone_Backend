package ideas.restaurantsListing.rt_data.Controller;

import ideas.restaurantsListing.rt_data.Entity.Cart;
import ideas.restaurantsListing.rt_data.Service.CartService;
import ideas.restaurantsListing.rt_data.dto.cart.CartItemsByCustomer;
import ideas.restaurantsListing.rt_data.Exception.cart.CartNotFoundException;
import ideas.restaurantsListing.rt_data.Exception.cart.CartSaveException;
import ideas.restaurantsListing.rt_data.Exception.customer.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Cart> saveCart(@RequestBody Cart cart) {
        try {
            Cart savedCart = cartService.saveCart(cart);
            return new ResponseEntity<>(savedCart, HttpStatus.CREATED);
        } catch (CartSaveException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_ADMIN')")
    @GetMapping("/cartItems/{customerId}")
    public ResponseEntity<List<CartItemsByCustomer>> getCartByCustomer(@PathVariable("customerId") int customerId) {
        try {
            List<CartItemsByCustomer> cartItems = cartService.getCartByCustomer(customerId);
            return new ResponseEntity<>(cartItems, HttpStatus.OK);
        } catch (CustomerNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_ADMIN')")
    @GetMapping("/totalPrice/{customerId}")
    public ResponseEntity<Double> getTotalPriceByCustomerId(@PathVariable("customerId") int customerId) {
        try {
            Double totalPrice = cartService.getTotalPriceByCustomerId(customerId);
            return new ResponseEntity<>(totalPrice, HttpStatus.OK);
        } catch (CustomerNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @DeleteMapping("/deleteCartItem/{cartId}")
    public ResponseEntity<Void> deleteCartItemByCartId(@PathVariable("cartId") int cartId) {
        try {
            cartService.deleteCartItemByCartId(cartId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (CartNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @DeleteMapping("/deleteCart/{customerId}")
    public ResponseEntity<Void> deleteCartByCustomerId(@PathVariable("customerId") int customerId) {
        try {
            cartService.deleteByCustomerId(customerId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (CustomerNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PutMapping("/updateQty")
    public ResponseEntity<Cart> updateCartQty(@RequestBody Cart cart) {
        try {
            Cart updatedCart = cartService.updateCartQty(cart.getCartId(), cart.getQty());
            return new ResponseEntity<>(updatedCart, HttpStatus.OK);
        } catch (CartNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
