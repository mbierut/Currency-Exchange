package pl.mbierut.models;

import lombok.Getter;

@Getter
public class Funds {
    public Currency currency;
    public double amount;

    public Currency getCurrency() {
        return currency;
    }

    public Funds(Currency currency, double amount) {
        this.currency = currency;
        this.amount = amount;
    }
}
