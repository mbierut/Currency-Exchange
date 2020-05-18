package pl.mbierut.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class Funds {
    public Currency currency;
    public double amount;

    public double getValue(){
        return this.getAmount() * this.getCurrency().getRate();
    }

}
