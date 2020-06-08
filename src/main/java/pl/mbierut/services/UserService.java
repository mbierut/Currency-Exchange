package pl.mbierut.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.mbierut.exceptions.UserAlreadyExistsException;
import pl.mbierut.repositories.UserRepository;
import pl.mbierut.models.requests.UserRegistrationRequest;

public class UserService {
    private UserRepository repository = new UserRepository();
    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    public void registerNewUser(UserRegistrationRequest request) throws UserAlreadyExistsException {
        if (this.repository.findUserByEmail(request.getEmail()) != null) {
            logger.error("User already exists");
            throw new UserAlreadyExistsException("Email address in use");
        }
        this.repository.addUser(request.getUserName(), request.getEmail(), request.getPassword());
        logger.info("Added a new user: {} at {}", request.getUserName(), request.getEmail());
    }

    public String showWallet(String email) {
        if (this.repository.findUserByEmail(email) == null) {
            return "User not found";
        }
        return this.repository.findUserByEmail(email).showWallet();
    }
}