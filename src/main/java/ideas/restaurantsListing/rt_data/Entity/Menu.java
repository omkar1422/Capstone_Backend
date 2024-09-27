package ideas.restaurantsListing.rt_data.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int menuId;

    @ManyToOne
    @JoinColumn(name = "restaurantId")
    @JsonBackReference
    private Restaurant restaurant;

    private String menuName;
    private float menuPrice;
    private String menuImage;
    private String menuDescription;
    private String menuType;

    public Menu() {

    }

    public String getMenuDescription() {
        return menuDescription;
    }

    public void setMenuDescription(String menuDescription) {
        this.menuDescription = menuDescription;
    }

    public Menu(int menuId, Restaurant restaurant, String menuName, Float menuPrice, String menuImage,
                String menuDescription, String menuType) {
        this.menuId = menuId;
        this.restaurant = restaurant;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuImage = menuImage;
        this.menuDescription = menuDescription;
        this.menuType = menuType;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public float getMenuPrice() {
        return menuPrice;
    }

    public void setMenuPrice(float menuPrice) {
        this.menuPrice = menuPrice;
    }

    public String getMenuImage() {
        return menuImage;
    }

    public void setMenuImage(String menuImage) {
        this.menuImage = menuImage;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "menuId=" + menuId +
                ", restaurant=" + restaurant +
                ", menuName='" + menuName + '\'' +
                ", menuPrice=" + menuPrice +
                ", menuImage='" + menuImage + '\'' +
                '}';
    }
}
