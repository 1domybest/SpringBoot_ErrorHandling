package jpabook.jpashoop.repository;

import jpabook.jpashoop.domain.OrderItem;
import lombok.Data;

@Data
public class OrderItemDTO {
    private String itemName;
    private int orderPrice;
    private int count;

    public OrderItemDTO(OrderItem orderItem) {
        this.itemName = orderItem.getItem().getName();
        this.orderPrice = orderItem.getItem().getPrice();
        this.count = orderItem.getCount();
    }
}
