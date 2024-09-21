package ideas.restaurantsListing.rt_data.dto.auth;

import ideas.restaurantsListing.rt_data.Entity.Customer;

public class AuthResponse {
    Customer customer;
    private String jwt;

    public AuthResponse() {

    }

    public AuthResponse(Customer customer, String jwt) {
        this.customer = customer;
        this.jwt = jwt;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
