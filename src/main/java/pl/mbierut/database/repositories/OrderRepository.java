package pl.mbierut.database.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.mbierut.database.entities.OrderEntity;

@Repository
public interface OrderRepository extends CrudRepository<OrderEntity, Long> {

}
