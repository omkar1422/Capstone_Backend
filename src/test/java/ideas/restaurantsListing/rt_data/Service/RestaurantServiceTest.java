package ideas.restaurantsListing.rt_data.Service;

import ideas.restaurantsListing.rt_data.Entity.Restaurant;
import ideas.restaurantsListing.rt_data.Repository.RestaurantRepository;
import ideas.restaurantsListing.rt_data.dto.restaurant.RestaurantById;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    public void shouldSaveRestaurant() {
        Restaurant restaurant = new Restaurant(1,null,"Atithi",
                "atithi@gmail.com","baner, pune","7058743322",null,null);
        when(restaurantRepository.save(restaurant)).thenReturn(restaurant);
        assertEquals(restaurant, restaurantService.saveRestaurant(restaurant));
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
        restaurantService.deleteRestaurantById(restaurantId);
        verify(restaurantRepository, times(1)).deleteById(restaurantId);
    }
}