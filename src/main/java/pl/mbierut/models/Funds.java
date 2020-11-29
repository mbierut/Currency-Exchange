package pl.mbierut.models;

import lombok.*;
import pl.mbierut.models.enums.BuyOrSell;
import pl.mbierut.models.enums.Currency;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Funds {

    private Currency currency;
    private double amount;

    public double getValue(BuyOrSell buyOrSell) {
        double rate = -1.0;
        if (buyOrSell.equals(BuyOrSell.buy)) {
            rate = this.getCurrency().getBuyRate();
        } else if (buyOrSell.equals(BuyOrSell.sell)) {
            rate = this.getCurrency().getSellRate();
        }
        return this.getAmount() * rate;
    }

}
