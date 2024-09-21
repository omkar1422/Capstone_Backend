package ideas.restaurantsListing.rt_data.Controller;

import ideas.restaurantsListing.rt_data.Entity.Customer;
import ideas.restaurantsListing.rt_data.Exception.customer.EmailAlreadyRegisteredException;
import ideas.restaurantsListing.rt_data.Repository.CustomerRepository;
import ideas.restaurantsListing.rt_data.Roles.Roles;
import ideas.restaurantsListing.rt_data.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(value = "/restaurantListings/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getall")
    public Iterable<Customer> getAllCustomers() {
        return customerService.getallCustomers();
    }

    @PostMapping("/authAdmin/register")
    public Customer registerAdmin(@RequestBody Customer admin) {
        if (customerRepository.existsByCustomerEmail(admin.getCustomerEmail())) {
            throw new EmailAlreadyRegisteredException("Email is already registered");
        }
        System.out.println("CREATING USER");
        // Hash the password
        admin.setCustomerPassword(passwordEncoder.encode(admin.getCustomerPassword()));

        // Explicitly set the role as ADMIN
        admin.setRole(Roles.ROLE_ADMIN);

        return customerService.saveCustomer(admin);
    }

    @PostMapping("/register")
    public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer) {
        customer.setCustomerPassword(passwordEncoder.encode(customer.getCustomerPassword()));

        if (customer.getRole() == null || customer.getRole().isEmpty()) {
            customer.setRole(Roles.ROLE_CUSTOMER);
        }

        Customer savedCustomer = customerService.saveCustomer(customer);
        return ResponseEntity.ok(savedCustomer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Customer>> getCustomerById(@PathVariable("id") int id) {
        Optional<Customer> customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }
}
