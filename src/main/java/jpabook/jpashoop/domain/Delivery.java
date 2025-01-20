package jpabook.jpashoop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Delivery {
    // joincolume = 주인
    // mappedby = 결속
    @Id
    @Column(name = "delivery_id")
    @GeneratedValue
    private Long id;

    @JsonIgnore
    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING) // 중간에 바뀌어도 ㄱㅊ음
    private DeliveryStatus status; // 배송준비 배송 READY, COMP
}
