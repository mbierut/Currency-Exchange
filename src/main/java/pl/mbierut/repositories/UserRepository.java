package pl.mbierut.repositories;

import lombok.Getter;
import pl.mbierut.models.User;

import java.util.HashMap;
import java.util.Map;

@Getter
public class UserRepository {
    private Map<String, User> userList;

    public User findUserByEmail(String email) {
        return this.userList.get(email);
    }

    public void addUser(String userName, String email, String password) {
        this.userList.put(email, new User(userName, email, password));
    }

    public UserRepository() {
        this.userList = new HashMap<>();
    }
}
