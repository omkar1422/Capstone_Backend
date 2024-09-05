package ideas.restaurantsListing.rt_data.Repository;

import ideas.restaurantsListing.rt_data.Entity.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {

    public Customer findByCustomerEmail(String customerEmail);
}
