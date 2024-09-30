package ideas.restaurantsListing.rt_data.Service;

import ideas.restaurantsListing.rt_data.Entity.Menu;
import ideas.restaurantsListing.rt_data.Entity.Restaurant;
import ideas.restaurantsListing.rt_data.Repository.MenuRepository;
import ideas.restaurantsListing.rt_data.dto.menu.MenusByRestaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MenuServiceTest {

    @Mock
    private MenuRepository menuRepository;

    @InjectMocks
    private MenuService menuService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldSaveMenu() {
        Restaurant restaurant = new Restaurant(1,null, "test name",
                "test email", "test address", "test phone", "test image",null);
        Menu menu = new Menu(1,restaurant,"test menu name", 50.0f, "test image", "test desc", "test food type");
        when(menuRepository.save(menu)).thenReturn(menu);
        assertEquals(menu, menuService.saveMenu(menu));
    }

    @Test
    void testGetMenusByRestaurant() {
        int restaurantId = 1;

        MenusByRestaurant menu1 = mock(MenusByRestaurant.class);
        MenusByRestaurant menu2 = mock(MenusByRestaurant.class);

        when(menu1.getMenuId()).thenReturn(1);
        when(menu1.getMenuName()).thenReturn("Menu1");
        when(menu1.getMenuPrice()).thenReturn(10.0f);
        when(menu1.getMenuImage()).thenReturn("image1.jpg");

        when(menu2.getMenuId()).thenReturn(2);
        when(menu2.getMenuName()).thenReturn("Menu2");
        when(menu2.getMenuPrice()).thenReturn(12.5f);
        when(menu2.getMenuImage()).thenReturn("image2.jpg");

        List<MenusByRestaurant> expectedMenus = List.of(menu1, menu2);

        when(menuRepository.findByRestaurant(any(Restaurant.class))).thenReturn(expectedMenus);

        List<MenusByRestaurant> actualMenus = menuService.getMenusByRestaurant(restaurantId);

        assertNotNull(actualMenus);
        assertEquals(expectedMenus.size(), actualMenus.size());
        assertEquals(expectedMenus, actualMenus);
        verify(menuRepository, times(1)).findByRestaurant(any(Restaurant.class));
    }

    @Test
    void testGetMenusByRestaurant_ThrowsException() {
        int restaurantId = 1;
        Restaurant restaurant = new Restaurant(restaurantId, null, null, null, null, null, null, null);

        when(menuRepository.findByRestaurant(any(Restaurant.class))).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> {
            menuService.getMenusByRestaurant(restaurantId);
        }, "Expected RuntimeException to be thrown");

        verify(menuRepository, times(1)).findByRestaurant(any(Restaurant.class));
    }

    @Test
    public void saveListOfMenus_ShouldReturnSavedMenus_WhenSuccessful() {
        // Arrange
        List<Menu> menus = Arrays.asList(new Menu(), new Menu()); // Initialize with test data
        when(menuRepository.saveAll(menus)).thenReturn(menus);

        // Act
        List<Menu> savedMenus = menuService.saveListOfMenus(menus);

        // Assert
        assertEquals(menus.size(), savedMenus.size());
        assertEquals(menus, savedMenus);
    }

    @Test
    public void saveListOfMenus_ShouldThrowRuntimeException_WhenExceptionOccurs() {
        // Arrange
        List<Menu> menus = Arrays.asList(new Menu(), new Menu()); // Initialize with test data
        when(menuRepository.saveAll(menus)).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            menuService.saveListOfMenus(menus);
        });

        assertEquals("Database error", exception.getMessage());
    }

    @Test
    public void saveMenu_ShouldThrowRuntimeException_WhenExceptionOccurs() {
        // Arrange
        Menu menu = new Menu(); // Initialize with test data
        when(menuRepository.save(menu)).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            menuService.saveMenu(menu);
        });

        assertEquals("Database error", exception.getMessage());
    }
}