package de.flashheart.rlgserver.backend.data.repositories;

import java.util.List;

import de.flashheart.rlgserver.backend.data.entity.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findByLastName(String lastName);
}