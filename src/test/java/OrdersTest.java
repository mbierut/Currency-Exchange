import org.junit.Test;
import pl.mbierut.Currency;
import pl.mbierut.InsufficientFundsException;
import pl.mbierut.Order;
import pl.mbierut.User;

public class OrdersTest {

    @Test
    public void OrderShouldProcessCorrectly(){
        User testUser = new User("Test", "test@test.com", "1");
        testUser.getWallet().getCurrencies().put(Currency.PLN, 100.0);
        testUser.getWallet().getCurrencies().put(Currency.AUD, 10.0);
        testUser.getWallet().getCurrencies().put(Currency.CAD, 35.1);
        testUser.getWallet().getCurrencies().put(Currency.JPY, 995.0);
        testUser.getWallet().getCurrencies().put(Currency.SEK, 3.5);

        Order order1 = new Order(Currency.PLN, Currency.USD, 5.0,15.0);
        try {
            testUser.getWallet().fulfillOrder(order1, testUser.getWallet());
        } catch (InsufficientFundsException exception) {
            exception.printStackTrace();
        }

        System.out.println(testUser.getWallet().getCurrencies().get(Currency.PLN));
        System.out.println(testUser.getWallet().getCurrencies().get(Currency.USD));
        System.out.println(testUser.getWallet());

    }
}
