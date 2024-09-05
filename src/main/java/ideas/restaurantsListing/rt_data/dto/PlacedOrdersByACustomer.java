package ideas.restaurantsListing.rt_data.dto;

import ideas.restaurantsListing.rt_data.Entity.Menu;
import ideas.restaurantsListing.rt_data.Entity.Restaurant;

public interface PlacedOrdersByACustomer {
    public int getPlacedOrderId();

    public Menu getMenu();

    public float getPlacedOrderPrice();
    public String getDelivery();
    public int getPlacedOrderQty();

    public interface Menu {
        public int getMenuId();

        public Restaurant getRestaurant();

        public String getMenuName();
        public float getMenuPrice();
        public String getMenuImage();

        public interface Restaurant {
            public int getRestaurantId();
            public String getRestaurantName();
            public String getRestaurantEmail();
            public String getRestaurantAddress();
            public String getRestaurantPhone();
        }
    }
}
