package ideas.restaurantsListing.rt_data.Service;

import ideas.restaurantsListing.rt_data.Entity.Customer;
import ideas.restaurantsListing.rt_data.Exception.customer.CustomerNotFoundException;
import ideas.restaurantsListing.rt_data.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Service
public class CustomerService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer getCustomerByEmail(String customerEmail) {
        try {
            return customerRepository.findByCustomerEmail(customerEmail);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Iterable<Customer> getallCustomers() {
        try {
            return customerRepository.findAll();
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Customer saveCustomer(Customer customer) {
        try {
            return customerRepository.save(customer);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Optional<Customer> getCustomerById(@PathVariable int id) {
        try {
            Optional<Customer> customer = customerRepository.findById(id);
            if (customer.isEmpty())
                throw new CustomerNotFoundException("Customer not found with id " + id);
            return customer;
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        try {
            Customer customer = customerRepository.findByCustomerEmail(email);
            if (customer == null) {
                throw new UsernameNotFoundException("Customer not found");
            }

            return org.springframework.security.core.userdetails.User.builder()
                    .username(customer.getCustomerEmail())
                    .password(customer.getCustomerPassword())
                    .roles(customer.getRole())
                    .build();
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
