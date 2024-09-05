package ideas.restaurantsListing.rt_data.Entity;

import jakarta.persistence.*;

@Entity
public class PlacedOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int placedOrderId;

    @ManyToOne
    private Menu menu;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Restaurant restaurant;

    private float placedOrderPrice;
    private String delivery;
    private int placedOrderQty;

    public PlacedOrder() {

    }

    public PlacedOrder(int placedOrderId, Menu menu, Customer customer, Restaurant restaurant, float placedOrderPrice,
                       String delivery, int placedOrderQty) {
        this.placedOrderId = placedOrderId;
        this.menu = menu;
        this.customer = customer;
        this.restaurant = restaurant;
        this.placedOrderPrice = placedOrderPrice;
        this.delivery = delivery;
        this.placedOrderQty = placedOrderQty;
    }

    public int getPlacedOrderId() {
        return placedOrderId;
    }

    public void setPlacedOrderId(int placedOrderId) {
        this.placedOrderId = placedOrderId;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public float getPlacedOrderPrice() {
        return placedOrderPrice;
    }

    public void setPlacedOrderPrice(float placedOrderPrice) {
        this.placedOrderPrice = placedOrderPrice;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public int getPlacedOrderQty() {
        return placedOrderQty;
    }

    public void setPlacedOrderQty(int placedOrderQty) {
        this.placedOrderQty = placedOrderQty;
    }

    @Override
    public String toString() {
        return "PlacedOrder{" +
                "placedOrderId=" + placedOrderId +
                ", menu=" + menu +
                ", customer=" + customer +
                ", restaurant=" + restaurant +
                ", placedOrderPrice=" + placedOrderPrice +
                ", delivery='" + delivery + '\'' +
                ", placedOrderQty=" + placedOrderQty +
                '}';
    }
}
