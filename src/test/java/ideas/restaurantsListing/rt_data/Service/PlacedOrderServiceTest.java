package ideas.restaurantsListing.rt_data.Service;

import ideas.restaurantsListing.rt_data.Entity.Customer;
import ideas.restaurantsListing.rt_data.Entity.PlacedOrder;
import ideas.restaurantsListing.rt_data.Entity.Restaurant;
import ideas.restaurantsListing.rt_data.Exception.placedOrder.PlacedOrderNotFound;
import ideas.restaurantsListing.rt_data.Repository.PlacedOrderRepository;
import ideas.restaurantsListing.rt_data.dto.placedOrders.PlacedOrdersByACustomer;
import ideas.restaurantsListing.rt_data.dto.placedOrders.PlacedOrdersByRestaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PlacedOrderServiceTest {

    @Mock
    private PlacedOrderRepository placedOrderRepository;

    @InjectMocks
    private PlacedOrderService placedOrderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldSavePlacedOrder() {
        PlacedOrder placedOrder = new PlacedOrder(1,null,null,null,
                300.0f,null,2);
        when(placedOrderRepository.save(placedOrder)).thenReturn(placedOrder);
        assertEquals(placedOrder,placedOrderService.savePlacedOrder(placedOrder));
    }

    @Test
    public void shouldGetPlacedOrdersByCustomer() {
        int customerId = 1;

        PlacedOrdersByACustomer placedOrder = mock(PlacedOrdersByACustomer.class);
        PlacedOrdersByACustomer.Menu menu = mock(PlacedOrdersByACustomer.Menu.class);
        PlacedOrdersByACustomer.Menu.Restaurant restaurant = mock(PlacedOrdersByACustomer.Menu.Restaurant.class);

        when(placedOrder.getPlacedOrderId()).thenReturn(1);
        when(placedOrder.getMenu()).thenReturn(menu);
        when(placedOrder.getPlacedOrderPrice()).thenReturn(100.0f);
        when(placedOrder.getDelivery()).thenReturn("Home Delivery");
        when(placedOrder.getPlacedOrderQty()).thenReturn(2);

        when(menu.getMenuId()).thenReturn(1);
        when(menu.getRestaurant()).thenReturn(restaurant);
        when(menu.getMenuName()).thenReturn("Pizza");
        when(menu.getMenuPrice()).thenReturn(50.0f);
        when(menu.getMenuImage()).thenReturn("image_url");

        when(restaurant.getRestaurantId()).thenReturn(1);
        when(restaurant.getRestaurantName()).thenReturn("Test Restaurant");
        when(restaurant.getRestaurantEmail()).thenReturn("test@restaurant.com");
        when(restaurant.getRestaurantAddress()).thenReturn("123 Test St");
        when(restaurant.getRestaurantPhone()).thenReturn("1234567890");

        when(placedOrderRepository.findByCustomer(any(Customer.class))).thenReturn(Arrays.asList(placedOrder));

        List<PlacedOrdersByACustomer> result = placedOrderService.placedOrdersByACustomer(customerId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getPlacedOrderId());
        assertEquals(100.0f, result.get(0).getPlacedOrderPrice(), 0.0);
        assertEquals("Home Delivery", result.get(0).getDelivery());
        assertEquals(2, result.get(0).getPlacedOrderQty());

        PlacedOrdersByACustomer.Menu resultMenu = result.get(0).getMenu();
        assertNotNull(resultMenu);
        assertEquals(1, resultMenu.getMenuId());
        assertEquals("Pizza", resultMenu.getMenuName());
        assertEquals(50.0f, resultMenu.getMenuPrice(), 0.0);
        assertEquals("image_url", resultMenu.getMenuImage());

        PlacedOrdersByACustomer.Menu.Restaurant resultRestaurant = resultMenu.getRestaurant();
        assertNotNull(resultRestaurant);
        assertEquals(1, resultRestaurant.getRestaurantId());
        assertEquals("Test Restaurant", resultRestaurant.getRestaurantName());
        assertEquals("test@restaurant.com", resultRestaurant.getRestaurantEmail());
        assertEquals("123 Test St", resultRestaurant.getRestaurantAddress());
        assertEquals("1234567890", resultRestaurant.getRestaurantPhone());
    }

    @Test
    public void shouldPlacedOrdersByRestaurant() {
        int restaurantId = 1;

        PlacedOrdersByRestaurant placedOrder = mock(PlacedOrdersByRestaurant.class);
        PlacedOrdersByRestaurant.Menu menu = mock(PlacedOrdersByRestaurant.Menu.class);
        PlacedOrdersByRestaurant.Customer customer = mock(PlacedOrdersByRestaurant.Customer.class);

        when(placedOrder.getPlacedOrderId()).thenReturn(1);
        when(placedOrder.getMenu()).thenReturn(menu);
        when(placedOrder.getCustomer()).thenReturn(customer);
        when(placedOrder.getPlacedOrderPrice()).thenReturn(100.0f);
        when(placedOrder.getDelivery()).thenReturn("Home Delivery");
        when(placedOrder.getPlacedOrderQty()).thenReturn(2);

        when(menu.getMenuId()).thenReturn(1);
        when(menu.getMenuName()).thenReturn("Pizza");
        when(menu.getMenuPrice()).thenReturn(50.0f);
        when(menu.getMenuImage()).thenReturn("image_url");

        when(customer.getCustomerId()).thenReturn(1);
        when(customer.getCustomerName()).thenReturn("John Doe");
        when(customer.getCustomerEmail()).thenReturn("john.doe@example.com");
        when(customer.getCustomerPhone()).thenReturn("1234567890");

        when(placedOrderRepository.findByRestaurant(any(Restaurant.class))).thenReturn(Arrays.asList(placedOrder));

        List<PlacedOrdersByRestaurant> result = placedOrderService.placedOrdersByRestaurant(restaurantId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getPlacedOrderId());
        assertEquals(100.0f, result.get(0).getPlacedOrderPrice(), 0.0);
        assertEquals("Home Delivery", result.get(0).getDelivery());
        assertEquals(2, result.get(0).getPlacedOrderQty());

        PlacedOrdersByRestaurant.Menu resultMenu = result.get(0).getMenu();
        assertNotNull(resultMenu);
        assertEquals(1, resultMenu.getMenuId());
        assertEquals("Pizza", resultMenu.getMenuName());
        assertEquals(50.0f, resultMenu.getMenuPrice(), 0.0);
        assertEquals("image_url", resultMenu.getMenuImage());
        PlacedOrdersByRestaurant.Customer resultCustomer = result.get(0).getCustomer();
        assertNotNull(resultCustomer);
        assertEquals(1, resultCustomer.getCustomerId());
        assertEquals("John Doe", resultCustomer.getCustomerName());
        assertEquals("john.doe@example.com", resultCustomer.getCustomerEmail());
        assertEquals("1234567890", resultCustomer.getCustomerPhone());
    }

    @Test
    public void shouldDeleteByPlacedOrderId_Success() {
        int orderId = 1;

        when(placedOrderRepository.existsById(orderId)).thenReturn(true);
        when(placedOrderRepository.deleteByPlacedOrderId(orderId)).thenReturn(1);

        Integer result = placedOrderService.deleteByPlacedOrderId(orderId);

        assertNotNull(result);
        assertEquals(Integer.valueOf(1), result);

        verify(placedOrderRepository, times(1)).existsById(orderId);
        verify(placedOrderRepository, times(1)).deleteByPlacedOrderId(orderId);
    }

    @Test
    public void testDeleteByPlacedOrderId_NotFound() {
        int orderId = 1;

        when(placedOrderRepository.existsById(orderId)).thenReturn(false);

        PlacedOrderNotFound exception = assertThrows(PlacedOrderNotFound.class, () -> {
            placedOrderService.deleteByPlacedOrderId(orderId);
        });

        assertEquals("Order with id " + orderId + "not found", exception.getMessage());
    }

}