package pl.mbierut.models.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.mbierut.database.entities.OrderEntity;

@Getter
@AllArgsConstructor
public class OrderRequest {
    private OrderEntity order;
    private String userEmail;
}
