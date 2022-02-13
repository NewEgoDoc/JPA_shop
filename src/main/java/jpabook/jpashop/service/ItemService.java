package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
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
    public void saveItem(Item item){

        itemRepository.save(item);
    }

    @Transactional
    public void updateItem(Long itemId, Book book){
        Book findItem = (Book)itemRepository.findOne(itemId);

        findItem.setId(book.getId());
        findItem.setName(book.getName());
        findItem.setPrice(book.getPrice());
        findItem.setStockQuantity(book.getStockQuantity());
        findItem.setAuthor(book.getAuthor());
        findItem.setIsbn(book.getIsbn());
    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }
}
