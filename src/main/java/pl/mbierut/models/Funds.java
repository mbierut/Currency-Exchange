package pl.mbierut.models;

public class Funds {
    public Currency currency;
    public double amount;

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Funds(Currency currency, double amount) {
        this.currency = currency;
        this.amount = amount;
    }
}
