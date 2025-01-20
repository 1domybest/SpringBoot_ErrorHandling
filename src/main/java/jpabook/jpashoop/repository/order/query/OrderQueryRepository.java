package jpabook.jpashoop.repository.order.query;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    final EntityManager em;

    public List<OrderQueryDTO> findOrderQueryDtos() {
        List<OrderQueryDTO> result = findOrders();
        result.forEach(orderQueryDTO -> {
            List<OrderItemQueryDTO> orderItems = findOrderItems(orderQueryDTO.getOrderId());
            orderQueryDTO.setOrderItems(orderItems);
        });
        return result;
    }

    private List<OrderItemQueryDTO> findOrderItems(Long orderId) {
        return em.createQuery("select new jpabook.jpashoop.repository.order.query.OrderItemQueryDTO(oi.order.id, i.name, i.price, oi.count)" +
                " from OrderItem oi" +
                " join oi.item i" +
                " where oi.order.id = :orderId", OrderItemQueryDTO.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }

    private List<OrderQueryDTO> findOrders() {
        return em.createQuery("select new jpabook.jpashoop.repository.order.query.OrderQueryDTO(o.id, m.name, o.orderDate, o.status, d.address)" +
                " from Order o" +
                " join o.member m" +
                " join o.delivery d", OrderQueryDTO.class).getResultList();
    }

    public List<OrderQueryDTO> findAllByDto_optimization() {
        // order List 가져오기
        List<OrderQueryDTO> result = findOrders();

        // order List 에서 orderId 뽑아오기
        List<Long> orderIds = result.stream()
                .map(o -> o.getOrderId())
                .collect(Collectors.toList());

        // 받아온 orderId List를 in절을사용하여 해당 id를 가지고있는 orderItem을 OrderItemQueryDTO로 변환시켜 다 가져옴
        // 여기에서 4개뜸 orderid 1번 2개, orderId 2번 2개 총 4개
        List<OrderItemQueryDTO> orderItems = em.createQuery("select new jpabook.jpashoop.repository.order.query.OrderItemQueryDTO(oi.order.id, i.name, i.price, oi.count)" +
                        " from OrderItem oi" +
                        " join oi.item i" +
                        " where oi.order.id in :orderIds", OrderItemQueryDTO.class)
                .setParameter("orderIds", orderIds)
                .getResultList();


        System.out.println("오더 갯수" + orderItems.size());
        // 가져온 OrderItemQueryDTO 리스트를 stream()으로 반복시켜서
        // 같은 orderId인것들을 묶어서 표현 4개였던게 -> groupingBy로 인해 2개로 압축
        Map<Long, List<OrderItemQueryDTO>> orderItemMap = orderItems.stream()
                .collect(Collectors.groupingBy(orderItemQueryDTO -> orderItemQueryDTO.getOrderId()));

        // 최종적으로 반환하려는 OrderQueryDTO 도 반복시켜서
        // orderItem을 add

        result.forEach(orderItemQueryDTO -> {
            orderItemQueryDTO.setOrderItems(orderItemMap.get(orderItemQueryDTO.getOrderId()));
        });

        return result;
    }

    public List<OrderFlatDto> findAllByDto_flat() {
        List<OrderFlatDto> list = em.createQuery(
                "select new jpabook.jpashoop.repository.order.query.OrderFlatDto(o.id, m.name, o.orderDate, o.status, d.address, i.name, i.price, oi.count)" +
                        " from Order o" +
                        " join o.member m" +
                        " join o.delivery d" +
                        " join o.orderItems oi" +
                        " join oi.item i", OrderFlatDto.class
        ).getResultList();

        return list;
    }
}
