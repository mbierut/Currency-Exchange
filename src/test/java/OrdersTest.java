import org.junit.Assert;
import org.junit.Test;
import pl.mbierut.exceptions.InsufficientFundsException;
import pl.mbierut.models.Currency;
import pl.mbierut.models.Funds;
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

        Funds funds1 = new Funds(Currency.PLN, 15.0);
        Order order1 = new Order(funds1, Currency.USD, 5.0);
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

        Funds funds2 = new Funds(Currency.AUD, 30.0);
        Order order2 = new Order(funds2, Currency.PLN, 2.1);
        testUser.getWallet().fulfillOrder(order2);
    }

    @Test
    public void sendFundsSendsFundsCorrectly(){
        User user1 = new User("test1", "test@test.com", "1");
        user1.getWallet().getCurrencies().put(Currency.PLN, 10.0);

        User user2 = new User("test2", "test@test.com", "1");
        user2.getWallet().getCurrencies().put(Currency.PLN, 10.0);

        Funds funds = new Funds(Currency.PLN, 9.0);
        try {
            user1.getWallet().sendMoney(user2.getWallet(), funds);
        } catch (InsufficientFundsException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(1.0, user1.getWallet().getCurrencies().get(Currency.PLN), 0.0);
        Assert.assertEquals(19.0, user2.getWallet().getCurrencies().get(Currency.PLN), 0.0);


    }

}
