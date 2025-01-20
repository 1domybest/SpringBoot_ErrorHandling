package jpabook.jpashoop;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jpabook.jpashoop.domain.*;
import jpabook.jpashoop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *  userA
 *  * JPA1 BOOK
 *  * JPA2 BOOK
 *
 * * userB
 *  * SPRING1 BOOK
 *  * SPRING2 BOOK
 */
// 앱이 시작될때 Component로 했기때문에 자동으로 읽어서 실행하고
@Component
@RequiredArgsConstructor
public class InitDB {

    private final initService initService;
    // InitDB가 자동으로 생성되면서 PostConstruct 때문에 init실행됨
    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class initService {

        private final EntityManager em;

        public void dbInit1() {
            Member member = createMember("userA","서울", "1", "1111");
            em.persist(member);

            Book book1 = createBook("JPA1 BOOK", 100);

            em.persist(book1);

            Book book2 = createBook("JPA2 BOOK", 100);

            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 10000, 2);

            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            System.out.println("주문완료");
            em.persist(order);
        }



        public void dbInit2() {
            Member member = createMember("userB", "전주", "2", "2222");
            em.persist(member);

            Book book1 = createBook("SPRING1 BOOK", 200);

            em.persist(book1);

            Book book2 = createBook("SPRING2 BOOK", 300);

            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000, 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 4);

            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);

            em.persist(order);
        }

        private static Delivery createDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }

        private static Book createBook(String BookName, int quantity) {
            Book book2 = new Book();
            book2.setName(BookName);
            book2.setPrice(10000);
            book2.setStockQuantity(quantity);
            return book2;
        }

        private static Member createMember(String name,String city, String street, String zipcode) {
            Member member = new Member();
            member.setName(name);
            member.setAddress(new Address(city, street, zipcode));
            return member;
        }
    }

}


