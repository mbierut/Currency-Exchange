package pl.mbierut;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.mbierut.database.entities.OrderEntity;
import pl.mbierut.database.entities.UserEntity;
import pl.mbierut.database.repositories.UserRepository;
import pl.mbierut.exceptions.InsufficientFundsException;
import pl.mbierut.models.Funds;
import pl.mbierut.models.enums.Currency;

import static pl.mbierut.models.enums.BuyOrSell.buy;
import static pl.mbierut.models.enums.BuyOrSell.sell;

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

    @Test
    public void findCurrencyWorks() {
        UserEntity testUser = new UserEntity("test", "test", "test");
        testUser.addFunds(new Funds(Currency.PLN, 60.0));
        Assert.assertEquals(60.0, testUser.findCurrencyInWallet(Currency.PLN).getFunds().getAmount(), 0.0);
    }

    @Test
    public void sellingCurrencyWorks() throws InsufficientFundsException {
        UserEntity testUser = new UserEntity("test", "test", "test");
        testUser.addFunds(new Funds(Currency.PLN, 60.0));
        Funds soldFunds = new Funds(Currency.PLN, 10.0);
        OrderEntity order = new OrderEntity(soldFunds, Currency.USD, sell);
        testUser.fulfillOrder(order);

        double expectedUSD = 10.0 / Currency.USD.getBuyRate();

        Assert.assertEquals(50.0, testUser.findCurrencyInWallet(order.getFundsToBuyOrSell().getCurrency()).getFunds().getAmount(), 0.0);
        Assert.assertEquals(expectedUSD, testUser.findCurrencyInWallet(order.getOtherCurrencyToSellOrBuy()).getFunds().getAmount(), 0.0);
    }

    @Test
    public void buyingCurrencyWorks() throws InsufficientFundsException {
        UserEntity testUser = new UserEntity("test", "test", "test");
        testUser.addFunds(new Funds(Currency.PLN, 60.0));
        Funds boughtFunds = new Funds(Currency.USD, 10.0);
        OrderEntity order = new OrderEntity(boughtFunds, Currency.PLN, buy);
        testUser.fulfillOrder(order);

        double expectedPLN = 60.0 - 10.0 * Currency.USD.getBuyRate();

        Assert.assertEquals(10.0, testUser.findCurrencyInWallet(order.getFundsToBuyOrSell().getCurrency()).getFunds().getAmount(), 0.0);
        Assert.assertEquals(expectedPLN, testUser.findCurrencyInWallet(order.getOtherCurrencyToSellOrBuy()).getFunds().getAmount(), 0.0);
    }

    @Test
    public void withdrawingFundsWorks() throws InsufficientFundsException{
        UserEntity testUser = new UserEntity("test", "test", "test");
        testUser.addFunds(new Funds(Currency.PLN, 60.0));
        testUser.withdrawFunds(new Funds(Currency.PLN, 45.0));

        Assert.assertEquals(15.0, testUser.findCurrencyInWallet(Currency.PLN).getFunds().getAmount(), 0.0);
    }

    @Test
    public void sendingMoneyWorks() throws InsufficientFundsException {
        UserEntity testUser1 = new UserEntity("test", "test", "test");
        testUser1.addFunds(new Funds(Currency.PLN, 60.0));
        UserEntity testUser2 = new UserEntity("test2", "test2", "test2");

        testUser1.sendMoney(testUser2, new Funds(Currency.PLN, 50.0));

        Assert.assertEquals(50.0, testUser2.findCurrencyInWallet(Currency.PLN).getFunds().getAmount(), 0.0);
        Assert.assertEquals(10.0, testUser1.findCurrencyInWallet(Currency.PLN).getFunds().getAmount(), 0.0);
    }

}
