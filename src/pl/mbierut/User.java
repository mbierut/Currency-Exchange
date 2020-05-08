package pl.mbierut;

public class User {
    private String username;
    private String email;
    private String password;
    private Wallet wallet;
    private TransactionHistory transactionHistory;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.wallet = new Wallet();
        this.transactionHistory = new TransactionHistory();
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public TransactionHistory getTransactionHistory() {
        return transactionHistory;
    }
}
