package ideas.restaurantsListing.rt_data.Service;

import ideas.restaurantsListing.rt_data.Entity.Cart;
import ideas.restaurantsListing.rt_data.Entity.Customer;
import ideas.restaurantsListing.rt_data.Exception.cart.CartNotFoundException;
import ideas.restaurantsListing.rt_data.Exception.cart.CartSaveException;
import ideas.restaurantsListing.rt_data.Exception.customer.CustomerNotFoundException;
import ideas.restaurantsListing.rt_data.Repository.CartRepository;
import ideas.restaurantsListing.rt_data.dto.cart.CartItemsByCustomer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartService cartService;

    @Mock
    private CartItemsByCustomer cartItemsByCustomer;

    private Cart cart;
    private final int cartId = 1;
    private final int customerId = 1;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        cart = new Cart();
        cart.setCartId(cartId);
    }

    // Test for saveCart
    @Test
    public void shouldSaveCart() {
        Cart cart = new Cart(1, new Customer(), null, 2);
        when(cartRepository.save(cart)).thenReturn(cart);

        Cart savedCart = cartService.saveCart(cart);
        assertNotNull(savedCart);
        assertEquals(1, savedCart.getCartId());
        verify(cartRepository, times(1)).save(cart);
    }

    // Test for getByCartId
    @Test
    public void shouldGetByCartId() {
        Cart cart = new Cart(1, new Customer(), null, 2);
        when(cartRepository.findByCartId(1)).thenReturn(cart);

        Cart foundCart = cartService.getByCartId(1);
        assertNotNull(foundCart);
        assertEquals(1, foundCart.getCartId());
        verify(cartRepository, times(1)).findByCartId(1);
    }

    @Test
    void shouldReturnCartItemsByCustomer_WhenCustomerExists() {
        int customerId = 1;
        // Arrange
        List<CartItemsByCustomer> expectedCartItems = new ArrayList<>();

        // Mocking the behavior of CartItemsByCustomer DTO
        CartItemsByCustomer item1 = mock(CartItemsByCustomer.class);
        when(item1.getCartId()).thenReturn(1);
        when(item1.getQty()).thenReturn(2);

        // Create a mocked Menu
        CartItemsByCustomer.Menu menuMock = mock(CartItemsByCustomer.Menu.class);
        when(menuMock.getMenuId()).thenReturn(1);
        when(menuMock.getMenuName()).thenReturn("Pizza");
        when(menuMock.getMenuPrice()).thenReturn(12.99f);

        // Create a mocked Restaurant
        CartItemsByCustomer.Menu.Restaurant restaurantMock = mock(CartItemsByCustomer.Menu.Restaurant.class);
        when(restaurantMock.getRestaurantId()).thenReturn(101);
        when(restaurantMock.getRestaurantName()).thenReturn("Italian Bistro");

        // Link the menu to the restaurant
        when(menuMock.getRestaurant()).thenReturn(restaurantMock);

        when(item1.getMenu()).thenReturn(menuMock);
        expectedCartItems.add(item1);

        // Ensure the customer ID matches exactly
        Customer customer = new Customer(customerId, null, null, null, null, null, null);
        when(cartRepository.findByCustomer(any(Customer.class))).thenReturn(expectedCartItems);

        // Act
        List<CartItemsByCustomer> actualCartItems = cartService.getCartByCustomer(customerId);

        // Assert
        assertEquals(expectedCartItems.size(), actualCartItems.size());
        assertEquals(expectedCartItems.get(0).getCartId(), actualCartItems.get(0).getCartId());
        assertEquals(expectedCartItems.get(0).getQty(), actualCartItems.get(0).getQty());
        assertEquals(expectedCartItems.get(0).getMenu().getMenuName(), actualCartItems.get(0).getMenu().getMenuName());
        assertEquals(expectedCartItems.get(0).getMenu().getRestaurant().getRestaurantName(), actualCartItems.get(0).getMenu().getRestaurant().getRestaurantName());
    }


    // Test for getTotalPriceByCustomerId
    @Test
    public void shouldGetTotalPriceByCustomerId() {
        when(cartRepository.findTotalPriceByCustomerId(1)).thenReturn(300.0);

        Double totalPrice = cartService.getTotalPriceByCustomerId(1);
        assertNotNull(totalPrice);
        assertEquals(300.0, totalPrice);
        verify(cartRepository, times(1)).findTotalPriceByCustomerId(1);
    }

    @Test
    void deleteCartItemByCartId_ShouldThrowCartNotFoundException_WhenCartDoesNotExist() {
        // Arrange
        when(cartRepository.findById(cartId)).thenReturn(Optional.empty());

        // Act & Assert
        CartNotFoundException exception = assertThrows(CartNotFoundException.class, () ->
                cartService.deleteCartItemByCartId(cartId));
        assertEquals("Cart with ID " + cartId + " not found.", exception.getMessage());
    }

    @Test
    void deleteByCustomerId_ShouldThrowCustomerNotFoundException_WhenNoCartsFound() {
        // Arrange
        when(cartRepository.findByCustomer(any())).thenReturn(List.of());

        // Act & Assert
        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class, () ->
                cartService.deleteByCustomerId(customerId));
        assertEquals("No cart items found for customer with ID " + customerId, exception.getMessage());
    }

    // Test for updateCartQty
    @Test
    public void shouldUpdateCartQty_CartExists() {
        Cart cart = new Cart(1, new Customer(), null, 2);
        when(cartRepository.findById(1)).thenReturn(Optional.of(cart));
        when(cartRepository.save(cart)).thenReturn(cart);

        Cart updatedCart = cartService.updateCartQty(1, 5);
        assertNotNull(updatedCart);
        assertEquals(5, updatedCart.getQty());
        verify(cartRepository, times(1)).findById(1);
        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    public void shouldUpdateCartQty_CartNotFound() {
        when(cartRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> cartService.updateCartQty(1, 5));
        assertEquals("Cart with ID 1 not found.", exception.getMessage());
        verify(cartRepository, times(1)).findById(1);
        verify(cartRepository, never()).save(any(Cart.class));
    }

    @Test
    void saveCart_ShouldThrowCartSaveException_WhenSaveFails() {
        // Arrange
        when(cartRepository.save(cart)).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        CartSaveException exception = assertThrows(CartSaveException.class, () ->
                cartService.saveCart(cart));
        assertEquals("Failed to save cart.", exception.getMessage());
    }

    @Test
    void getByCartId_ShouldThrowRuntimeException_WhenRuntimeErrorOccurs() {
        // Arrange
        when(cartRepository.findByCartId(cartId)).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                cartService.getByCartId(cartId));
        assertEquals("Database error", exception.getMessage());
    }

    @Test
    void getCartByCustomer_ShouldThrowCustomerNotFoundException_WhenNoCartItemsFound() {
        // Arrange
        when(cartRepository.findByCustomer(any())).thenReturn(List.of()); // Return empty list

        // Act & Assert
        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class, () ->
                cartService.getCartByCustomer(customerId));
        assertEquals("No cart items found for customer with ID " + customerId, exception.getMessage());
    }

    @Test
    void getTotalPriceByCustomerId_ShouldThrowCustomerNotFoundException_WhenTotalPriceIsNull() {
        // Arrange
        when(cartRepository.findTotalPriceByCustomerId(customerId)).thenReturn(null);

        // Act & Assert
        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class, () ->
                cartService.getTotalPriceByCustomerId(customerId));
        assertEquals("No total price found for customer with ID " + customerId, exception.getMessage());
    }

    @Test
    void deleteByCustomerId_ShouldDeleteCarts_WhenCartsExist() {
        // Arrange
        CartItemsByCustomer cartItemMock = mock(CartItemsByCustomer.class);
        when(cartItemMock.getCartId()).thenReturn(1);
        when(cartRepository.findByCustomer(any())).thenReturn(List.of(cartItemMock));

        // Act
        cartService.deleteByCustomerId(customerId);

        // Assert
        verify(cartRepository, times(1)).deleteByCustomer(any());
    }

    @Test
    void deleteCartItemByCartId_ShouldDeleteCart_WhenCartExists() {
        // Arrange
        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));

        // Act
        cartService.deleteCartItemByCartId(cartId);

        // Assert
        verify(cartRepository, times(1)).deleteByCartId(cartId);
    }
}