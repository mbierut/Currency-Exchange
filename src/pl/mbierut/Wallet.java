package pl.mbierut;

import java.util.ArrayList;
import java.util.List;

public class Wallet {
    private double fundsInPLN;
    private List<Currency> currencyList;

    public Currency findCurrency(String code){
        for (Currency currency : currencyList){
            if (code.equals(currency.getCode())){
                return currency;
            }
        }
        return null;
    }

    public double getFundsInPLN() {
        return fundsInPLN;
    }

    public void setFundsInPLN(double fundsInPLN) {
        this.fundsInPLN = fundsInPLN;
    }

    public List<Currency> getCurrencyList() {
        return currencyList;
    }

    public void setCurrencyList(List<Currency> currencyList) {
        this.currencyList = currencyList;
    }

    public Wallet(double fundsInPLN) {
        this.fundsInPLN = fundsInPLN;
        this.currencyList = new ArrayList<>();
    }

    public Wallet(double fundsInPLN, List<Currency> currencyList) {
        this.fundsInPLN = fundsInPLN;
        this.currencyList = currencyList;
    }
}
