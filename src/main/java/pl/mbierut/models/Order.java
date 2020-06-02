package pl.mbierut.models;

import lombok.Getter;
import lombok.ToString;
import pl.mbierut.models.enums.BuyOrSell;
import pl.mbierut.models.enums.Currency;

import java.time.LocalDateTime;

@Getter
@ToString
public class Order {
    private Funds fundsToBuyOrSell;
    private Currency otherCurrencyToSellOrBuy;
    private BuyOrSell buyOrSell;
    private LocalDateTime date;

    public Order(Funds fundsToBuyOrSell, Currency otherCurrencyToSellOrBuy, BuyOrSell buyOrSell) {
        this.fundsToBuyOrSell = fundsToBuyOrSell;
        this.otherCurrencyToSellOrBuy = otherCurrencyToSellOrBuy;
        this.buyOrSell = buyOrSell;
        date = LocalDateTime.now();
    }

    double getFundsValueInOtherCurrency() {
        double rate = -1.0;
        if (this.getBuyOrSell().equals(BuyOrSell.sell)) {
            rate = this.getOtherCurrencyToSellOrBuy().getBuyRate();
        } else if (this.getBuyOrSell().equals(BuyOrSell.buy)) {
            rate = this.getOtherCurrencyToSellOrBuy().getSellRate();
        }
        return this.getFundsToBuyOrSell().getValue(this.buyOrSell) / rate;
    }

}