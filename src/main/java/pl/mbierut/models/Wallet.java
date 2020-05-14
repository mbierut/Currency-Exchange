package pl.mbierut.models;

import lombok.Getter;
import pl.mbierut.exceptions.InsufficientFundsException;
import java.util.HashMap;
import java.util.Map;

@Getter
public class Wallet {
    private Map<Currency, Double> currencies;

    public void fulfillOrder(Order order) throws InsufficientFundsException {
        double curSubtractedFrom = this.getCurrencies().get(order.getFundsToSell().getCurrency());
        if (curSubtractedFrom - order.getFundsToSell().getAmount() < 0){
            throw new InsufficientFundsException("Insufficient funds");
        }
        this.getCurrencies().merge(order.getCurrencyBuy(),
                order.getFundsToSell().getAmount()*order.getFundsToSell().getCurrency().getRate()/order.getCurrencyBuy().getRate(), Double::sum);
        this.getCurrencies().put(order.getFundsToSell().getCurrency(), curSubtractedFrom - order.getFundsToSell().getAmount());
    }

    public void sendMoney(Wallet wallet, Funds funds) throws InsufficientFundsException{
        if (wallet.getCurrencies().get(funds.getCurrency()) - funds.getAmount() < 0){
            throw new InsufficientFundsException("Insufficient funds");
        }
        this.getCurrencies().put(funds.getCurrency(), wallet.getCurrencies().get(funds.getCurrency()) - funds.getAmount());
        wallet.getCurrencies().merge(funds.getCurrency(), funds.getAmount(), Double::sum);
    }

    public Wallet() {
        this.currencies = new HashMap<>();
        for (Currency currency : Currency.values()){
            this.currencies.put(currency, 0.0);
        }
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "currencies=" + currencies +
                '}';
    }

}