package pl.mbierut.models;

import lombok.Getter;
import lombok.ToString;
import org.slf4j.LoggerFactory;
import pl.mbierut.database.entities.OrderEntity;
import pl.mbierut.exceptions.InsufficientFundsException;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import pl.mbierut.models.enums.BuyOrSell;
import pl.mbierut.models.enums.Currency;

@Getter
@ToString
public class Wallet {
    private Map<Currency, Double> currencies;
    private static Logger logger = LoggerFactory.getLogger(Wallet.class);

    public void fulfillOrder(Order order) throws InsufficientFundsException {
        if (order.getBuyOrSell().equals(BuyOrSell.sell)) {
            this.sellCurrency(order);
        } else if (order.getBuyOrSell().equals(BuyOrSell.buy)) {
            this.buyCurrency(order);
        }
    }

    private void sellCurrency(Order order) throws InsufficientFundsException {
        double curSubtractedFrom = this.getCurrencies().get(order.getFundsToBuyOrSell().getCurrency());
        if (curSubtractedFrom - order.getFundsToBuyOrSell().getAmount() < 0) {
            logger.error("Insufficient funds");
            throw new InsufficientFundsException("Insufficient funds");
        }
        this.getCurrencies().merge(order.getOtherCurrencyToSellOrBuy(), order.getFundsValueInOtherCurrency(), Double::sum);
        this.getCurrencies().put(order.getFundsToBuyOrSell().getCurrency(), curSubtractedFrom - order.getFundsToBuyOrSell().getAmount());
        logger.info("{}{} was sold for {}{}", order.getFundsToBuyOrSell().getAmount(), order.getFundsToBuyOrSell().getCurrency(), order.getFundsValueInOtherCurrency(), order.getOtherCurrencyToSellOrBuy());
    }

    private void buyCurrency(Order order) throws InsufficientFundsException {
        double curSubtractedFrom = this.getCurrencies().get(order.getOtherCurrencyToSellOrBuy());
        if (curSubtractedFrom - order.getFundsValueInOtherCurrency() < 0) {
            logger.error("Insufficient funds");
            throw new InsufficientFundsException("Insufficient funds");
        }
        this.getCurrencies().merge(order.getFundsToBuyOrSell().getCurrency(), order.getFundsToBuyOrSell().getAmount(), Double::sum);
        this.getCurrencies().put(order.getOtherCurrencyToSellOrBuy(), curSubtractedFrom - order.getFundsValueInOtherCurrency());
        logger.info("{}{} was bought for {}{}", order.getFundsToBuyOrSell().getAmount(), order.getFundsToBuyOrSell().getCurrency(), order.getFundsValueInOtherCurrency(), order.getOtherCurrencyToSellOrBuy());
    }

    public void sendMoney(Wallet wallet, Funds funds) throws InsufficientFundsException {
        if (wallet.getCurrencies().get(funds.getCurrency()) - funds.getAmount() < 0) {
            logger.error("Insufficient funds");
            throw new InsufficientFundsException("Insufficient funds");
        }
        this.getCurrencies().put(funds.getCurrency(), wallet.getCurrencies().get(funds.getCurrency()) - funds.getAmount());
        wallet.getCurrencies().merge(funds.getCurrency(), funds.getAmount(), Double::sum);
        logger.info("Sent {}{}", funds.getAmount(), funds.getCurrency());
    }

    public void addFunds(Funds funds) {
        this.getCurrencies().merge(funds.getCurrency(), funds.getAmount(), Double::sum);
        logger.info("Added {}{} to the wallet", funds.getAmount(), funds.getCurrency());
    }

    public void withdrawFunds(Funds funds) throws InsufficientFundsException {
        if (this.getCurrencies().get(funds.getCurrency()) - funds.getAmount() < 0) {
            logger.error("Insufficient funds");
            throw new InsufficientFundsException("Insufficient funds");
        }
        this.getCurrencies().put(funds.getCurrency(), this.getCurrencies().get(funds.getCurrency()) - funds.getAmount());
        logger.info("Deposited {}{}", funds.getAmount(), funds.getCurrency());
    }

    Wallet() {
        this.currencies = new HashMap<>();
        for (Currency currency : Currency.values()) {
            this.currencies.put(currency, 0.0);
        }
    }

}