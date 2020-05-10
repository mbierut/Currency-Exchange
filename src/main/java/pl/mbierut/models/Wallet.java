package pl.mbierut.models;

import pl.mbierut.exceptions.InsufficientFundsException;

import java.util.HashMap;
import java.util.Map;
public class Wallet {
    public Map<Currency, Double> currencies;

    public void fulfillOrder(Order order) throws InsufficientFundsException {
        this.getCurrencies().merge(order.getCurrencyBuy(), order.getAmountToSell()*order.getRate(), Double::sum);
        double curSubtractedFrom = this.getCurrencies().get(order.getCurrencySell());
        this.getCurrencies().put(order.getCurrencySell(), curSubtractedFrom - order.getAmountToSell());
    }

    public void sendMoney(Wallet wallet, Funds funds) throws InsufficientFundsException{
        this.getCurrencies().put(funds.getCurrency(), wallet.getCurrencies().get(funds.getCurrency()) - funds.getAmount());
        wallet.getCurrencies().merge(funds.getCurrency(), funds.getAmount(), Double::sum);
    }

    public Wallet() {
        this.currencies = new HashMap<>();
    }

    public Map<Currency, Double> getCurrencies() {
        return currencies;
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "currencies=" + currencies +
                '}';
    }

}