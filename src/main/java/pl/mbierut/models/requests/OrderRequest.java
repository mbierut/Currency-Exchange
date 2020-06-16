package pl.mbierut.models.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.mbierut.models.Order;

@Getter
@AllArgsConstructor
public class OrderRequest {
    private Order order;
    private String userEmail;
}
