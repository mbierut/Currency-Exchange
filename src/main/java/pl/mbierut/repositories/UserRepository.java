package pl.mbierut.repositories;

import lombok.Getter;
import pl.mbierut.models.User;

import java.util.ArrayList;
import java.util.List;

@Getter
public class UserRepository {
    private List<User> userList;

    public User findUserByEmail(String email) {
        for (User user : userList) {
            if (email.equals(user.getEmail())) {
                return user;
            }
        }
        return null;
    }

    public void addUser(String userName, String email, String password) {
        userList.add(new User(userName, email, password));
    }

    public UserRepository() {
        this.userList = new ArrayList<>();
    }
}
