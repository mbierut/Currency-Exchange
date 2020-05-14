package pl.mbierut.models;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class TransactionHistory {
    private List<Order> list;

    public TransactionHistory() {
        this.list = new ArrayList<>();
    }
}
