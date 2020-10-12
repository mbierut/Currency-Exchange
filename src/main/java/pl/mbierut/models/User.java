package pl.mbierut.models;

import lombok.Getter;

@Getter
public class User {

    private String username;

    private String email;

    private String password;

    private Wallet wallet;

    private OrderHistory orderHistory;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.wallet = new Wallet();
        this.orderHistory = new OrderHistory();
    }

    public String showWallet() {
        return this.getWallet().toString();
    }
}
