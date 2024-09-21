package ideas.restaurantsListing.rt_data.Service;

import ideas.restaurantsListing.rt_data.Entity.Customer;
import ideas.restaurantsListing.rt_data.Entity.Rating;
import ideas.restaurantsListing.rt_data.Entity.Restaurant;
import ideas.restaurantsListing.rt_data.Repository.RatingRepository;
import ideas.restaurantsListing.rt_data.dto.rating.RatingOfRestaurantByCustomer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RatingServiceTest {

    @Mock
    private RatingRepository ratingRepository;

    @InjectMocks
    private RatingService ratingService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldSaveRating() {
        Rating rating = new Rating(1,null,null,2.5f,null);
        when(ratingRepository.save(rating)).thenReturn(rating);
        assertEquals(rating, ratingService.saveRating(rating));
    }

    @Test
    public void shouldGetAverageRatingOfARestaurant() {
        Double avgRating = 4.5;
        int restaurantId = 1;
        when(ratingRepository.findAverageRatingByRestaurantId(1)).thenReturn(4.5);
        assertEquals(avgRating, ratingService.getAverageRatingOfARestaurant(restaurantId));
    }

    @Test
    public void shouldGetRatingByCustomer() {

        Customer cus = new Customer(1,null,null,null,null,null,null);
        Restaurant res = new Restaurant(1,null,null,null,null,null,null);

        RatingOfRestaurantByCustomer rating = mock(RatingOfRestaurantByCustomer.class);
        when(rating.getRatingId()).thenReturn(1);
        when(rating.getRating()).thenReturn(4.5f);
        when(rating.getReviewText()).thenReturn("test review text");

        when(ratingRepository.findByRestaurantAndCustomer(any(Restaurant.class),any(Customer.class))).thenReturn(rating);

        RatingOfRestaurantByCustomer foundRating = ratingService.getRatingByRestaurantAndCustomer(res.getRestaurantId(),cus.getCustomerId());

        assertNotNull(foundRating);

        RatingOfRestaurantByCustomer.Customer customer = mock(RatingOfRestaurantByCustomer.Customer.class);
        when(customer.getCustomerId()).thenReturn(1);
        when(customer.getCustomerName()).thenReturn("test name");
        when(customer.getCustomerEmail()).thenReturn("test@gmail.com");

        RatingOfRestaurantByCustomer.Restaurant restaurant = mock(RatingOfRestaurantByCustomer.Restaurant.class);
        when(restaurant.getRestaurantId()).thenReturn(1);
        when(restaurant.getRestaurantName()).thenReturn("test name");
        when(restaurant.getRestaurantAddress()).thenReturn("test address");
    }
}