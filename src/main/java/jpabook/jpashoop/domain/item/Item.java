package jpabook.jpashoop.domain.item;

import jakarta.persistence.*;
import jpabook.jpashoop.domain.Category;
import jpabook.jpashoop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Entity
/*
상속받은 데이터들을 어떻게 넣을것인지
item 테이블에 한번에 넣을것인지?, 각기 따로 테이블을 만들것인지?
**/
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

/*
구분을 어떤 변수명으로 지을것인지? [앨범, 책, 영화 의 enum의 클래스 이름정도?로 생각하면 될듯]
**/
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<Category>();

    //==비지니스 로직==//

    /**
     * stock 증가
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /**
     * stock 감소
     */
    public void removeStock(int quantity) {
        int restStock =  this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }

        this.stockQuantity = restStock;
    }
}
