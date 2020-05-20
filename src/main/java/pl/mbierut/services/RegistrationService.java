package pl.mbierut.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.mbierut.exceptions.UserAlreadyExistsException;
import pl.mbierut.models.User;

import java.util.ArrayList;
import java.util.List;

public class RegistrationService {
    private List<User> userList;
    private static Logger logger = LoggerFactory.getLogger(RegistrationService.class);

    public void registerNewUser (String name, String email, String password) throws UserAlreadyExistsException {
        for (User user : this.userList){
            if (email.equals(user.getEmail())){
                logger.error("User already exists");
                throw new UserAlreadyExistsException("Email address in use");
            }
            this.userList.add(new User(name, email, password));
            logger.info("Added a new user: {} at {}", name, email);
        }
    }

    public RegistrationService() {
        this.userList = new ArrayList<>();
    }
}