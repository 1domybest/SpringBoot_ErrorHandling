package jpabook.jpashoop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
/*
상속한 Item의 dtype 중 나는 어떤 이넘인지? 에 대한 값
*/
@DiscriminatorValue("A")
@Getter @Setter
public class Album extends Item{

    private String artist;

    private String etc;


}
