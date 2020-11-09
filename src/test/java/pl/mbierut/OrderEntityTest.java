package pl.mbierut;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.mbierut.database.entities.OrderEntity;
import pl.mbierut.database.repositories.OrderRepository;
import pl.mbierut.models.Funds;
import pl.mbierut.models.enums.BuyOrSell;
import pl.mbierut.models.enums.Currency;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderEntityTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void orderShouldProcessCorrectly() {
        OrderEntity orderEntity = new OrderEntity();
        Funds funds = new Funds(Currency.CAD, 1000);
        orderEntity.setFundsToBuyOrSell(funds);
        orderEntity.setOtherCurrencyToSellOrBuy(Currency.PLN);
        orderEntity.setBuyOrSell(BuyOrSell.buy);

        orderRepository.save(orderEntity);

        Assert.assertEquals(orderRepository.findById(1L).get().getFundsToBuyOrSell().getCurrency(), Currency.CAD);

    }

}

