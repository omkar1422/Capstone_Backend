package ideas.restaurantsListing.rt_data.Controller;

import ideas.restaurantsListing.rt_data.Entity.Customer; // Adjust the package based on your structure
import ideas.restaurantsListing.rt_data.Exception.customer.EmailAlreadyRegisteredException;
import ideas.restaurantsListing.rt_data.Repository.CustomerRepository;
import ideas.restaurantsListing.rt_data.Roles.Roles;
import ideas.restaurantsListing.rt_data.Service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldGetAllCustomers_Success() {
        // Arrange
        Iterable<Customer> customers = mock(Iterable.class);
        when(customerService.getallCustomers()).thenReturn(customers);

        // Act
        Iterable<Customer> response = customerController.getAllCustomers();

        // Assert
        assertNotNull(response);
        assertEquals(customers, response);
    }

    @Test
    public void shouldGetAllCustomers_Failure() {
        when(customerService.getallCustomers()).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> customerController.getAllCustomers());
    }

    @Test
    public void shouldRegisterAdmin_Success() {
        // Arrange
        Customer admin = new Customer();
        admin.setCustomerEmail("admin@example.com");
        admin.setCustomerPassword("password");

        when(customerRepository.existsByCustomerEmail(admin.getCustomerEmail())).thenReturn(false);
        when(passwordEncoder.encode(admin.getCustomerPassword())).thenReturn("hashedPassword");
        when(customerService.saveCustomer(any(Customer.class))).thenReturn(admin);

        Customer response = customerController.registerAdmin(admin);

        assertNotNull(response);
        assertEquals("admin@example.com", response.getCustomerEmail());
        assertEquals("hashedPassword", response.getCustomerPassword());
        assertEquals(Roles.ROLE_ADMIN, response.getRole());
    }

    @Test
    public void shouldRegisterAdmin_EmailAlreadyRegistered() {
        // Arrange
        Customer admin = new Customer();
        admin.setCustomerEmail("admin@example.com");
        admin.setCustomerPassword("password");

        when(customerRepository.existsByCustomerEmail(admin.getCustomerEmail())).thenReturn(true);

        // Act & Assert
        EmailAlreadyRegisteredException exception = assertThrows(EmailAlreadyRegisteredException.class, () ->
                customerController.registerAdmin(admin)
        );
        assertEquals("Email is already registered", exception.getMessage());
    }


    @Test
    public void shouldSaveCustomer_Success() {
        // Arrange
        Customer customer = new Customer();
        customer.setCustomerEmail("customer@example.com");
        customer.setCustomerPassword("password");

        when(passwordEncoder.encode(customer.getCustomerPassword())).thenReturn("hashedPassword");
        when(customerService.saveCustomer(any(Customer.class))).thenReturn(customer);

        // Act
        ResponseEntity<Customer> response = customerController.saveCustomer(customer);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("hashedPassword", response.getBody().getCustomerPassword());
    }

    @Test
    public void shouldSaveCustomer_Failure() {
        // Arrange
        Customer customer = new Customer();
        customer.setCustomerEmail("customer@example.com");
        customer.setCustomerPassword("password");

        when(passwordEncoder.encode(customer.getCustomerPassword())).thenReturn("hashedPassword");
        when(customerService.saveCustomer(any(Customer.class))).thenThrow(new RuntimeException());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> customerController.saveCustomer(customer));
    }

    @Test
    public void shouldGetCustomerById_Success() {
        // Arrange
        Optional<Customer> customer = Optional.of(new Customer());
        when(customerService.getCustomerById(1)).thenReturn(customer);

        // Act
        ResponseEntity<Optional<Customer>> response = customerController.getCustomerById(1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customer, response.getBody());
    }

    @Test
    public void shouldGetCustomerById_Failure() {
        // Arrange
        when(customerService.getCustomerById(1)).thenThrow(new RuntimeException());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> customerController.getCustomerById(1));
    }
}
