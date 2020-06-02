package pl.mbierut.services;

import pl.mbierut.exceptions.InsufficientFundsException;
import pl.mbierut.models.enums.Currency;
import pl.mbierut.models.Order;
import pl.mbierut.models.User;

import java.util.HashMap;
import java.util.Map;

public class TransactionService {
    private Map<Long, Order> transactionsMap;

    public void makeOrder(Order order, User user) {
        try {
            user.getWallet().fulfillOrder(order);
        } catch (InsufficientFundsException e) {
            e.printStackTrace();
        }
        user.getOrderHistory().getList().add(order);
        this.addTransactionToMap(order);
    }

    private void addTransactionToMap(Order order) {
        transactionsMap.put(generateNewOrderNumber(), order);
    }

    private long generateNewOrderNumber() {
        return this.transactionsMap.size() + 1L;
    }

    public String getTransactionByNumber(long number) {
        return this.transactionsMap.get(number).toString();
    }

    public double[] getBuyAndSellRates(Currency currency) {
        return new double[]{currency.getBuyRate(), currency.getSellRate()};
    }

    public TransactionService() {
        this.transactionsMap = new HashMap<>();
    }
}