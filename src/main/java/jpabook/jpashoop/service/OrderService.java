package jpabook.jpashoop.service;

import jpabook.jpashoop.domain.Delivery;
import jpabook.jpashoop.domain.Member;
import jpabook.jpashoop.domain.Order;
import jpabook.jpashoop.domain.OrderItem;
import jpabook.jpashoop.domain.item.Item;
import jpabook.jpashoop.repository.ItemRepository;
import jpabook.jpashoop.repository.MemberRepositoryOld;
import jpabook.jpashoop.repository.OrderRepository;
import jpabook.jpashoop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepositoryOld memberRepositoryOld;
    private final ItemRepository itemRepository;

    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        // 엔티티 조회
        Member member = memberRepositoryOld.findOne(memberId);
        Item item = itemRepository.fincOne(itemId);

        // 배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // 주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        // 주문 저장 cascade = CascadeType.ALL
        orderRepository.save(order);

        // 어디까지 cascade = CascadeType.ALL 를해야하나?
        // 같이 항상붙어다니는 정도까지
        return order.getId();
    }

    /**
     * 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        order.cancel();
        // 여기에서 repository를 통해 가져왔기때문에 또 repository를 사용하여 업데이트 할필요가 없다
        // 알아서해줌
    }

    /**
     * 검색
     */
    public List<Order> findOrders(OrderSearch orderSearch) {
        List<Order> all = orderRepository.findAll(orderSearch);
        for (Order order : all) {
            for (OrderItem item : order.getOrderItems()) {
                System.out.println("아이템 이름 " + item.getItem().getName());
            }
        }
        return all;
    }
}
