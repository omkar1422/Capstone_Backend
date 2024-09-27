package ideas.restaurantsListing.rt_data.Service;

import ideas.restaurantsListing.rt_data.Entity.Menu;
import ideas.restaurantsListing.rt_data.Entity.Restaurant;
import ideas.restaurantsListing.rt_data.Repository.MenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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
    public void shouldGetMenusByRestaurant() {

    }
}