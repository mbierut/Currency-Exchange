package pl.mbierut.models;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class Order {
    private Funds fundsToSell;
    private Currency currencyBuy;
    private LocalDateTime date;

    public Order(Funds fundsToSell, Currency currencyBuy) {
        this.fundsToSell = fundsToSell;
        this.currencyBuy = currencyBuy;
        date = LocalDateTime.now();
    }

    public double getFundsValue(){
        return this.getFundsToSell().getValue()/this.getCurrencyBuy().getRate();
    }

}