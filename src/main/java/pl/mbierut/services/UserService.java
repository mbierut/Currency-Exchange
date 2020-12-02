package pl.mbierut.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.mbierut.database.entities.UserEntity;
import pl.mbierut.database.repositories.UserRepository;
import pl.mbierut.exceptions.UserAlreadyExistsException;
import pl.mbierut.models.requests.UserRegistrationRequest;

@Service
public class UserService {
    private UserRepository repository;
    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    public void registerNewUser(UserRegistrationRequest request) throws UserAlreadyExistsException {
        if (this.repository.findByEmail(request.getEmail()) != null) {
            logger.error("User already exists");
            throw new UserAlreadyExistsException("Email address in use");
        }
        this.repository.save(new UserEntity(request.getUserName(), request.getEmail(), request.getPassword()));
        logger.info("Added a new user: {} at {}", request.getUserName(), request.getEmail());
    }

    public String showWallet(String email) {
        UserEntity user = this.repository.findByEmail(email);
        if (user == null) {
            return null;
        }
        return user.getWalletEntries().toString();
    }

    public UserEntity getUser(String email) {
        return this.repository.findByEmail(email);
    }

    public UserService(UserRepository repository) {
        this.repository = repository;
    }
}