package ideas.restaurantsListing.rt_data.Controller;

import ideas.restaurantsListing.rt_data.Entity.Cart;
import ideas.restaurantsListing.rt_data.Service.CartService;
import ideas.restaurantsListing.rt_data.dto.cart.CartItemsByCustomer;
import ideas.restaurantsListing.rt_data.Exception.cart.CartNotFoundException;
import ideas.restaurantsListing.rt_data.Exception.cart.CartSaveException;
import ideas.restaurantsListing.rt_data.Exception.customer.CustomerNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CartControllerTest {

    @InjectMocks
    private CartController cartController;

    @Mock
    private CartService cartService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void saveCart_ShouldReturnCreatedCart_WhenSuccessful() {
        Cart cart = new Cart(); // Initialize your Cart object accordingly
        when(cartService.saveCart(cart)).thenReturn(cart);

        ResponseEntity<Cart> response = cartController.saveCart(cart);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(cart, response.getBody());
    }

    @Test
    public void saveCart_ShouldReturnInternalServerError_WhenSaveFails() {
        Cart cart = new Cart(); // Initialize your Cart object accordingly
        when(cartService.saveCart(cart)).thenThrow(new CartSaveException("Failed to save cart.", new RuntimeException()));

        ResponseEntity<Cart> response = cartController.saveCart(cart);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }


    @Test
    public void getCartByCustomer_ShouldReturnCartItems_WhenSuccessful() {
        int customerId = 1;

        // Create a mock instance of CartItemsByCustomer
        CartItemsByCustomer mockCartItem = mock(CartItemsByCustomer.class);

        // Set up the mock behavior for CartItemsByCustomer
        when(mockCartItem.getCartId()).thenReturn(1);
        when(mockCartItem.getQty()).thenReturn(2);

        // Create a list of mock items to return
        List<CartItemsByCustomer> cartItems = Arrays.asList(mockCartItem);

        // Set up the mock behavior for the service
        when(cartService.getCartByCustomer(customerId)).thenReturn(cartItems);

        // Call the controller method
        ResponseEntity<List<CartItemsByCustomer>> response = cartController.getCartByCustomer(customerId);

        // Assert the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cartItems, response.getBody());
    }

    @Test
    public void getCartByCustomer_ShouldReturnNotFound_WhenNoCartItemsFound() {
        int customerId = 1;
        when(cartService.getCartByCustomer(customerId)).thenThrow(new CustomerNotFoundException("No cart items found."));

        ResponseEntity<List<CartItemsByCustomer>> response = cartController.getCartByCustomer(customerId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void getTotalPriceByCustomerId_ShouldReturnTotalPrice_WhenSuccessful() {
        int customerId = 1;
        double totalPrice = 100.0;
        when(cartService.getTotalPriceByCustomerId(customerId)).thenReturn(totalPrice);

        ResponseEntity<Double> response = cartController.getTotalPriceByCustomerId(customerId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(totalPrice, response.getBody());
    }

    @Test
    public void getTotalPriceByCustomerId_ShouldReturnNotFound_WhenNoPriceFound() {
        int customerId = 1;
        when(cartService.getTotalPriceByCustomerId(customerId)).thenThrow(new CustomerNotFoundException("No total price found."));

        ResponseEntity<Double> response = cartController.getTotalPriceByCustomerId(customerId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void deleteCartItemByCartId_ShouldReturnNoContent_WhenSuccessful() {
        int cartId = 1;
        doNothing().when(cartService).deleteCartItemByCartId(cartId);

        ResponseEntity<Void> response = cartController.deleteCartItemByCartId(cartId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void deleteCartItemByCartId_ShouldReturnNotFound_WhenCartNotFound() {
        int cartId = 1;
        doThrow(new CartNotFoundException("Cart not found.")).when(cartService).deleteCartItemByCartId(cartId);

        ResponseEntity<Void> response = cartController.deleteCartItemByCartId(cartId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void deleteCartByCustomerId_ShouldReturnNoContent_WhenSuccessful() {
        int customerId = 1;
        doNothing().when(cartService).deleteByCustomerId(customerId);

        ResponseEntity<Void> response = cartController.deleteCartByCustomerId(customerId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void deleteCartByCustomerId_ShouldReturnNotFound_WhenCustomerNotFound() {
        int customerId = 1;
        doThrow(new CustomerNotFoundException("Customer not found.")).when(cartService).deleteByCustomerId(customerId);

        ResponseEntity<Void> response = cartController.deleteCartByCustomerId(customerId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void updateCartQty_ShouldReturnUpdatedCart_WhenSuccessful() {
        Cart cart = new Cart(); // Initialize your Cart object with test data
        when(cartService.updateCartQty(cart.getCartId(), cart.getQty())).thenReturn(cart);

        ResponseEntity<Cart> response = cartController.updateCartQty(cart);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cart, response.getBody());
    }

    @Test
    public void updateCartQty_ShouldReturnNotFound_WhenCartNotFound() {
        Cart cart = new Cart(); // Initialize your Cart object with test data
        when(cartService.updateCartQty(cart.getCartId(), cart.getQty())).thenThrow(new CartNotFoundException("Cart not found."));

        ResponseEntity<Cart> response = cartController.updateCartQty(cart);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }
}
