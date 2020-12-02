package pl.mbierut.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.mbierut.database.entities.OrderEntity;
import pl.mbierut.database.entities.UserEntity;
import pl.mbierut.exceptions.InsufficientFundsException;
import pl.mbierut.models.Funds;
import pl.mbierut.models.enums.BuyOrSell;
import pl.mbierut.models.enums.Currency;
import pl.mbierut.models.requests.OrderRequest;
import pl.mbierut.services.TransactionService;
import pl.mbierut.services.UserService;

@Controller
public class TransactionController {
    private TransactionService transactionService;
    private UserService userService;

    public TransactionController(TransactionService transactionService, UserService userService) {
        this.transactionService = transactionService;
        this.userService = userService;
    }

    @GetMapping("/transactions")
    public String chooseTransactionType() {
        return "transactions";
    }

    @GetMapping("/order")
    public String prepareOrder() {
        return "order";
    }

    @PostMapping("/order")
    public String makeOrder(@RequestParam(name = "currency1") String curName1,
                            @RequestParam(name = "amount") double amount,
                            @RequestParam(name = "currency2") String curName2,
                            @RequestParam(name = "buyOrSell") String buyOrSellString,
                            @RequestParam(name = "email") String email) {
        Currency cur1 = Currency.valueOf(curName1);
        Currency cur2 = Currency.valueOf(curName2);
        BuyOrSell buyOrSell = BuyOrSell.valueOf(buyOrSellString);
        this.transactionService.makeOrder(new OrderRequest(new OrderEntity(new Funds(cur1, amount), cur2, buyOrSell), email));
        return "success";
    }

    @GetMapping("/add-funds")
    public String prepareFundsToAdd() {
        return "add-funds";
    }

    @PostMapping("/add-funds")
    public String addFunds(@RequestParam(name = "email") String email,
                           @RequestParam(name = "currency") String currencyName,
                           @RequestParam(name = "amount") double amount) {
        Currency currency = Currency.valueOf(currencyName);
        Funds funds = new Funds(currency, amount);
        UserEntity user = this.userService.getUser(email);
        if (user != null) {
            user.addFunds(funds);
            return "success";
        }
        return "error";
    }

    @GetMapping("/withdraw-funds")
    public String prepareFundsToWithdraw() {
        return "withdraw-funds";
    }

    @PostMapping("/withdraw-funds")
    public String withdrawFunds(@RequestParam(name = "email") String email,
                                @RequestParam(name = "currency") String currencyName,
                                @RequestParam(name = "amount") double amount) {
        Currency currency = Currency.valueOf(currencyName);
        Funds funds = new Funds(currency, amount);
        UserEntity user = this.userService.getUser(email);
        if (user != null) {
            try {
                user.withdrawFunds(funds);
            } catch (InsufficientFundsException e) {
                e.printStackTrace();
                return "error";
            }
            return "success";
        }
        return "error";
    }

    @GetMapping("/send-funds")
    public String prepareFundsToSend() {
        return "add-funds";
    }

    @PostMapping("/send-funds")
    public String sendFunds(@RequestParam(name = "email1") String email1,
                            @RequestParam(name = "email2") String email2,
                            @RequestParam(name = "currency") String currencyName,
                            @RequestParam(name = "amount") double amount) {
        Currency currency = Currency.valueOf(currencyName);
        Funds funds = new Funds(currency, amount);
        UserEntity user1 = this.userService.getUser(email1);
        UserEntity user2 = this.userService.getUser(email2);
        if (user1 != null && user2 != null) {
            try {
                user1.sendMoney(user2, funds);
            } catch (InsufficientFundsException e) {
                e.printStackTrace();
                return "error";
            }
            return "success";
        }
        return "error";
    }
}
