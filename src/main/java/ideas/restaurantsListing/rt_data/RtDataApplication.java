package ideas.restaurantsListing.rt_data;

import ideas.restaurantsListing.rt_data.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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


//		TODO: delete particular order of a particular customer
//		find placed order by customer first and then delete by it placedorderId
//		placedOrderRepository.deleteByPlacedOrderId(2);


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
