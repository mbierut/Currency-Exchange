package pl.mbierut.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.mbierut.database.entities.OrderEntity;
import pl.mbierut.models.Funds;
import pl.mbierut.models.enums.BuyOrSell;
import pl.mbierut.models.enums.Currency;
import pl.mbierut.models.requests.OrderRequest;
import pl.mbierut.services.TransactionService;

@Controller
public class TransactionController {
    private TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
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
        this.service.makeOrder(new OrderRequest(new OrderEntity(new Funds(cur1, amount), cur2, buyOrSell), email));
        return "success";
    }


}
