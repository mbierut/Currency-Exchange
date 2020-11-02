package pl.mbierut.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.mbierut.exceptions.UserAlreadyExistsException;
import pl.mbierut.models.Wallet;
import pl.mbierut.repository.UserRepositoryOld;
import pl.mbierut.models.requests.UserRegistrationRequest;

@Service
public class UserService {
    private UserRepositoryOld repository;
    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService() {
        this.repository = new UserRepositoryOld();
    }

    public void registerNewUser(UserRegistrationRequest request) throws UserAlreadyExistsException {
        if (this.repository.findUserByEmail(request.getEmail()) != null) {
            logger.error("User already exists");
            throw new UserAlreadyExistsException("Email address in use");
        }
        this.repository.addUser(request.getUserName(), request.getEmail(), request.getPassword());
        logger.info("Added a new user: {} at {}", request.getUserName(), request.getEmail());
    }

    public Wallet showWallet(String email) {
        if (this.repository.findUserByEmail(email) == null) {
            return null;
        }
        return this.repository.findUserByEmail(email).getWallet();
    }
}