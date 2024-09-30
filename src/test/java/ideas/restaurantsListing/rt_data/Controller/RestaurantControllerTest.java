package ideas.restaurantsListing.rt_data.Controller;

import ideas.restaurantsListing.rt_data.Entity.Restaurant;
import ideas.restaurantsListing.rt_data.Exception.restaurant.RestaurantNotFoundException;
import ideas.restaurantsListing.rt_data.Service.RestaurantService;
import ideas.restaurantsListing.rt_data.dto.restaurant.RestaurantById;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class RestaurantControllerTest {

    @Mock
    private RestaurantService restaurantService;

    @InjectMocks
    private RestaurantController restaurantController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldSaveRestaurant() {
        Restaurant restaurant = new Restaurant(1,null,"test name",
                "test@gmail.com","test address","7058743322","test image","veg");
        when(restaurantService.saveRestaurant(restaurant)).thenReturn(restaurant);
        assertEquals(restaurant, restaurantService.saveRestaurant(restaurant));
    }

    @Test
    public void shouldSaveRestaurant_Failure() {
        // Arrange
        Restaurant restaurant = new Restaurant(); // Set the necessary fields
        when(restaurantService.saveRestaurant(any(Restaurant.class))).thenThrow(new RuntimeException());

        // Act
        ResponseEntity<?> response = restaurantController.saveRestaurant(restaurant);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Failed to save restaurant", response.getBody());
    }

    @Test
    public void shouldSaveListOfRestaurants_Success() {
        // Sample data
        Restaurant restaurant1 = new Restaurant(1,null,"test name",
                "test@gmail.com","test address","7058743322","test image","veg");
        Restaurant restaurant2 = new Restaurant(2,null,"test name",
                "test@gmail.com","test address","7058743322","test image","veg");
        List<Restaurant> restaurants = Arrays.asList(restaurant1, restaurant2);

        when(restaurantService.saveListOfRestaurants(restaurants)).thenReturn(restaurants);

        ResponseEntity<List<Restaurant>> response = restaurantController.saveListOfRestaurants(restaurants);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(restaurants, response.getBody());
    }

    @Test
    public void shouldGetAllRestaurants_Failure() {
        // Arrange
        when(restaurantService.getAllRestaurants()).thenThrow(new RuntimeException());

        // Act
        ResponseEntity<List<Restaurant>> response = restaurantController.getAllRestaurants();

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void shouldGetRestaurantsByPaging_Success() {
        // Arrange
        List<Restaurant> restaurants = new ArrayList<>();
        when(restaurantService.getAllRestaurantsPaging(0, 10)).thenReturn(restaurants);

        // Act
        ResponseEntity<List<Restaurant>> response = restaurantController.getRestaurantsByPaging(0, 10);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(restaurants, response.getBody());
    }

    @Test
    public void shouldGetRestaurantsByPaging_Failure() {
        // Arrange
        when(restaurantService.getAllRestaurantsPaging(0, 10)).thenThrow(new RuntimeException());

        // Act
        ResponseEntity<List<Restaurant>> response = restaurantController.getRestaurantsByPaging(0, 10);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void shouldGetRestaurantById_Success() {
        // Arrange
        RestaurantById restaurantById = mock(RestaurantById.class);
        when(restaurantService.getRestaurantById(1)).thenReturn(Optional.of(restaurantById));

        // Act
        ResponseEntity<?> response = restaurantController.getRestaurantById(1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Optional.of(restaurantById), response.getBody());
    }

    @Test
    public void shouldGetRestaurantById_NotFound() {
        // Arrange
        when(restaurantService.getRestaurantById(1)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = restaurantController.getRestaurantById(1);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Restaurant with id 1 not found", response.getBody());
    }

    @Test
    public void shouldGetRestaurantById_Failure() {
        // Arrange
        when(restaurantService.getRestaurantById(1)).thenThrow(new RuntimeException());

        // Act
        ResponseEntity<?> response = restaurantController.getRestaurantById(1);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Failed to retrieve restaurant", response.getBody());
    }

    @Test
    public void shouldDeleteRestaurantById_Success() {
        // Arrange
        doNothing().when(restaurantService).deleteRestaurantById(1);

        // Act
        ResponseEntity<?> response = restaurantController.deleteRestaurantById(1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Restaurant deleted successfully", response.getBody());
    }

    @Test
    public void shouldDeleteRestaurantById_NotFound() {
        // Arrange
        doThrow(new RestaurantNotFoundException("Restaurant with id 1 not found"))
                .when(restaurantService).deleteRestaurantById(1);

        // Act
        ResponseEntity<?> response = restaurantController.deleteRestaurantById(1);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Restaurant with id 1 not found", response.getBody());
    }

    @Test
    public void shouldDeleteRestaurantById_Failure() {
        // Arrange
        doThrow(new RuntimeException()).when(restaurantService).deleteRestaurantById(1);

        // Act
        ResponseEntity<?> response = restaurantController.deleteRestaurantById(1);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Failed to delete restaurant", response.getBody());
    }
}