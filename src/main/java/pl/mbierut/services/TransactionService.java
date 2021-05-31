package pl.mbierut.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mbierut.database.entities.UserEntity;
import pl.mbierut.database.repositories.OrderRepository;
import pl.mbierut.database.repositories.UserRepository;
import pl.mbierut.exceptions.InsufficientFundsException;
import pl.mbierut.models.enums.Currency;
import pl.mbierut.models.requests.OrderRequest;

@Service
@AllArgsConstructor
public class TransactionService {
    private UserRepository userRepository;
    private OrderRepository orderRepository;

    public void makeOrder(OrderRequest request) throws InsufficientFundsException {
        UserEntity user = this.userRepository.findByEmail(request.getUserEmail());
        user.fulfillOrder(request.getOrder());
    }

    public String getTransactionByNumber(long number) {
        return this.orderRepository.findById(number).toString();
    }

    public double[] getBuyAndSellRates(Currency currency) {
        return new double[]{currency.getBuyRate(), currency.getSellRate()};
    }

}