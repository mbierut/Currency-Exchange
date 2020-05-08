package pl.mbierut;

import java.time.LocalDateTime;

public class Order {
    private Currency currencySell;
    private Currency currencyBuy;
    private double rate;
    private double amountToSell;
    private LocalDateTime date;


    public Order(Currency currencySell, Currency currencyBuy, double rate, double amountToSell) {
        this.currencySell = currencySell;
        this.currencyBuy = currencyBuy;
        this.rate = rate;
        this.amountToSell = amountToSell;
        date = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public Currency getCurrencySell() {
        return currencySell;
    }

    public void setCurrencySell(Currency currencySell) {
        this.currencySell = currencySell;
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

    public double getAmountToSell() {
        return amountToSell;
    }

    public void setAmountToSell(double amountToSell) {
        this.amountToSell = amountToSell;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

}