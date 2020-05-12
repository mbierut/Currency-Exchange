package pl.mbierut.models;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
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


}