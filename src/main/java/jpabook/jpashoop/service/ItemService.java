package jpabook.jpashoop.service;

import jpabook.jpashoop.controller.BookForm;
import jpabook.jpashoop.domain.item.Book;
import jpabook.jpashoop.domain.item.Item;
import jpabook.jpashoop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    @Transactional
    public void updateItem(Long itemId, BookForm form) {
        // 지속적으로 또 다른곳으로 넘겨서 데이터를 변경시킨다면? findOne 해서 받은 데이터를 사용한후 save하고
        // 그게아니면 그냥 merge << 조심해야함 merge는 모든 데이터가 다 바뀜...; 선택을 할수있는개념이 아님 그래서 그냥 find 사용하면될듯

        Book book = (Book)itemRepository.fincOne(itemId);
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());
        book.setId(form.getId());
        // book.addStock(form.getStockQuantity()); 이런식으로 도메인안에있는 비지니스 로직을 사용하자;
        // 영속화시켜서 바로 업데이트
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public Item findOne(Long id) {
        return itemRepository.fincOne(id);
    }

}
