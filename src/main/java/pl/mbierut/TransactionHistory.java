package pl.mbierut;

import java.util.List;

public class TransactionHistory {
    private List<Order> list;


    @Override
    public String toString() {
        return this.list.toString();
    }
}
