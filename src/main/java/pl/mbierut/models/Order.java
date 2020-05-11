package pl.mbierut.models;

import java.time.LocalDateTime;

public class Order {
    private Funds fundsToSell;
    private Currency currencyBuy;
    private double rate;
    private LocalDateTime date;


    public Order(Funds fundsToSell, Currency currencyBuy, double rate) {
        this.fundsToSell = fundsToSell;
        this.currencyBuy = currencyBuy;
        this.rate = rate;
        date = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public Funds getFundsToSell() {
        return fundsToSell;
    }

    public void setFundsToSell(Funds fundsToSell) {
        this.fundsToSell = fundsToSell;
    }

    public Currency getCurrencyBuy() {
        return currencyBuy;
    }

    public void setCurrencyBuy(Currency currencyBuy) {
        this.currencyBuy = currencyBuy;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

}