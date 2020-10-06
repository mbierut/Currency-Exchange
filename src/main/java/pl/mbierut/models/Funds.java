package pl.mbierut.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import pl.mbierut.models.enums.BuyOrSell;
import pl.mbierut.models.enums.Currency;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@ToString
@AllArgsConstructor
public class Funds {
    public Currency currency;
    public double amount;

    double getValue(BuyOrSell buyOrSell) {
        double rate = -1.0;
        if (buyOrSell.equals(BuyOrSell.buy)) {
            rate = this.getCurrency().getBuyRate();
        } else if (buyOrSell.equals(BuyOrSell.sell)) {
            rate = this.getCurrency().getSellRate();
        }
        return this.getAmount() * rate;
    }

}
