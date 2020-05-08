package pl.mbierut;

import java.util.HashMap;
import java.util.Map;
public class Wallet {
    public Map<Currency, Double> currencies;

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