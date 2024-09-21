package ideas.restaurantsListing.rt_data.dto.cart;


import ideas.restaurantsListing.rt_data.Entity.Customer;

public interface CartItemsByCustomer {
    Integer getCartId();

    Menu getMenu();
    Customer getCustomer();

    Integer getQty();

    interface Customer {
        public int getCustomerId();
    }

    interface Menu {
        public Integer getMenuId();

        public Restaurant getRestaurant();

        public float getMenuPrice();
        public String getMenuName();
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
