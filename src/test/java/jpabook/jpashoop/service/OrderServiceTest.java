package jpabook.jpashoop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashoop.domain.Address;
import jpabook.jpashoop.domain.Member;
import jpabook.jpashoop.domain.Order;
import jpabook.jpashoop.domain.OrderStatus;
import jpabook.jpashoop.domain.item.Book;
import jpabook.jpashoop.exception.NotEnoughStockException;
import jpabook.jpashoop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        //given
        Member member = createMember();

        Book book = createBook("시골 JAP", 10000, 10);

        //when
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "상품 주문시 상태는 ORDER");
        assertEquals(1, getOrder.getOrderItems().size(), "주문한 상품 종류 수가 정확해야한다.");
        assertEquals(10000 * orderCount, getOrder.getTotalPrice(), "주문 가격은 가격 * 수량이다.");
        assertEquals(8, book.getStockQuantity(), "주문 수량만큼 재고가 줄어야 한다.");
    }

    @Test() // <-- 테스트 유닛4에는 여기에 NotEnoughStockException.class 이게있었음
    @ExceptionHandler(NotEnoughStockException.class) // << 왜 이게 안먹을까? 검색해봐야힘 원래는
    public void 상품주문_재고수량초과() throws Exception {
        //given
        Member member = createMember();
        Book book = createBook("시골 JAP", 10000, 10);

        int orderCount = 11;

        //when
        orderService.order(member.getId(), book.getId(), orderCount);

        //then
        fail("재고 수량 부족 예외가 발생해야 한다.");
    }

    @Test()
    @ExceptionHandler(NotEnoughStockException.class)
    public void 주문취소() throws Exception {
        //given
        Member member = createMember();
        Book book = createBook("시골 JAP", 10000, 10);

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);

        //then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals(OrderStatus.CANCEL, getOrder.getStatus(),"주문 취소시 상태는 CANCEL 이다.");
        assertEquals(10, book.getStockQuantity(),"주문 취소된 상품은 그만큼 재고가 증가해야 한다.");
    }


    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "은평구", "녹번로4길 10"));
        em.persist(member);
        return member;
    }



}