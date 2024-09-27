package ideas.restaurantsListing.rt_data.Service;

import ideas.restaurantsListing.rt_data.Entity.Menu;
import ideas.restaurantsListing.rt_data.Entity.Restaurant;
import ideas.restaurantsListing.rt_data.Repository.MenuRepository;
import ideas.restaurantsListing.rt_data.dto.menu.MenusByRestaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    public List<Menu> saveListOfMenus(List<Menu> menus) {
        return menuRepository.saveAll(menus);
    }

    public Menu saveMenu(Menu menu) {
        return menuRepository.save(menu);
    }

    public List<MenusByRestaurant> getMenusByRestaurant(int id) {
        return menuRepository.findByRestaurant(
                new Restaurant(id,null,null,null,null,null,null,null)
        );
    }
}
