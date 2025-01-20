package jpabook.jpashoop.repository.order.simplequery;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderSimpleQueryRepository {

    private final EntityManager em;

    public List<OrderSimpleQueryDTO> findOrderDTOs() {
        return em.createQuery(
                        "select new jpabook.jpashoop.repository.order.simplequery.OrderSimpleQueryDTO(o.id, o.member.name, o.orderDate, o.status, o.delivery.address)" +
                                " from Order o" +
                                " join o.member" +
                                " join o.delivery", OrderSimpleQueryDTO.class)

                .getResultList();
    }
}
