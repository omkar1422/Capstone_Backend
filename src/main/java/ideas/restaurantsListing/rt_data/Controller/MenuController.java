package ideas.restaurantsListing.rt_data.Controller;

import ideas.restaurantsListing.rt_data.Entity.Menu;
import ideas.restaurantsListing.rt_data.Service.MenuService;
import ideas.restaurantsListing.rt_data.dto.menu.MenusByRestaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantListings/api/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public Menu saveMenu(@RequestBody Menu menu) {
        return menuService.saveMenu(menu);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CUSTOMER')")
    @GetMapping("/{restaurantId}")
    public List<MenusByRestaurant> getMenusByRestaurant(@PathVariable("restaurantId") int id) {
        return menuService.getMenusByRestaurant(id);
    }
}
