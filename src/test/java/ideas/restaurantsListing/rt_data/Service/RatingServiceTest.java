package ideas.restaurantsListing.rt_data.Service;

import ideas.restaurantsListing.rt_data.Entity.Customer;
import ideas.restaurantsListing.rt_data.Entity.Rating;
import ideas.restaurantsListing.rt_data.Entity.Restaurant;
import ideas.restaurantsListing.rt_data.Repository.RatingRepository;
import ideas.restaurantsListing.rt_data.dto.rating.RatingByRestaurat;
import ideas.restaurantsListing.rt_data.dto.rating.RatingOfRestaurantByCustomer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        Restaurant res = new Restaurant(1,null,null,null,null,null,null,null);

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

    @Test
    public void shouldGetRatingsByRestaurant_Success() {
        // Arrange
        int restaurantId = 1;

        // Create mock DTOs
        RatingByRestaurat mockRating = mock(RatingByRestaurat.class);
        RatingByRestaurat.Customer mockCustomer = mock(RatingByRestaurat.Customer.class);

        when(mockRating.getRatingId()).thenReturn(101);
        when(mockRating.getRating()).thenReturn(4.5f);
        when(mockRating.getReviewText()).thenReturn("Great food!");

        when(mockRating.getCustomer()).thenReturn(mockCustomer);
        when(mockCustomer.getCustomerId()).thenReturn(202);
        when(mockCustomer.getCustomerName()).thenReturn("John Doe");
        when(mockCustomer.getCustomerEmail()).thenReturn("john.doe@example.com");

        // Mock repository to return a list of ratings
        List<RatingByRestaurat> mockRatingList = new ArrayList<>();
        mockRatingList.add(mockRating);
        when(ratingRepository.findByRestaurant(any(Restaurant.class))).thenReturn(mockRatingList);

        // Act
        List<RatingByRestaurat> ratings = ratingService.getRatingsByRestaurant(restaurantId);

        // Assert
        assertNotNull(ratings, "Ratings list should not be null");
        assertEquals(1, ratings.size(), "Expected one rating in the list");
        assertEquals(101, ratings.get(0).getRatingId());
        assertEquals(4.5f, ratings.get(0).getRating());
        assertEquals("Great food!", ratings.get(0).getReviewText());
        assertEquals(202, ratings.get(0).getCustomer().getCustomerId());
        assertEquals("John Doe", ratings.get(0).getCustomer().getCustomerName());
        assertEquals("john.doe@example.com", ratings.get(0).getCustomer().getCustomerEmail());

        verify(ratingRepository, times(1)).findByRestaurant(any(Restaurant.class));
    }

    @Test
    public void shouldGetRatingsByRestaurant_Exception() {
        // Arrange
        int restaurantId = 1;
        when(ratingRepository.findByRestaurant(any(Restaurant.class))).thenThrow(new RuntimeException("Service error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ratingService.getRatingsByRestaurant(restaurantId);
        });

        // Verify the exception message
        assertEquals("Service error", exception.getMessage());
        verify(ratingRepository, times(1)).findByRestaurant(any(Restaurant.class));
    }

    @Test
    public void testGetRatingByRestaurantAndCustomer_Exception() {
        // Arrange
        int restaurantId = 1;
        int customerId = 1;

        // Mocking the repository to throw an exception when the method is called
        when(ratingRepository.findByRestaurantAndCustomer(any(Restaurant.class), any(Customer.class)))
                .thenThrow(new RuntimeException("Service error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ratingService.getRatingByRestaurantAndCustomer(restaurantId, customerId);
        });

        // Verify the exception message
        assertEquals("Service error", exception.getMessage());
    }

}