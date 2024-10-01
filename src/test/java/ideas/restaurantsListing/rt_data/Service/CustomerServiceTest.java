package ideas.restaurantsListing.rt_data.Service;

import ideas.restaurantsListing.rt_data.Entity.Customer;
import ideas.restaurantsListing.rt_data.Exception.customer.CustomerNotFoundException;
import ideas.restaurantsListing.rt_data.Repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository; // Mocked repository

    @InjectMocks
    private CustomerService customerService; // Service under test

    private Customer customer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks

        customer = new Customer();
        customer.setCustomerEmail("test@example.com");
        customer.setCustomerPassword("password123");
        customer.setRole("USER");
    }

    @Test
    public void shouldReturnCustomerWhenEmailExists() {
        // Arrange
        String customerEmail = "test@example.com";
        Customer expectedCustomer = new Customer(1, "Test User", customerEmail, "password", "1234567890", null, null); // Mock customer

        when(customerRepository.findByCustomerEmail(customerEmail)).thenReturn(expectedCustomer);

        // Act
        Customer actualCustomer = customerService.getCustomerByEmail(customerEmail);

        // Assert
        assertNotNull(actualCustomer);
        assertEquals(expectedCustomer.getCustomerId(), actualCustomer.getCustomerId());
        assertEquals(expectedCustomer.getCustomerName(), actualCustomer.getCustomerName());
        assertEquals(expectedCustomer.getCustomerEmail(), actualCustomer.getCustomerEmail());
        verify(customerRepository, times(1)).findByCustomerEmail(customerEmail); // Verify the repository call
    }

    @Test
    public void shouldThrowRuntimeExceptionWhenRetrievingCustomerFails() {
        // Arrange
        String customerEmail = "nonexistent@example.com";
        when(customerRepository.findByCustomerEmail(customerEmail)).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            customerService.getCustomerByEmail(customerEmail);
        });

        assertEquals("Database error", exception.getMessage());
        verify(customerRepository, times(1)).findByCustomerEmail(customerEmail); // Verify the repository call
    }

    @Test
    void shouldGetAllCustomers() {

        List<Customer> customers = List.of(customer); // Mock list of customers
        when(customerRepository.findAll()).thenReturn(customers);

        Iterable<Customer> result = customerService.getallCustomers();

        assertNotNull(result);
        assertEquals(customers, result);
        verify(customerRepository, times(1)).findAll(); // Verify method call
    }

    @Test
    void shouldSaveCustomer() {
        // Arrange
        when(customerRepository.save(customer)).thenReturn(customer);

        // Act
        Customer result = customerService.saveCustomer(customer);

        // Assert
        assertNotNull(result);
        assertEquals(customer, result);
        verify(customerRepository, times(1)).save(customer); // Verify method call
    }

    @Test
    void shouldGetCustomerById_Success() {
        // Arrange
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        // Act
        Optional<Customer> result = customerService.getCustomerById(1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(customer, result.get());
        verify(customerRepository, times(1)).findById(1); // Verify method call
    }

    @Test
    public void shouldThrowRuntimeExceptionWhenGettingAllCustomersFails() {
        // Arrange
        when(customerRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            customerService.getallCustomers();
        });

        assertEquals("Database error", exception.getMessage());
        verify(customerRepository, times(1)).findAll(); // Verify the repository call
    }

    @Test
    public void shouldThrowRuntimeExceptionWhenSavingCustomerFails() {
        // Arrange
        Customer customer = new Customer(1, "Test User", "test@example.com", "password", "1234567890", null, null);
        when(customerRepository.save(customer)).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            customerService.saveCustomer(customer);
        });

        assertEquals("Database error", exception.getMessage());
        verify(customerRepository, times(1)).save(customer); // Verify the repository call
    }

    @Test
    void shouldThrowCustomerNotFoundExceptionWhenCustomerNotFound() {
        // Arrange: Mock customerRepository to return empty for a given id
        int customerId = 1;
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // Act & Assert: Verify that CustomerNotFoundException is thrown
        assertThrows(CustomerNotFoundException.class, () -> {
            customerService.getCustomerById(customerId);
        });
    }

    @Test
    public void shouldThrowRuntimeExceptionWhenGettingCustomerByIdFailsDueToException() {
        // Arrange
        int customerId = 1;
        when(customerRepository.findById(customerId)).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            customerService.getCustomerById(customerId);
        });

        assertEquals("Database error", exception.getMessage());
        verify(customerRepository, times(1)).findById(customerId); // Verify the repository call
    }

}