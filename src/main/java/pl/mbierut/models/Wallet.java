package pl.mbierut.models;

import lombok.Getter;
import lombok.ToString;
import org.slf4j.LoggerFactory;
import pl.mbierut.exceptions.InsufficientFundsException;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;

@Getter
@ToString
public class Wallet {
    private Map<Currency, Double> currencies;
    private static Logger logger = LoggerFactory.getLogger(Wallet.class);

    public void fulfillOrder(Order order) throws InsufficientFundsException {
        double curSubtractedFrom = this.getCurrencies().get(order.getFundsToSell().getCurrency());
        if (curSubtractedFrom - order.getFundsToSell().getAmount() < 0){
            logger.error("Insufficient funds");
            throw new InsufficientFundsException("Insufficient funds");
        }
        this.getCurrencies().merge(order.getCurrencyBuy(), order.getFundsValue(), Double::sum);
        this.getCurrencies().put(order.getFundsToSell().getCurrency(), curSubtractedFrom - order.getFundsToSell().getAmount());
        logger.info("{}{} was sold for {}{}", order.getFundsToSell().getAmount(), order.getFundsToSell().getCurrency(), order.getFundsValue() , order.getCurrencyBuy());
    }

    public void sendMoney(Wallet wallet, Funds funds) throws InsufficientFundsException{
        if (wallet.getCurrencies().get(funds.getCurrency()) - funds.getAmount() < 0){
            logger.error("Insufficient funds");
            throw new InsufficientFundsException("Insufficient funds");
        }
        this.getCurrencies().put(funds.getCurrency(), wallet.getCurrencies().get(funds.getCurrency()) - funds.getAmount());
        wallet.getCurrencies().merge(funds.getCurrency(), funds.getAmount(), Double::sum);
        logger.info("Sent {}{}", funds.getAmount(), funds.getCurrency());
    }

    public void addFunds(Funds funds){
        this.getCurrencies().merge(funds.getCurrency(), funds.getAmount(), Double::sum);
        logger.info("Added {}{} to the wallet", funds.getAmount(), funds.getCurrency());
    }

    public void withdrawFunds(Funds funds) throws InsufficientFundsException{
        if (this.getCurrencies().get(funds.getCurrency()) - funds.getAmount() < 0){
            logger.error("Insufficient funds");
            throw new InsufficientFundsException("Insufficient funds");
        }
        this.getCurrencies().put(funds.getCurrency(), this.getCurrencies().get(funds.getCurrency()) - funds.getAmount());
        logger.info("Deposited {}{}", funds.getAmount(), funds.getCurrency());
    }

    public Wallet() {
        this.currencies = new HashMap<>();
        for (Currency currency : Currency.values()){
            this.currencies.put(currency, 0.0);
        }
    }

}