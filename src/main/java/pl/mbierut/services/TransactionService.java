package pl.mbierut.services;

import pl.mbierut.exceptions.InsufficientFundsException;
import pl.mbierut.models.Currency;
import pl.mbierut.models.Order;
import pl.mbierut.models.User;

import java.util.HashMap;
import java.util.Map;

public class TransactionService {
    private Map<Long, Order> transactionsList;

    public void makeOrder(Order order, User user) {
        try {
            user.getWallet().fulfillOrder(order);
        } catch (InsufficientFundsException e) {
            e.printStackTrace();
        }
        user.getTransactionHistory().getList().add(order);
        this.addTransactionToList(order);
    }

    private void addTransactionToList(Order order) {
        transactionsList.put(transactionsList.size() + 1L, order);
    }

    public String getTransactionByNumber(long number) {
        return this.transactionsList.get(number).toString();
    }

    public double[] getBuyAndSellRates(Currency currency) {
        return new double[]{currency.getBuyRate(), currency.getSellRate()};
    }

    public TransactionService() {
        this.transactionsList = new HashMap<>();
    }
}