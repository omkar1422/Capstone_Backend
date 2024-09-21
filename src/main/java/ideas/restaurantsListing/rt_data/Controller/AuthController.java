package ideas.restaurantsListing.rt_data.Controller;

import ideas.restaurantsListing.rt_data.Entity.Customer;
import ideas.restaurantsListing.rt_data.Service.CustomerService;
import ideas.restaurantsListing.rt_data.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ideas.restaurantsListing.rt_data.dto.auth.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/restaurantListings/api/customer")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateCustomer(@RequestBody Customer customer) throws Exception {

        AuthResponse authResponse;
        Customer customerDetails;
        try {
            System.out.println("Request arrived");
            customerDetails = customerService.getCustomerByEmail(customer.getCustomerEmail());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    customer.getCustomerEmail(), customer.getCustomerPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = customerService.loadUserByUsername(customer.getCustomerEmail());

        final String jwt = jwtTokenUtil.generateToken(userDetails);
        authResponse = new AuthResponse(
                new Customer(
                        customerDetails.getCustomerId(),
                        customerDetails.getCustomerName(),
                        customerDetails.getCustomerEmail(),
                        customerDetails.getCustomerPassword(),
                        customerDetails.getCustomerPhone(),
                        customerDetails.getRole(),
                        customerDetails.getCustomerAddress()
                ),
                jwt
                );
        return ResponseEntity.ok(authResponse);
    }
}


