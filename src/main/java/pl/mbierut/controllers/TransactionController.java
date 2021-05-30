package pl.mbierut.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.mbierut.database.entities.OrderEntity;
import pl.mbierut.database.entities.UserEntity;
import pl.mbierut.database.repositories.WalletEntryRepository;
import pl.mbierut.exceptions.InsufficientFundsException;
import pl.mbierut.models.Funds;
import pl.mbierut.models.enums.BuyOrSell;
import pl.mbierut.models.enums.Currency;
import pl.mbierut.models.requests.OrderRequest;
import pl.mbierut.services.TransactionService;
import pl.mbierut.services.UserService;

@Controller
public class TransactionController {
    private WalletEntryRepository walletEntryRepository;
    private TransactionService transactionService;
    private UserService userService;

    public TransactionController(TransactionService transactionService, UserService userService, WalletEntryRepository walletEntryRepository) {
        this.transactionService = transactionService;
        this.userService = userService;
        this.walletEntryRepository = walletEntryRepository;
    }

    @ModelAttribute("currentUsername")
    public String getCurrentUser() {
        return getCurrentUserEmail();
    }

    @GetMapping("/transactions")
    public String chooseTransactionType(Model model) {
        model.addAttribute("yourCurrencies", walletEntryRepository.findByUser_Email(getCurrentUserEmail()));
        return "transactions";
    }

    @GetMapping("/order")
    public String prepareOrder() {
        return "order";
    }

    @PostMapping("/order")
    @Transactional
    public String makeOrder(@RequestParam(name = "currencyName1") String currencyName1,
                            @RequestParam(name = "amount") double amount,
                            @RequestParam(name = "currencyName2") String currencyName2,
                            @RequestParam(name = "buyOrSell") String buyOrSellString) {
        Currency cur1 = Currency.valueOf(currencyName1);
        Currency cur2 = Currency.valueOf(currencyName2);
        BuyOrSell buyOrSell = BuyOrSell.valueOf(buyOrSellString);
        try {
            this.transactionService.makeOrder(new OrderRequest(new OrderEntity(new Funds(cur1, amount), cur2, buyOrSell), getCurrentUserEmail()));
        } catch (InsufficientFundsException e) {
            e.printStackTrace();
            return "error";
        }
        return "success";
    }

    @GetMapping("/add-funds")
    public String prepareFundsToAdd() {
        return "add-funds";
    }

    @PostMapping("/add-funds")
    @Transactional
    public String addFunds(@RequestParam(name = "currencyName") String currencyName, @RequestParam(name = "amount") double amount) {
        Currency currency = Currency.valueOf(currencyName);
        Funds funds = new Funds(currency, amount);
        UserEntity user = this.userService.getUser(getCurrentUserEmail());
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
    @Transactional
    public String withdrawFunds(@RequestParam(name = "currencyName") String currencyName,
                                @RequestParam(name = "amount") double amount) {
        Currency currency = Currency.valueOf(currencyName);
        Funds funds = new Funds(currency, amount);
        UserEntity user = this.userService.getUser(getCurrentUserEmail());
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
        return "send-funds";
    }

    @PostMapping("/send-funds")
    @Transactional
    public String sendFunds(@RequestParam(name = "email2") String email2,
                            @RequestParam(name = "currencyName") String currencyName,
                            @RequestParam(name = "amount") double amount) {
        Currency currency = Currency.valueOf(currencyName);
        Funds funds = new Funds(currency, amount);
        UserEntity user1 = this.userService.getUser(getCurrentUserEmail());
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

    private String getCurrentUserEmail() {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            return null;
        }
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }
}
