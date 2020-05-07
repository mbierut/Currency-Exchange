package pl.mbierut;

import java.time.LocalDateTime;

public class TransactionService {

    public String buyCurrency(String code, double amount, double rate, Wallet wallet) {
        if (amount <= 0.0 || rate <= 0.0){
            return "Error: non-positive numbers";
        }
        if (wallet.getFundsInPLN() < amount*rate){
            return "Error: insufficient funds";
        }
        Currency cur = wallet.findCurrency(code);
        if (cur != null){
            cur.setAmount(cur.getAmount() + amount);
        } else {
            wallet.getCurrencyList().add(new Currency(amount, code));
        }
        return LocalDateTime.now() + ": " + amount + code + " were bought for " + amount*rate + "PLN";
    }

    public String sellCurrency(String code, double amount, double rate, Wallet wallet) {
        if (amount <= 0.0 || rate <= 0.0){
            return "Error: non-positive numbers";
        }
        Currency cur = wallet.findCurrency(code);
        if (cur == null || cur.getAmount() < amount){
            return "Error: insufficient funds";
        }
        wallet.setFundsInPLN(wallet.getFundsInPLN() + amount*rate);
        cur.setAmount(cur.getAmount() - amount);
        return LocalDateTime.now() + ": " + amount + code + " were sold for " + amount*rate + "PLN";
    }

}