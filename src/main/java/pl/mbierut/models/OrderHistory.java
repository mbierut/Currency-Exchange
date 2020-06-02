package pl.mbierut.models;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class OrderHistory {
    private List<Order> list;

    public OrderHistory() {
        this.list = new ArrayList<>();
    }
}
