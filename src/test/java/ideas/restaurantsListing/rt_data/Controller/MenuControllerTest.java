package ideas.restaurantsListing.rt_data.Controller;

import ideas.restaurantsListing.rt_data.Controller.MenuController;
import ideas.restaurantsListing.rt_data.Entity.Menu;
import ideas.restaurantsListing.rt_data.Service.MenuService;
import ideas.restaurantsListing.rt_data.dto.menu.MenusByRestaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class MenuControllerTest {

    @InjectMocks
    private MenuController menuController;

    @Mock
    private MenuService menuService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void saveListOfMenus_ShouldReturnMenus_WhenSuccessful() {
        // Arrange
        List<Menu> menus = Arrays.asList(new Menu(), new Menu());
        when(menuService.saveListOfMenus(menus)).thenReturn(menus);

        // Act
        List<Menu> result = menuController.saveListOfMenus(menus);

        // Assert
        assertEquals(menus, result);
    }

    @Test
    public void saveListOfMenus_ShouldThrowRuntimeException_WhenExceptionOccurs() {
        // Arrange
        List<Menu> menus = Arrays.asList(new Menu(), new Menu());
        when(menuService.saveListOfMenus(menus)).thenThrow(new RuntimeException("Error saving menus"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            menuController.saveListOfMenus(menus);
        });

        assertEquals("Error saving menus", exception.getMessage());
    }

    @Test
    public void saveMenu_ShouldReturnMenu_WhenSuccessful() {
        // Arrange
        Menu menu = new Menu(); // Initialize with test data
        when(menuService.saveMenu(menu)).thenReturn(menu);

        // Act
        Menu result = menuController.saveMenu(menu);

        // Assert
        assertEquals(menu, result);
    }

    @Test
    public void saveMenu_ShouldThrowRuntimeException_WhenExceptionOccurs() {
        // Arrange
        Menu menu = new Menu(); // Initialize with test data
        when(menuService.saveMenu(menu)).thenThrow(new RuntimeException("Error saving menu"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            menuController.saveMenu(menu);
        });

        assertEquals("Error saving menu", exception.getMessage());
    }

    @Test
    public void getMenusByRestaurant_ShouldReturnMenus_WhenSuccessful() {
        // Arrange
        int restaurantId = 1;
        List<MenusByRestaurant> menusByRestaurant = Arrays.asList(mock(MenusByRestaurant.class));
        when(menuService.getMenusByRestaurant(restaurantId)).thenReturn(menusByRestaurant);

        // Act
        List<MenusByRestaurant> result = menuController.getMenusByRestaurant(restaurantId);

        // Assert
        assertEquals(menusByRestaurant, result);
    }

    @Test
    public void getMenusByRestaurant_ShouldThrowRuntimeException_WhenExceptionOccurs() {
        // Arrange
        int restaurantId = 1;
        when(menuService.getMenusByRestaurant(restaurantId)).thenThrow(new RuntimeException("Error fetching menus"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            menuController.getMenusByRestaurant(restaurantId);
        });

        assertEquals("Error fetching menus", exception.getMessage());
    }
}
