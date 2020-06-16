package pl.mbierut.services;

import org.springframework.stereotype.Service;
import pl.mbierut.exceptions.InsufficientFundsException;
import pl.mbierut.models.OrderHistory;
import pl.mbierut.models.User;
import pl.mbierut.models.enums.Currency;
import pl.mbierut.models.requests.OrderRequest;
import pl.mbierut.repositories.UserRepository;

@Service
public class TransactionService {
    private OrderHistory orderHistory;
    private UserRepository repository;

    public void makeOrder(OrderRequest request) {
        try {
            User user = repository.findUserByEmail(request.getUserEmail());
            user.getWallet().fulfillOrder(request.getOrder());
            long number = this.orderHistory.getMap().size() + 1L;
            user.getOrderHistory().saveFilledOrder(request.getOrder(), number);
            this.orderHistory.saveFilledOrder(request.getOrder(), number);
        } catch (InsufficientFundsException e) {
            e.printStackTrace();
        }
    }

    public String getTransactionByNumber(long number) {
        return this.orderHistory.getOrderByNumber(number).toString();
    }

    public double[] getBuyAndSellRates(Currency currency) {
        return new double[]{currency.getBuyRate(), currency.getSellRate()};
    }

    public TransactionService(UserRepository repository) {
        this.orderHistory = new OrderHistory();
        this.repository = repository;
    }
}