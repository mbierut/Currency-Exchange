package pl.mbierut;

import org.junit.Assert;
import org.junit.Test;
import pl.mbierut.exceptions.UserAlreadyExistsException;
import pl.mbierut.models.requests.UserRegistrationRequest;
import pl.mbierut.services.UserService;

public class UserTest {

    @Test(expected = UserAlreadyExistsException.class)
    public void userAlreadyExistsExceptionThrownProperly() throws UserAlreadyExistsException {
        UserService service = new UserService();
        String testEmail = "test@test.com";
        UserRegistrationRequest req1 = new UserRegistrationRequest("Test1", testEmail, "1");
        UserRegistrationRequest req2 = new UserRegistrationRequest("Test2", testEmail, "2");

        service.registerNewUser(req1);
        service.registerNewUser(req2);
    }

    @Test
    public void showWalletWorksCorrectly() throws UserAlreadyExistsException {
        UserService service = new UserService();
        String testEmail = "test@test.com";
        UserRegistrationRequest request = new UserRegistrationRequest("Test", testEmail, "1");
        service.registerNewUser(request);
        System.out.println(service.showWallet(testEmail));

        Assert.assertNotNull(service.showWallet(testEmail));
    }
}
