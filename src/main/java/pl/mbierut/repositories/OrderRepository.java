package pl.mbierut.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.mbierut.entities.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {

}
