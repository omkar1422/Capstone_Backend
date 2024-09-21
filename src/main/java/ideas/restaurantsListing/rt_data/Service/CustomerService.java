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
        return customerRepository.findByCustomerEmail(customerEmail);
    }

    public Iterable<Customer> getallCustomers() {
        return customerRepository.findAll();
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Optional<Customer> getCustomerById(@PathVariable int id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty())
            throw new CustomerNotFoundException("Customer not found with id " + id);
        return customer;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Customer customer = customerRepository.findByCustomerEmail(email);
        if (customer == null) {
            throw new UsernameNotFoundException("Customer not found");
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(customer.getCustomerEmail())
                .password(customer.getCustomerPassword())
                .roles(customer.getRole())
                .build();
    }
}
