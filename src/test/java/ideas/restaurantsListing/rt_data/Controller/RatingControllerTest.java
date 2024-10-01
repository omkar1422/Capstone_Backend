package ideas.restaurantsListing.rt_data.Controller;

import ideas.restaurantsListing.rt_data.Entity.Customer;
import ideas.restaurantsListing.rt_data.Entity.Rating;
import ideas.restaurantsListing.rt_data.Entity.Restaurant;
import ideas.restaurantsListing.rt_data.Exception.rating.RatingAlreadySubmitted;
import ideas.restaurantsListing.rt_data.Service.RatingService;
import ideas.restaurantsListing.rt_data.dto.rating.RatingByRestaurat;
import ideas.restaurantsListing.rt_data.dto.rating.RatingOfRestaurantByCustomer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RatingControllerTest {

    @Mock
    private RatingService ratingService;

    @InjectMocks
    private RatingController ratingController;

    @Mock
    private RatingOfRestaurantByCustomer ratingOfRestaurantByCustomer;

    @Mock
    private RatingOfRestaurantByCustomer.Customer customer;

    @Mock
    private RatingOfRestaurantByCustomer.Restaurant restaurant;

    private Rating rating;
    private Restaurant restaurant1;
    private Customer customer1;

    private RatingOfRestaurantByCustomer ratingDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        when(customer.getCustomerId()).thenReturn(1);
        when(customer.getCustomerName()).thenReturn("John Doe");
        when(customer.getCustomerEmail()).thenReturn("john@example.com");

        when(restaurant.getRestaurantId()).thenReturn(1);
        when(restaurant.getRestaurantName()).thenReturn("Test Restaurant");
        when(restaurant.getRestaurantAddress()).thenReturn("123 Main St");

        when(ratingOfRestaurantByCustomer.getCustomer()).thenReturn(customer);
        when(ratingOfRestaurantByCustomer.getRestaurant()).thenReturn(restaurant);
        when(ratingOfRestaurantByCustomer.getRatingId()).thenReturn(1);
        when(ratingOfRestaurantByCustomer.getRating()).thenReturn(4.5f);
        when(ratingOfRestaurantByCustomer.getReviewText()).thenReturn("Great restaurant");

        restaurant1 = new Restaurant();
        restaurant1.setRestaurantId(1); // Set an ID for the restaurant

        // Set up a sample Customer object
        customer1 = new Customer();
        customer1.setCustomerId(1); // Set an ID for the customer

        ratingDTO = mock(RatingOfRestaurantByCustomer.class);
        when(ratingDTO.getRestaurant()).thenReturn(restaurant);
        when(ratingDTO.getCustomer()).thenReturn(customer);

        // Set up the Rating object
        rating = new Rating();
        rating.setRestaurant(restaurant1); // Assign the restaurant to the rating
        rating.setCustomer(customer1); // Assign the customer to the rating
    }

    @Test
    public void shouldSaveListOfRatings_Success() {

        Rating rating1 = new Rating();
        Rating rating2 = new Rating();
        List<Rating> ratings = Arrays.asList(rating1, rating2);

        when(ratingService.saveListOfRatings(ratings)).thenReturn(ratings);

        List<Rating> response = ratingController.saveListOfRatings(ratings);

        assertNotNull(response);
        assertEquals(ratings, response);
    }

    @Test
    public void shouldSaveListOfRatings_ExceptionThrown() {
        List<Rating> ratings = Arrays.asList(
                new Rating(1, null, null, 4.5f, "test review"),
                new Rating(1, null, null, 4.5f, "test review")
        );

        doThrow(new RuntimeException("Service layer exception"))
                .when(ratingService).saveListOfRatings(ratings);

        assertThrows(RuntimeException.class, () -> {
            ratingController.saveListOfRatings(ratings);
        });
    }

    @Test
    public void shouldGetRatingByRestaurantAndCustomer_Success() {

        when(ratingService.getRatingByRestaurantAndCustomer(1, 1)).thenReturn(ratingOfRestaurantByCustomer);

        RatingOfRestaurantByCustomer result = ratingController.getRatingByRestaurantAndCustomer(1, 1);

        assertNotNull(result);
        assertEquals(1, result.getRatingId());
        assertEquals(4.5f, result.getRating());
        assertEquals("Great restaurant", result.getReviewText());
        assertEquals(1, result.getCustomer().getCustomerId());
        assertEquals("John Doe", result.getCustomer().getCustomerName());
        assertEquals("john@example.com", result.getCustomer().getCustomerEmail());
        assertEquals(1, result.getRestaurant().getRestaurantId());
        assertEquals("Test Restaurant", result.getRestaurant().getRestaurantName());
        assertEquals("123 Main St", result.getRestaurant().getRestaurantAddress());

        verify(ratingService, times(1)).getRatingByRestaurantAndCustomer(1, 1);
    }

    @Test
    public void shouldGetRatingByRestaurantAndCustomer_Exception() {

        when(ratingService.getRatingByRestaurantAndCustomer(1, 1)).thenThrow(new RuntimeException("Service exception"));

        assertThrows(RuntimeException.class, () -> {
            ratingController.getRatingByRestaurantAndCustomer(1, 1);
        });

        verify(ratingService, times(1)).getRatingByRestaurantAndCustomer(1, 1);
    }

    @Test
    public void shouldGetAverageRatingByRestaurant_Success() {
        // Arrange
        int restaurantId = 1;
        Double expectedAverageRating = 4.2;
        when(ratingService.getAverageRatingOfARestaurant(restaurantId)).thenReturn(expectedAverageRating);

        // Act
        Double actualAverageRating = ratingController.getAverageRatingByRestaurant(restaurantId);

        // Assert
        assertNotNull(actualAverageRating);
        assertEquals(expectedAverageRating, actualAverageRating);
        verify(ratingService, times(1)).getAverageRatingOfARestaurant(restaurantId);
    }

    @Test
    public void shouldGetAverageRatingByRestaurant_Exception() {
        // Arrange
        int restaurantId = 1;
        when(ratingService.getAverageRatingOfARestaurant(restaurantId)).thenThrow(new RuntimeException("Service error"));

        // Act & Assert: Check if RuntimeException is thrown
        assertThrows(RuntimeException.class, () -> {
            ratingController.getAverageRatingByRestaurant(restaurantId);
        });

        verify(ratingService, times(1)).getAverageRatingOfARestaurant(restaurantId);
    }

    @Test
    public void shouldGetAllRatings_Success() {
        // Arrange
        int restaurantId = 1;
        RatingByRestaurat mockRating1 = mock(RatingByRestaurat.class);
        RatingByRestaurat mockRating2 = mock(RatingByRestaurat.class);

        when(mockRating1.getRatingId()).thenReturn(1);
        when(mockRating1.getRating()).thenReturn(4.5f);
        when(mockRating1.getReviewText()).thenReturn("Great food!");

        RatingByRestaurat.Customer mockCustomer1 = mock(RatingByRestaurat.Customer.class);
        when(mockCustomer1.getCustomerId()).thenReturn(101);
        when(mockCustomer1.getCustomerName()).thenReturn("John Doe");
        when(mockRating1.getCustomer()).thenReturn(mockCustomer1);

        when(mockRating2.getRatingId()).thenReturn(2);
        when(mockRating2.getRating()).thenReturn(3.8f);
        when(mockRating2.getReviewText()).thenReturn("Nice ambiance.");

        RatingByRestaurat.Customer mockCustomer2 = mock(RatingByRestaurat.Customer.class);
        when(mockCustomer2.getCustomerId()).thenReturn(102);
        when(mockCustomer2.getCustomerName()).thenReturn("Jane Doe");
        when(mockRating2.getCustomer()).thenReturn(mockCustomer2);

        List<RatingByRestaurat> mockRatings = Arrays.asList(mockRating1, mockRating2);
        when(ratingService.getRatingsByRestaurant(restaurantId)).thenReturn(mockRatings);

        // Act
        List<RatingByRestaurat> actualRatings = ratingController.getAllRatings(restaurantId);

        // Assert
        assertNotNull(actualRatings);
        assertEquals(2, actualRatings.size());
        assertEquals(1, actualRatings.get(0).getRatingId());
        assertEquals(4.5f, actualRatings.get(0).getRating());
        assertEquals("John Doe", actualRatings.get(0).getCustomer().getCustomerName());
        assertEquals(2, actualRatings.get(1).getRatingId());
        assertEquals(3.8f, actualRatings.get(1).getRating());
        assertEquals("Jane Doe", actualRatings.get(1).getCustomer().getCustomerName());

        verify(ratingService, times(1)).getRatingsByRestaurant(restaurantId);
    }

    @Test
    public void testGetAllRatings_Exception() {
        // Arrange
        int restaurantId = 1;
        when(ratingService.getRatingsByRestaurant(restaurantId)).thenThrow(new RuntimeException("Service error"));

        // Act & Assert: Check if RuntimeException is thrown
        assertThrows(RuntimeException.class, () -> {
            ratingController.getAllRatings(restaurantId);
        });

        verify(ratingService, times(1)).getRatingsByRestaurant(restaurantId);
    }

    @Test
    void shouldSaveRating_WhenRatingIsNew() {
        // Arrange
        when(ratingService.getRatingByRestaurantAndCustomer(rating.getRestaurant().getRestaurantId(), rating.getCustomer().getCustomerId()))
                .thenReturn(null); // Simulating that no rating exists
        when(ratingService.saveRating(rating)).thenReturn(rating); // Simulating successful save

        // Act
        Rating savedRating = ratingController.saveRating(rating);

        // Assert
        assertNotNull(savedRating);
        verify(ratingService).saveRating(rating);
    }

    @Test
    void shouldThrowRatingAlreadySubmitted_WhenRatingExists() {
        // Arrange: Simulate that the rating already exists
        when(ratingService.getRatingByRestaurantAndCustomer(restaurant.getRestaurantId(), customer.getCustomerId()))
                .thenReturn(ratingDTO); // Existing rating found as DTO

        // Act & Assert: Expect RatingAlreadySubmitted exception to be thrown
        RatingAlreadySubmitted exception = assertThrows(RatingAlreadySubmitted.class, () -> {
            ratingController.saveRating(rating);
        });

        // Assert that the exception message is as expected
        assertEquals("rating for the restaurant with id " + restaurant.getRestaurantId() +
                "already exists by customer with id " + customer.getCustomerId(), exception.getMessage());

        // Verify that saveRating() was never called because the rating already exists
        verify(ratingService, never()).saveRating(any(Rating.class));
    }

    @Test
    void shouldThrowRuntimeException_OnServiceError() {
        // Arrange
        when(ratingService.getRatingByRestaurantAndCustomer(rating.getRestaurant().getRestaurantId(), rating.getCustomer().getCustomerId()))
                .thenThrow(new RuntimeException("Service Error")); // Simulating a service error

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                ratingController.saveRating(rating)
        );
        assertEquals("Service Error", exception.getMessage());
    }

}