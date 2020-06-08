package pl.mbierut.services;

import pl.mbierut.exceptions.InsufficientFundsException;
import pl.mbierut.models.Order;
import pl.mbierut.models.OrderHistory;
import pl.mbierut.models.User;
import pl.mbierut.models.enums.Currency;

public class TransactionService {
    private OrderHistory orderHistory;

    public void makeOrder(Order order, User user) {
        try {
            user.getWallet().fulfillOrder(order);
        } catch (InsufficientFundsException e) {
            e.printStackTrace();
        }
        long number = this.orderHistory.getMap().size() + 1L;
        user.getOrderHistory().saveFilledOrder(order, number);
        this.orderHistory.saveFilledOrder(order, number);
    }

    public String getTransactionByNumber(long number) {
        return this.orderHistory.getOrderByNumber(number).toString();
    }

    public double[] getBuyAndSellRates(Currency currency) {
        return new double[]{currency.getBuyRate(), currency.getSellRate()};
    }

    public TransactionService() {
        this.orderHistory = new OrderHistory();
    }
}