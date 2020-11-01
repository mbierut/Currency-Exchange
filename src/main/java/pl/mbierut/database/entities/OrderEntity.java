package pl.mbierut.database.entities;

import lombok.*;
import pl.mbierut.models.Funds;
import pl.mbierut.models.enums.BuyOrSell;
import pl.mbierut.models.enums.Currency;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    public long orderNumber;

    @Embedded
    private Funds fundsToBuyOrSell;

    @Column(name = "other_currency")
    private Currency otherCurrencyToSellOrBuy;

    @Column(name = "transaction_type")
    private BuyOrSell buyOrSell;

    @Column(name = "date")
    private LocalDateTime date;

}