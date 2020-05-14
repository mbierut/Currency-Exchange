package pl.mbierut.services;

import pl.mbierut.exceptions.InsufficientFundsException;
import pl.mbierut.models.Order;
import pl.mbierut.models.User;

public class TransactionService {
    public void makeOrder(Order order, User user){
        try {
            user.getWallet().fulfillOrder(order);
        } catch (InsufficientFundsException e) {
            e.printStackTrace();
        }
        user.getTransactionHistory().getList().add(order);
    }
}