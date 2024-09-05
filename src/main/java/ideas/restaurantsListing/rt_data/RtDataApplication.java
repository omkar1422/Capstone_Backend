package ideas.restaurantsListing.rt_data;

import ideas.restaurantsListing.rt_data.Entity.*;
import ideas.restaurantsListing.rt_data.Repository.*;
import ideas.restaurantsListing.rt_data.dto.CartItemsByCustomer;
import ideas.restaurantsListing.rt_data.dto.PlacedOrdersByACustomer;
import ideas.restaurantsListing.rt_data.dto.PlacedOrdersByRestaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class RtDataApplication implements CommandLineRunner {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	RestaurantRepository restaurantRepository;

	@Autowired
	MenuRepository menuRepository;

	@Autowired
	PlacedOrderRepository placedOrderRepository;

	@Autowired
	RatingRepository ratingRepository;

	@Autowired
	CartRepository cartRepository;

	public static void main(String[] args) {
		SpringApplication.run(RtDataApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

//		TODO: Create a customer
//		Customer customer = new Customer(1, "John Smilga", "john@gmail.com", "password", "7058743322");
//		Customer c = customerRepository.save(customer);
//		System.out.println(c);

//		TODO: View all customers
//		List<Customer> customers = (List<Customer>) customerRepository.findAll();
//		for (Customer customer: customers)
//			System.out.println(customer);

//		TODO: find customer by id
//		Optional<Customer> customer =  customerRepository.findById(1);
//		System.out.println(customer);

//		TODO: Create a restaurant
//		Restaurant restaurant = (Restaurant) restaurantRepository.save(
//				new Restaurant(0,"Atithi","atithi@gmail.com","baner," +
//						" pune", "7058743322")
//		);
//		System.out.println(restaurant);

//		Restaurant restaurant = new Restaurant(0, "Maratha Samrat", "marathaSamrat@gmail.com", "baner, pune", "8459637251");
//		Restaurant res = restaurantRepository.save(restaurant);


//		TODO: find all restaurants
//		List<Restaurant> restaurants = (List<Restaurant>) restaurantRepository.findAll();
//		System.out.println(restaurants);

//		TODO: find restaurant by id
//		Optional<Restaurant> restaurant = restaurantRepository.findById(1);
//		System.out.println(restaurant);

//		TODO: delete restaurant by id
//		restaurantRepository.deleteById(1);

//		TODO: Create a new menu
//		Menu menu1 = (Menu) menuRepository.save(
//				new Menu(0,
//						new Restaurant(1,null,null,null,null),
//						"Margherita pizza", 500.0f, "")
//		);
//		System.out.println(menu1);
//
//		Menu menu2 = (Menu) menuRepository.save(
//				new Menu(0,
//						new Restaurant(1,null,null,null,null),
//						"White sauce pasta", 400.0f, "")
//		);
//		System.out.println(menu2);
//
//		Menu menu3 = (Menu) menuRepository.save(
//		new Menu(0,
//				new Restaurant(1,null,null,null,null),
//				"Cheese Burger", 300.0f, "")
//		);
//		System.out.println(menu3);

//		TODO: create a placed order
//		PlacedOrder order = placedOrderRepository.save(new PlacedOrder(0,
//				new Menu(1,null,null,null,null),
//				new Customer(1,null,null,null,null),
//				new Restaurant(1,null,null,null,null),
//				620.0f, "not delivered", 1)
//		);
//		System.out.println(order);

//		TODO: find placed order by Customer
//		List<PlacedOrdersByACustomer> orders = placedOrderRepository.findByCustomer(
//				new Customer(1,null,null,null,null)
//		);
//		for(PlacedOrdersByACustomer order: orders) {
//			System.out.println(order.getPlacedOrderId());
//			System.out.println(order.getMenu());
//			System.out.println(order.getPlacedOrderQty());
//			System.out.println(order.getPlacedOrderPrice());
//			System.out.println(order.getDelivery());
//		}

//		TODO: find placed order by restaurant
//		List<PlacedOrdersByRestaurant> orders = placedOrderRepository.findByRestaurant(
//				new Restaurant(1,null,null,null,null)
//		);
//		for(PlacedOrdersByRestaurant order: orders) {
//			System.out.println(order.getPlacedOrderId());
//			System.out.println(order.getMenu());
//			System.out.println(order.getCustomer());
//
//		}

//		TODO: delete particular order of a particular customer
//		find placed order by customer first and then delete by it placedorderId
//		placedOrderRepository.deleteByPlacedOrderId(2);

//		TODO: get all menus of a restaurant
//		List<MenusByRestaurant> menus = menuRepository.findByRestaurant(
//				new Restaurant(1,null,null,null,null)
//		);
//		for(MenusByRestaurant menu: menus) {
//			System.out.println(menu.getMenuId());
//			System.out.println(menu.getMenuName());
//			System.out.println(menu.getMenuPrice());
//			System.out.println(menu.getMenuImage());
//		}

//		TODO: Create Rating
//		Rating rating = ratingRepository.save(
//				new Rating(0,
//						new Customer(1,null,null,null,null),
//						new Restaurant(1,null,null,null,null),
//						4.5f)
//		);
//		System.out.println(rating);

//		Rating rating = ratingRepository.save(new Rating(0,
//				new Customer(2,null,null,null,null),
//				new Restaurant(1,null,null,null,null),
//				4.0f, "Preety good views"));
//		System.out.println(rating);

//		TODO: view rating submitted by a customer to a restaurant (work on its stackoverflow prblm in dto)
//		RatingOfRestaurantByCustomer rating = ratingRepository.findByRestaurantAndCustomer(
//				new Restaurant(1,null,null,null,null),
//				new Customer(1,null,null,null,null)
//		);
//		System.out.println(rating);

//		System.out.println(rating.getRatingId());
//		System.out.println(rating.getCustomer());
//		System.out.println(rating.getRestaurant());
//		System.out.println(rating.getRating());

//		TODO: average rating of a restaurant
//		Double avgRating = ratingRepository.findAverageRatingByRestaurantId(
//				1
//		);
//		System.out.println(avgRating);

//		TODO: create a cart for a customer
//		Cart cart1 = cartRepository.save(new Cart(0,
//				new Customer(1,null,null,null,null),
//				new Menu(2,null,null,null,null),
//				2));
//		System.out.println(cart1);
//
//		Cart cart2 = cartRepository.save(new Cart(0,
//				new Customer(1,null,null,null,null),
//				new Menu(3,null,null,null,null),
//				1));
//		System.out.println(cart2);

//		TODO: get cart by a customer
//		List<CartItemsByCustomer> cartItems = cartRepository.findByCustomer(
//				new Customer(1,null,null,null,null)
//		);
//
//		for(CartItemsByCustomer item: cartItems) {
//			System.out.println(item.getCartId());
//			System.out.println(item.getMenu());
//			System.out.println(item.getQty());
//		}

//		TODO: find total price of cart by customer
//		System.out.println(cartRepository.findTotalPriceByCustomerId(1));
	}
}
