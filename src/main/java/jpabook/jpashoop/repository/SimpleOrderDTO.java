package jpabook.jpashoop.repository;

import jpabook.jpashoop.domain.Address;
import jpabook.jpashoop.domain.Order;
import jpabook.jpashoop.domain.OrderItem;
import jpabook.jpashoop.domain.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class SimpleOrderDTO {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;

    public SimpleOrderDTO(Order order) {
        this.orderId = order.getId();
        this.name = order.getMember().getName();
        this.orderDate = order.getOrderDate();
        this.orderStatus = order.getStatus();
        this.address = order.getDelivery().getAddress();
    }
}



