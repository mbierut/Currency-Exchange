package pl.mbierut.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.mbierut.database.repositories.WalletEntryRepository;
import pl.mbierut.exceptions.UserAlreadyExistsException;
import pl.mbierut.models.enums.Currency;
import pl.mbierut.models.requests.UserRegistrationRequest;
import pl.mbierut.services.UserService;

import java.util.List;

@Controller
public class UserController {
    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @ModelAttribute("currentUsername")
    public String getCurrentUser() {
        return getCurrentUserEmail();
    }

    @GetMapping("/")
    public String sendHome(Model model) {
        List<String[]> listOfCurrencies = Currency.getRateTable();
        model.addAttribute("listOfCurrencies", listOfCurrencies);
        return "index";
    }

    @GetMapping("/login")
    public String goToLogin() {
        return "login";
    }

    @GetMapping("/register")
    public String goToRegistration() {
        return "register";
    }

    @PostMapping("/register")
    public String registerNewUser(@RequestParam(name = "userName") String userName,
                                  @RequestParam(name = "email") String email,
                                  @RequestParam(name = "password") String password) {

        UserRegistrationRequest request = new UserRegistrationRequest(userName, email, password);
        try {
            service.registerNewUser(request);
        } catch (UserAlreadyExistsException e) {
            e.printStackTrace();
        }
        return "success";
    }

    @GetMapping("/wallet")
    public String showWallet(Model model) {
        model.addAttribute("email", "");
        return "wallet";
    }

    @PostMapping("/wallet")
    public String showWalletForEmail(Model model, @RequestParam(name = "email") String email) {
        String wallet = service.showWallet(email);
        model.addAttribute("wallet", wallet);
        return "wallet";
    }

    private String getCurrentUserEmail() {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            return null;
        }
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }
}
