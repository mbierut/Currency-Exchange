package pl.mbierut;

public class Currency {
    private double amount;
    private String code;

    public Currency(double amount, String code) {
        this.amount = amount;
        this.code = code;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
