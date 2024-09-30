package ideas.restaurantsListing.rt_data.Service;

import ideas.restaurantsListing.rt_data.Entity.Restaurant;
import ideas.restaurantsListing.rt_data.Exception.restaurant.RestaurantNotFoundException;
import ideas.restaurantsListing.rt_data.Repository.RestaurantRepository;
import ideas.restaurantsListing.rt_data.dto.restaurant.RestaurantById;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

class RestaurantServiceTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private RestaurantService restaurantService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldSaveListOfRestaurantsSuccessfully() {
        // Arrange
        Restaurant restaurant1 = new Restaurant(1,null,"Atithi",
                "atithi@gmail.com","baner, pune","7058743322",null,null);
        Restaurant restaurant2 = new Restaurant(2,null,"test name",
                "test email","test address","test pjone",null,null);
        List<Restaurant> restaurants = Arrays.asList(restaurant1, restaurant2);

        when(restaurantRepository.saveAll(restaurants)).thenReturn(restaurants);

        // Act
        List<Restaurant> savedRestaurants = restaurantService.saveListOfRestaurants(restaurants);

        // Assert
        assertEquals(restaurants.size(), savedRestaurants.size());
        verify(restaurantRepository, times(1)).saveAll(restaurants);
    }

    @Test
    public void shouldSaveListOfRestaurants_Exception() {
        // Arrange
        List<Restaurant> restaurants = Arrays.asList(new Restaurant(), new Restaurant()); // Create a list of mock Restaurant instances

        // Mock the behavior of the repository to throw an exception
        when(restaurantRepository.saveAll(anyList()))
                .thenThrow(new RuntimeException("Database error"));

        // Act and Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            restaurantService.saveListOfRestaurants(restaurants);
        });

        // Verify the exception message
        assertEquals("Failed to save the list of restaurants", exception.getMessage());
        assertEquals("Database error", exception.getCause().getMessage()); // Verify the cause of the exception
    }

    @Test
    public void shouldSaveRestaurant() {
        Restaurant restaurant = new Restaurant(1,null,"Atithi",
                "atithi@gmail.com","baner, pune","7058743322",null,null);
        when(restaurantRepository.save(restaurant)).thenReturn(restaurant);
        assertEquals(restaurant, restaurantService.saveRestaurant(restaurant));
    }

    @Test
    public void shouldThrowRuntimeExceptionWhenSavingRestaurantFails() {
        // Arrange
        Restaurant restaurant = new Restaurant(); // Create a sample restaurant object
        when(restaurantRepository.save(any(Restaurant.class))).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            restaurantService.saveRestaurant(restaurant);
        });

        assertEquals("Failed to save restaurant", exception.getMessage());
        verify(restaurantRepository, times(1)).save(restaurant); // Verify that save was called once
    }

    @Test
    public void shouldReturnPaginatedListOfRestaurants() {
        // Arrange
        int pageNo = 0;
        int pageSize = 5;
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(new Restaurant(/* Initialize with test data */));
        restaurants.add(new Restaurant(/* Initialize with test data */));
        Page<Restaurant> pagedResponse = new PageImpl<>(restaurants);

        when(restaurantRepository.findAll(any(Pageable.class))).thenReturn(pagedResponse);

        // Act
        List<Restaurant> result = restaurantService.getAllRestaurantsPaging(pageNo, pageSize);

        // Assert
        assertEquals(2, result.size()); // Check the size of the returned list
        verify(restaurantRepository, times(1)).findAll(PageRequest.of(pageNo, pageSize)); // Verify the method was called once
    }

    @Test
    public void shouldThrowRuntimeExceptionWhenRetrievingRestaurantsByPagingFails() {
        // Arrange
        int pageNo = 0;
        int pageSize = 5;
        when(restaurantRepository.findAll(any(Pageable.class))).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            restaurantService.getAllRestaurantsPaging(pageNo, pageSize);
        });

        assertEquals("Failed to retrieve paginated list of restaurants", exception.getMessage());
        verify(restaurantRepository, times(1)).findAll(PageRequest.of(pageNo, pageSize)); // Verify that findAll was called once
    }

    @Test
    public void shouldGetAllRestaurants() {
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(new Restaurant(1,null,null,null,null,null,null,null));
        restaurants.add(new Restaurant(2,null,null,null,null,null,null,null));
        when(restaurantRepository.findAll()).thenReturn(restaurants);
        assertEquals(restaurants, restaurantService.getAllRestaurants());
    }

    @Test
    public void shouldThrowRuntimeExceptionWhenRetrievingRestaurantsFails() {
        // Arrange
        when(restaurantRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            restaurantService.getAllRestaurants();
        });

        assertEquals("Failed to retrieve the list of restaurants", exception.getMessage());
        verify(restaurantRepository, times(1)).findAll(); // Verify that findAll was called once
    }

    @Test
    public void shouldGetRestaurantById() {
        RestaurantById restaurant = mock(RestaurantById.class);
        when(restaurant.getRestaurantId()).thenReturn(1);
        when(restaurant.getRestaurantName()).thenReturn("Test Restaurant");
        when(restaurant.getRestaurantEmail()).thenReturn("test@example.com");
        when(restaurant.getRestaurantAddress()).thenReturn("123 Test St");
        when(restaurant.getRestaurantPhone()).thenReturn("123-456-7890");

        when(restaurantRepository.findById(1)).thenReturn(Optional.of(restaurant));

        Optional<RestaurantById> foundRestaurant = restaurantService.getRestaurantById(1);

        assertTrue(foundRestaurant.isPresent());
        assertEquals(1, foundRestaurant.get().getRestaurantId());
        assertEquals("Test Restaurant", foundRestaurant.get().getRestaurantName());
        assertEquals("test@example.com", foundRestaurant.get().getRestaurantEmail());
        assertEquals("123 Test St", foundRestaurant.get().getRestaurantAddress());
        assertEquals("123-456-7890", foundRestaurant.get().getRestaurantPhone());

        RestaurantById.MenusByRestaurant menu = mock(RestaurantById.MenusByRestaurant.class);
        when(menu.getMenuId()).thenReturn(1);
        when(menu.getMenuName()).thenReturn("Test Menu");
        when(menu.getMenuPrice()).thenReturn(9.99f);
        when(menu.getMenuImage()).thenReturn("test_image.jpg");

        when(restaurant.getMenus()).thenReturn(Arrays.asList(menu));

        when(restaurantRepository.findById(1)).thenReturn(Optional.of(restaurant));

        List<RestaurantById.MenusByRestaurant> menus = foundRestaurant.get().getMenus();
        assertEquals(1, menus.size());
        assertEquals(1, menus.get(0).getMenuId());
        assertEquals("Test Menu", menus.get(0).getMenuName());
        assertEquals(9.99f, menus.get(0).getMenuPrice());
        assertEquals("test_image.jpg", menus.get(0).getMenuImage());

        verify(restaurantRepository, times(1)).findById(1);

    }

    @Test
    public void shouldDeleteRestaurantById() {
        int restaurantId = 1;
        when(restaurantRepository.existsById(restaurantId)).thenReturn(true);
        restaurantService.deleteRestaurantById(restaurantId);
        verify(restaurantRepository, times(1)).deleteById(restaurantId);
    }

    @Test
    public void shouldThrowRestaurantNotFoundExceptionWhenRestaurantDoesNotExistInGetRestaurantById() {
        // Arrange
        int restaurantId = 1;
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

        // Act & Assert
        RestaurantNotFoundException exception = assertThrows(RestaurantNotFoundException.class, () -> {
            restaurantService.getRestaurantById(restaurantId);
        });

        assertEquals("Restaurant with id 1 doesn't exist", exception.getMessage());
        verify(restaurantRepository, times(1)).findById(restaurantId);
    }

    @Test
    public void shouldThrowRuntimeExceptionWhenRetrievingRestaurantByIdFails() {
        // Arrange
        int restaurantId = 1;
        when(restaurantRepository.findById(restaurantId)).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            restaurantService.getRestaurantById(restaurantId);
        });

        assertEquals("Failed to retrieve restaurant by ID", exception.getMessage());
        assertEquals("Database error", exception.getCause().getMessage());
        verify(restaurantRepository, times(1)).findById(restaurantId);
    }

    @Test
    public void shouldThrowRestaurantNotFoundExceptionWhenRestaurantDoesNotExistInDeleteRestaurantById() {
        // Arrange
        int restaurantId = 1;
        when(restaurantRepository.existsById(restaurantId)).thenReturn(false);

        // Act & Assert
        RestaurantNotFoundException exception = assertThrows(RestaurantNotFoundException.class, () -> {
            restaurantService.deleteRestaurantById(restaurantId);
        });

        assertEquals("Restaurant with id 1 doesn't exist", exception.getMessage());
        verify(restaurantRepository, times(1)).existsById(restaurantId);
        verify(restaurantRepository, never()).deleteById(restaurantId); // Ensure delete is not called
    }

    @Test
    public void shouldThrowRuntimeExceptionWhenDeletingRestaurantByIdFails() {
        // Arrange
        int restaurantId = 1;
        when(restaurantRepository.existsById(restaurantId)).thenReturn(true);
        doThrow(new RuntimeException("Database error")).when(restaurantRepository).deleteById(restaurantId);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            restaurantService.deleteRestaurantById(restaurantId);
        });

        assertEquals("Failed to delete restaurant by ID", exception.getMessage());
        assertEquals("Database error", exception.getCause().getMessage());
        verify(restaurantRepository, times(1)).deleteById(restaurantId);
    }
}