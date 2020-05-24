import org.junit.Assert;
import org.junit.Test;
import pl.mbierut.clients.CurrencyRestClient;
import pl.mbierut.models.Currency;

import java.io.IOException;

public class CurrencyRestClientTest {

    @Test
    public void clientCorrectlyConnectsAndGrabsData() {
        CurrencyRestClient testClient = new CurrencyRestClient();
        try {
            Assert.assertNotEquals(0.0, testClient.getSellRate(Currency.AUD));
            Assert.assertNotEquals(0.0, testClient.getBuyRate(Currency.AUD));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
