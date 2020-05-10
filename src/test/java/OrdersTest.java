import org.junit.Assert;
import org.junit.Test;
import pl.mbierut.exceptions.InsufficientFundsException;
import pl.mbierut.models.Currency;
import pl.mbierut.models.Order;
import pl.mbierut.models.User;

public class OrdersTest {

    @Test
    public void orderShouldProcessCorrectly(){
        User testUser = new User("Test", "test@test.com", "1");
        testUser.getWallet().getCurrencies().put(Currency.PLN, 100.0);
        testUser.getWallet().getCurrencies().put(Currency.AUD, 10.0);
        testUser.getWallet().getCurrencies().put(Currency.CAD, 35.1);
        testUser.getWallet().getCurrencies().put(Currency.JPY, 995.0);
        testUser.getWallet().getCurrencies().put(Currency.SEK, 3.5);

        Order order1 = new Order(Currency.PLN, Currency.USD, 5.0,15.0);
        try {
            testUser.getWallet().fulfillOrder(order1);
            Assert.assertEquals(85.0, testUser.getWallet().getCurrencies().get(Currency.PLN), 0.0);
            Assert.assertEquals(75.0, testUser.getWallet().getCurrencies().get(Currency.USD), 0.0);
        } catch (InsufficientFundsException exception) {
            exception.printStackTrace();
        }
    }

    @Test(expected = InsufficientFundsException.class)
    public void insufficientFundsTestThrownProperly() throws InsufficientFundsException{
        User testUser = new User("Test", "test@test.com", "1");
        testUser.getWallet().getCurrencies().put(Currency.PLN, 100.0);

        Order order2 = new Order(Currency.AUD, Currency.PLN, 2.1, 30);
        testUser.getWallet().fulfillOrder(order2);
    }
}
