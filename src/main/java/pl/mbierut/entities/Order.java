package pl.mbierut.entities;

import lombok.Getter;
import lombok.ToString;
import pl.mbierut.models.Funds;
import pl.mbierut.models.enums.BuyOrSell;
import pl.mbierut.models.enums.Currency;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long orderNumber;

    @Column(name = "funds")
    private Funds fundsToBuyOrSell;

    @Column(name = "other_currency")
    private Currency otherCurrencyToSellOrBuy;

    @Column(name = "transaction_type")
    private BuyOrSell buyOrSell;

    @Column(name = "date")
    private LocalDateTime date;

    public Order(Funds fundsToBuyOrSell, Currency otherCurrencyToSellOrBuy, BuyOrSell buyOrSell) {
        this.fundsToBuyOrSell = fundsToBuyOrSell;
        this.otherCurrencyToSellOrBuy = otherCurrencyToSellOrBuy;
        this.buyOrSell = buyOrSell;
        this.date = LocalDateTime.now();
    }

//    double getFundsValueInOtherCurrency() {
//        double rate = -1.0;
//        if (this.getBuyOrSell().equals(BuyOrSell.sell)) {
//            rate = this.getOtherCurrencyToSellOrBuy().getBuyRate();
//        } else if (this.getBuyOrSell().equals(BuyOrSell.buy)) {
//            rate = this.getOtherCurrencyToSellOrBuy().getSellRate();
//        }
//        return this.getFundsToBuyOrSell().getValue(this.buyOrSell) / rate;
//    }

}