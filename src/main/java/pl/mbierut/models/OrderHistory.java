package pl.mbierut.models;

import lombok.Getter;
import lombok.ToString;
import pl.mbierut.entities.Order;

import java.util.HashMap;
import java.util.Map;

@Getter
@ToString
public class OrderHistory {
    private Map<Long, pl.mbierut.entities.Order> map;

    public OrderHistory() {
        this.map = new HashMap<>();
    }

    public void saveFilledOrder(pl.mbierut.entities.Order order, long number) {
        order.orderNumber = number;
        this.map.put(order.getOrderNumber(), order);
    }

    public Order getOrderByNumber(long number) {
        return this.map.get(number);
    }
}
