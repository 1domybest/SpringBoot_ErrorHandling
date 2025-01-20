package jpabook.jpashoop.service.query;

import jpabook.jpashoop.domain.Order;
import jpabook.jpashoop.repository.OrderDTO;
import jpabook.jpashoop.repository.OrderRepository;
import jpabook.jpashoop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderQueryService {

    private final OrderRepository orderRepository;

    public List<OrderDTO> orderV3() {
        List<Order> orders = orderRepository.findAllWithItem();

        List<OrderDTO> result = orders.stream()
                .map(OrderDTO::new)
                .collect(Collectors.toList());

        return result;
    }
}
