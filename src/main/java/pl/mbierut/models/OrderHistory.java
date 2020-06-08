package pl.mbierut.models;

import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter
@ToString
public class OrderHistory {
    private Map<Long, Order> map;

    public OrderHistory() {
        this.map = new HashMap<>();
    }

    public void saveFilledOrder(Order order, long number) {
        order.orderNumber = number;
        this.map.put(order.getOrderNumber(), order);
    }

    public Order getOrderByNumber(long number) {
        return this.map.get(number);
    }
}
