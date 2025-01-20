package jpabook.jpashoop.api;

import jpabook.jpashoop.domain.Order;
import jpabook.jpashoop.domain.OrderItem;
import jpabook.jpashoop.repository.OrderRepository;
import jpabook.jpashoop.repository.OrderSearch;
import jpabook.jpashoop.repository.OrderDTO;
import jpabook.jpashoop.repository.order.query.OrderFlatDto;
import jpabook.jpashoop.repository.order.query.OrderItemQueryDTO;
import jpabook.jpashoop.repository.order.query.OrderQueryDTO;
import jpabook.jpashoop.repository.order.query.OrderQueryRepository;
import jpabook.jpashoop.service.query.OrderQueryService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;
import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;
    private final OrderQueryService orderQueryService;

    @GetMapping("/api/v1/orders")
    public List<Order> orderV1() {
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        for (Order order : orders) {
            order.getMember().getName();
            order.getDelivery().getAddress();
            for (OrderItem orderItem : order.getOrderItems()) {
                orderItem.getItem().getName();
            }
        }

        return orders;
    }

    @GetMapping("/api/v2/orders")
    public List<OrderDTO> orderV2() {
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());

        List<OrderDTO> result = orders.stream()
                .map(OrderDTO::new)
                .collect(Collectors.toList());

        return result;
    }

    @GetMapping("/api/v3/orders")
    public List<OrderDTO> orderV3() {
        return orderQueryService.orderV3();
    }

    @GetMapping("/api/v3.1/orders")
    public List<OrderDTO> orderV3_page(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                       @RequestParam(value = "limit", defaultValue = "100") int limit
                                       ) {
        List<Order> orders = orderRepository.findAllWithMemberDelivery(offset, limit);
        List<OrderDTO> result = orders.stream()
                .map(OrderDTO::new)
                .collect(Collectors.toList());

        return result;
    }

    @GetMapping("/api/v4/orders")
    public List<OrderQueryDTO> orderV4(
    ) {
        return orderQueryRepository.findOrderQueryDtos();
    }

    @GetMapping("/api/v5/orders")
    public List<OrderQueryDTO> orderV5(
    ) {
        return orderQueryRepository.findAllByDto_optimization();
    }

    @GetMapping("/api/v6/orders")
    public List<OrderQueryDTO> orderV6(
    ) {
        List<OrderFlatDto> flats = orderQueryRepository.findAllByDto_flat();
        List<OrderQueryDTO> collect = flats.stream()
                .collect(groupingBy(o -> new OrderQueryDTO(o.getOrderId(), o.getName(), o.getOrderDate(), o.getOrderStatus(), o.getAddress()),
                        mapping(o -> new OrderItemQueryDTO(o.getOrderId(), o.getItemName(), o.getOrderPrice(), o.getCount()), toList())
                )).entrySet().stream()
                .map(e -> new OrderQueryDTO(e.getKey().getOrderId(), e.getKey().getName(), e.getKey().getOrderDate(), e.getKey().getOrderStatus(), e.getKey().getAddress(), e.getValue()))
                .collect(toList());

        System.out.println("컬렉션 크기: " + collect.size());
        return collect;
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

}
