package ideas.restaurantsListing.rt_data.Controller;

import ideas.restaurantsListing.rt_data.Entity.PlacedOrder;
import ideas.restaurantsListing.rt_data.Service.PlacedOrderService;
import ideas.restaurantsListing.rt_data.dto.placedOrders.PlacedOrdersByACustomer;
import ideas.restaurantsListing.rt_data.dto.placedOrders.PlacedOrdersByRestaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlacedOrderControllerTest {

    @Mock
    private PlacedOrderService placedOrderService;

    @InjectMocks
    private PlacedOrderController placedOrderController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // Test for savePlacedOrder
    @Test
    public void testSavePlacedOrder_Success() {
        PlacedOrder mockPlacedOrder = mock(PlacedOrder.class);
        when(placedOrderService.savePlacedOrder(mockPlacedOrder)).thenReturn(mockPlacedOrder);

        PlacedOrder savedOrder = placedOrderController.savePlacedOrder(mockPlacedOrder);

        assertNotNull(savedOrder);
        verify(placedOrderService, times(1)).savePlacedOrder(mockPlacedOrder);
    }

    @Test
    public void testSavePlacedOrder_Exception() {

        PlacedOrder mockPlacedOrder = mock(PlacedOrder.class);
        when(placedOrderService.savePlacedOrder(mockPlacedOrder)).thenThrow(new RuntimeException("Service error"));

        assertThrows(RuntimeException.class, () -> {
            placedOrderController.savePlacedOrder(mockPlacedOrder);
        });

        verify(placedOrderService, times(1)).savePlacedOrder(mockPlacedOrder);
    }

    // Test for deleteRestaurantById
    @Test
    public void testDeleteRestaurantById_Success() {
        int orderId = 1;
        when(placedOrderService.deleteByPlacedOrderId(orderId)).thenReturn(orderId);

        int deletedId = placedOrderController.deleteRestaurantById(orderId);

        assertEquals(orderId, deletedId);
        verify(placedOrderService, times(1)).deleteByPlacedOrderId(orderId);
    }

    @Test
    public void testDeleteRestaurantById_Exception() {
        int orderId = 1;
        when(placedOrderService.deleteByPlacedOrderId(orderId)).thenThrow(new RuntimeException("Service error"));

        assertThrows(RuntimeException.class, () -> {
            placedOrderController.deleteRestaurantById(orderId);
        });

        verify(placedOrderService, times(1)).deleteByPlacedOrderId(orderId);
    }

    @Test
    public void testPlaceOrderFromCart_Success() {

        int customerId = 1;
        doNothing().when(placedOrderService).placeOrderAndClearCart(customerId);

        placedOrderController.placeOrderFromCart(customerId);

        verify(placedOrderService, times(1)).placeOrderAndClearCart(customerId);
    }

    @Test
    public void testPlaceOrderFromCart_Exception() {

        int customerId = 1;
        doThrow(new RuntimeException("Service error")).when(placedOrderService).placeOrderAndClearCart(customerId);

        assertThrows(RuntimeException.class, () -> {
            placedOrderController.placeOrderFromCart(customerId);
        });

        verify(placedOrderService, times(1)).placeOrderAndClearCart(customerId);
    }

    @Test
    public void testGetPlacedOrdersByCustomer_Success() {
        // Arrange
        int customerId = 1;

        // Create mock DTOs
        PlacedOrdersByACustomer mockPlacedOrder = mock(PlacedOrdersByACustomer.class);
        PlacedOrdersByACustomer.Menu mockMenu = mock(PlacedOrdersByACustomer.Menu.class);
        PlacedOrdersByACustomer.Menu.Restaurant mockRestaurant = mock(PlacedOrdersByACustomer.Menu.Restaurant.class);

        when(mockPlacedOrder.getPlacedOrderId()).thenReturn(1);
        when(mockPlacedOrder.getPlacedOrderPrice()).thenReturn(100.0f);
        when(mockPlacedOrder.getDelivery()).thenReturn("Home");
        when(mockPlacedOrder.getPlacedOrderQty()).thenReturn(2);
        when(mockPlacedOrder.getMenu()).thenReturn(mockMenu);

        when(mockMenu.getMenuId()).thenReturn(1);
        when(mockMenu.getMenuName()).thenReturn("Pizza");
        when(mockMenu.getMenuPrice()).thenReturn(50.0f);
        when(mockMenu.getMenuImage()).thenReturn("pizza.jpg");
        when(mockMenu.getRestaurant()).thenReturn(mockRestaurant);

        when(mockRestaurant.getRestaurantId()).thenReturn(1);
        when(mockRestaurant.getRestaurantName()).thenReturn("The Pizza Place");
        when(mockRestaurant.getRestaurantEmail()).thenReturn("info@pizzaplace.com");
        when(mockRestaurant.getRestaurantAddress()).thenReturn("123 Pizza St.");
        when(mockRestaurant.getRestaurantPhone()).thenReturn("1234567890");

        // Mock service to return a list of orders
        List<PlacedOrdersByACustomer> mockPlacedOrderList = new ArrayList<>();
        mockPlacedOrderList.add(mockPlacedOrder);
        when(placedOrderService.placedOrdersByACustomer(customerId)).thenReturn(mockPlacedOrderList);

        // Act
        List<PlacedOrdersByACustomer> placedOrders = placedOrderController.getPlacedOrdersByCustomer(customerId);

        // Assert
        assertNotNull(placedOrders);
        assertEquals(1, placedOrders.size());
        assertEquals(1, placedOrders.get(0).getPlacedOrderId());
        assertEquals("Pizza", placedOrders.get(0).getMenu().getMenuName());
        assertEquals("The Pizza Place", placedOrders.get(0).getMenu().getRestaurant().getRestaurantName());
        verify(placedOrderService, times(1)).placedOrdersByACustomer(customerId);
    }

    @Test
    public void testGetPlacedOrdersByCustomer_Exception() {
        // Arrange
        int customerId = 1;
        when(placedOrderService.placedOrdersByACustomer(customerId)).thenThrow(new RuntimeException("Service error"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            placedOrderController.getPlacedOrdersByCustomer(customerId);
        });

        verify(placedOrderService, times(1)).placedOrdersByACustomer(customerId);
    }

    @Test
    public void testGetPlacedOrdersByRestaurant_Success() {
        // Arrange
        int restaurantId = 1;

        // Create mock DTOs
        PlacedOrdersByRestaurant mockPlacedOrder = mock(PlacedOrdersByRestaurant.class);
        PlacedOrdersByRestaurant.Menu mockMenu = mock(PlacedOrdersByRestaurant.Menu.class);
        PlacedOrdersByRestaurant.Customer mockCustomer = mock(PlacedOrdersByRestaurant.Customer.class);

        when(mockPlacedOrder.getPlacedOrderId()).thenReturn(1);
        when(mockPlacedOrder.getPlacedOrderPrice()).thenReturn(200.0f);
        when(mockPlacedOrder.getDelivery()).thenReturn("Home");
        when(mockPlacedOrder.getPlacedOrderQty()).thenReturn(3);
        when(mockPlacedOrder.getMenu()).thenReturn(mockMenu);
        when(mockPlacedOrder.getCustomer()).thenReturn(mockCustomer);

        when(mockMenu.getMenuId()).thenReturn(1);
        when(mockMenu.getMenuName()).thenReturn("Burger");
        when(mockMenu.getMenuPrice()).thenReturn(50.0f);
        when(mockMenu.getMenuImage()).thenReturn("burger.jpg");

        when(mockCustomer.getCustomerId()).thenReturn(1);
        when(mockCustomer.getCustomerName()).thenReturn("John Doe");
        when(mockCustomer.getCustomerEmail()).thenReturn("john.doe@example.com");
        when(mockCustomer.getCustomerPhone()).thenReturn("1234567890");

        // Mock service to return a list of orders
        List<PlacedOrdersByRestaurant> mockPlacedOrderList = new ArrayList<>();
        mockPlacedOrderList.add(mockPlacedOrder);
        when(placedOrderService.placedOrdersByRestaurant(restaurantId)).thenReturn(mockPlacedOrderList);

        // Act
        List<PlacedOrdersByRestaurant> placedOrders = placedOrderController.getPlacedOrdersByRestaurant(restaurantId);

        // Assert
        assertNotNull(placedOrders);
        assertEquals(1, placedOrders.size());
        assertEquals(1, placedOrders.get(0).getPlacedOrderId());
        assertEquals("Burger", placedOrders.get(0).getMenu().getMenuName());
        assertEquals("John Doe", placedOrders.get(0).getCustomer().getCustomerName());
        verify(placedOrderService, times(1)).placedOrdersByRestaurant(restaurantId);
    }

    @Test
    public void testGetPlacedOrdersByRestaurant_Exception() {
        // Arrange
        int restaurantId = 1;
        when(placedOrderService.placedOrdersByRestaurant(restaurantId)).thenThrow(new RuntimeException("Service error"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            placedOrderController.getPlacedOrdersByRestaurant(restaurantId);
        });

        verify(placedOrderService, times(1)).placedOrdersByRestaurant(restaurantId);
    }
}