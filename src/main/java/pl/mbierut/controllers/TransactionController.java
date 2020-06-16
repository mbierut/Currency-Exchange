package pl.mbierut.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.mbierut.models.Order;
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
    public String makeOrder(@RequestParam(name = "order")Order order,
                            @RequestParam(name = "email") String email) {
        this.service.makeOrder(new OrderRequest(order, email));
        return "success";
    }


}
