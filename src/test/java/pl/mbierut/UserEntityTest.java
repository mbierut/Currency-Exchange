package pl.mbierut;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.mbierut.database.entities.UserEntity;
import pl.mbierut.database.repositories.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserEntityTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void userCreatedCorrectly() {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("test.test@test.test");
        userEntity.setPassword("test-test");
        userEntity.setUsername("test");

        userRepository.save(userEntity);

        Assert.assertEquals(userRepository.findByEmail("test.test@test.test").getUsername(), "test");
    }

}
