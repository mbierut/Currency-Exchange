package pl.mbierut.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.mbierut.entities.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
}
