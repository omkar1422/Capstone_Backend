package ideas.restaurantsListing.rt_data.dto.restaurant;

import ideas.restaurantsListing.rt_data.Entity.Menu;

import java.util.List;
public interface RestaurantById {
    public int getRestaurantId();
    public List<MenusByRestaurant> getMenus();
    public String getRestaurantName();
    public String getRestaurantEmail();
    public String getRestaurantAddress();
    public String getRestaurantPhone();

    public interface MenusByRestaurant {
        public int getMenuId();
        public String getMenuName();
        public float getMenuPrice();
        public String getMenuImage();
    }
}

