package ideas.restaurantsListing.rt_data.Controller;

import ideas.restaurantsListing.rt_data.Controller.AuthController;
import ideas.restaurantsListing.rt_data.Entity.Customer;
import ideas.restaurantsListing.rt_data.Service.CustomerService;
import ideas.restaurantsListing.rt_data.Util.JwtUtil;
import ideas.restaurantsListing.rt_data.dto.auth.AuthResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private CustomerService customerService;

    @Mock
    private JwtUtil jwtTokenUtil;

    @Mock
    private UserDetails userDetails;

    private Customer customer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        customer = new Customer();
        customer.setCustomerEmail("test@example.com");
        customer.setCustomerPassword("password");
    }

    @Test
    public void authenticateCustomer_ShouldReturnAuthResponse_WhenSuccessful() throws Exception {
        // Arrange
        Customer customerDetails = new Customer();
        customerDetails.setCustomerId(1);
        customerDetails.setCustomerName("Test User");
        customerDetails.setCustomerEmail("test@example.com");
        customerDetails.setCustomerPhone("1234567890");
        customerDetails.setRole("ROLE_CUSTOMER");
        customerDetails.setCustomerAddress("123 Test St");

        when(customerService.getCustomerByEmail(customer.getCustomerEmail())).thenReturn(customerDetails);

        // Instead of doNothing(), just mock the authentication method
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken(customer.getCustomerEmail(), customer.getCustomerPassword()));

        when(customerService.loadUserByUsername(customer.getCustomerEmail())).thenReturn(userDetails);
        when(jwtTokenUtil.generateToken(userDetails)).thenReturn("jwt-token");

        // Act
        ResponseEntity<?> response = authController.authenticateCustomer(customer);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        AuthResponse authResponse = (AuthResponse) response.getBody();
        assertEquals(customerDetails.getCustomerId(), authResponse.getCustomer().getCustomerId());
        assertEquals("jwt-token", authResponse.getJwt());
    }


    @Test
    public void authenticateCustomer_ShouldThrowException_WhenBadCredentials() {
        // Arrange
        Customer customerDetails = new Customer();
        customerDetails.setCustomerEmail("test@example.com");
        customerDetails.setCustomerPassword("password"); // Correct password in the DB for the test

        when(customerService.getCustomerByEmail(customer.getCustomerEmail())).thenReturn(customerDetails);
        doThrow(new BadCredentialsException("Incorrect username or password"))
                .when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> {
            authController.authenticateCustomer(customer);
        });

        assertEquals("Incorrect username or password", exception.getMessage());
    }


    @Test
    public void authenticateCustomer_ShouldThrowException_WhenCustomerNotFound() {
        // Arrange
        when(customerService.getCustomerByEmail(customer.getCustomerEmail())).thenThrow(new RuntimeException("Customer not found"));

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> {
            authController.authenticateCustomer(customer);
        });

        assertEquals("Customer not found", exception.getMessage());
    }
}
