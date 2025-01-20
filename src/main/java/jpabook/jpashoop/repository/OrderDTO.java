package jpabook.jpashoop.repository;

import jpabook.jpashoop.domain.Address;
import jpabook.jpashoop.domain.Order;
import jpabook.jpashoop.domain.OrderItem;
import jpabook.jpashoop.domain.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderDTO {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private List<OrderItemDTO> orderItems;

    public OrderDTO(Order order) {
        this.orderId = order.getId();
        this.name = order.getMember().getName();
        this.orderDate = order.getOrderDate();
        this.orderStatus = order.getStatus();
        this.address = order.getDelivery().getAddress();
        this.orderItems = order.getOrderItems()
                                        .stream()
                                        .map(OrderItemDTO::new)
                                        .collect(Collectors.toList());

    }
}

