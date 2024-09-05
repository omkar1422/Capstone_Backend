package ideas.restaurantsListing.rt_data.dto;

import ideas.restaurantsListing.rt_data.Entity.Customer;
import ideas.restaurantsListing.rt_data.Entity.Menu;
import ideas.restaurantsListing.rt_data.Entity.Restaurant;

public interface PlacedOrdersByRestaurant {
    public int getPlacedOrderId();

    public Menu getMenu();
    public Customer getCustomer();

    public float getPlacedOrderPrice();
    public String getDelivery();
    public int getPlacedOrderQty();

    public interface Menu {
        public int getMenuId();
        public String getMenuName();
        public float getMenuPrice();
        public String getMenuImage();
    }

    public interface Customer {
        public int getCustomerId();
        public String getCustomerName();
        public String getCustomerEmail();
        public String getCustomerPhone();
    }
}
