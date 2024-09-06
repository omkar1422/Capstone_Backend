package ideas.restaurantsListing.rt_data.dto.placedOrders;

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
