package pl.mbierut;

import java.util.HashMap;
import java.util.Map;
public class Wallet {
    public Map<Currency, Double> currencies;

    public void fulfillOrder(Order order, Wallet wallet) throws InsufficientFundsException {
        wallet.getCurrencies().merge(order.getCurrencyBuy(), order.getAmountToSell()*order.getRate(), Double::sum);
        double curSubtractedFrom = wallet.getCurrencies().get(order.getCurrencySell());
        wallet.getCurrencies().put(order.getCurrencySell(), curSubtractedFrom - order.getAmountToSell());
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