package jpabook.jpashoop.api;

import jpabook.jpashoop.domain.Order;
import jpabook.jpashoop.repository.OrderRepository;
import jpabook.jpashoop.repository.OrderSearch;
import jpabook.jpashoop.repository.SimpleOrderDTO;
import jpabook.jpashoop.repository.order.simplequery.OrderSimpleQueryDTO;
import jpabook.jpashoop.repository.order.simplequery.OrderSimpleQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * xToOne (ManyToOne, OneToOne)
 * Order
 * Order -> Member
 * Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    @GetMapping("api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        for (Order order: all) {
            order.getMember().getName(); // Lazy 강제 초기화
            order.getDelivery().getAddress();
        }
        return all;
    }

    @GetMapping("api/v2/simple-orders")
    public List<SimpleOrderDTO> ordersV2() {
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        List<SimpleOrderDTO> collect = orders.stream()
                .map(order -> new SimpleOrderDTO(order))
                .collect(Collectors.toList());
        return collect;
    }

    @GetMapping("api/v3/simple-orders")
    public List<SimpleOrderDTO> ordersV3() {
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        List<SimpleOrderDTO> collect = orders.stream()
                .map(order -> new SimpleOrderDTO(order))
                .collect(Collectors.toList());
        return collect;
    }

    @GetMapping("api/v4/simple-orders")
    public List<OrderSimpleQueryDTO> ordersV4() {
        return orderSimpleQueryRepository.findOrderDTOs();
    }

}
