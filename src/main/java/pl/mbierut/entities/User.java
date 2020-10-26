package pl.mbierut.entities;

import lombok.Getter;
import pl.mbierut.models.OrderHistory;

import javax.persistence.*;

@Entity
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @OneToOne(mappedBy = "user")
    @PrimaryKeyJoinColumn
    private Wallet wallet;

//    @Column(name = "order_history")
//    private OrderHistory orderHistory;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.wallet = new Wallet();
//        this.orderHistory = new OrderHistory();
    }

//    public String showWallet() {
//        return this.getWallet().toString();
//    }
}
