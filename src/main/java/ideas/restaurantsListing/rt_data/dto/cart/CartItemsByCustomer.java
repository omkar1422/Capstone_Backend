package ideas.restaurantsListing.rt_data.dto.cart;


public interface CartItemsByCustomer {
    Integer getCartId();

    Menu getMenu();

    Integer getQty();

    interface Menu {
        public Integer getMenuId();

        public Restaurant getRestaurant();

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
