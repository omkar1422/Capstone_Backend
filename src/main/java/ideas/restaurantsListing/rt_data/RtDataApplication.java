package ideas.restaurantsListing.rt_data;

import ideas.restaurantsListing.rt_data.Entity.Customer;
import ideas.restaurantsListing.rt_data.Entity.Restaurant;
import ideas.restaurantsListing.rt_data.Repository.*;
import ideas.restaurantsListing.rt_data.dto.rating.RatingOfRestaurantByCustomer;
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

//		TODO: find total price of cart by customer
//		System.out.println(cartRepository.findTotalPriceByCustomerId(1));
	}
}
