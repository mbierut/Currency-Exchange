package pl.mbierut;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.mbierut.database.entities.WalletEntryEntity;
import pl.mbierut.database.repositories.WalletEntryRepository;
import pl.mbierut.models.Funds;
import pl.mbierut.models.enums.Currency;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WalletEntryEntityTest {

    @Autowired
    private WalletEntryRepository walletEntryRepository;

    @Test
    public void walletEntryAddedCorrectly() {
        WalletEntryEntity entity = new WalletEntryEntity();
        Funds funds = new Funds(Currency.CAD, 1000);
        entity.setFunds(funds);

        walletEntryRepository.save(entity);

        Assert.assertEquals(walletEntryRepository.findById(1L).get().getFunds().getCurrency(), Currency.CAD);

    }

}
