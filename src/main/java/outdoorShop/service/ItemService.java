package outdoorShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import outdoorShop.controller.BenchFormDto;
import outdoorShop.domain.items.Item;
import outdoorShop.domain.items.product.Bench;
import outdoorShop.repository.ItemRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.saveItem(item);
    }

    @Transactional
    public void insertBench(BenchFormDto benchFormDto) throws Exception {
      Bench bench=Bench.builder()
              .name(benchFormDto.getName())
              .price(benchFormDto.getPrice())
              .stockQuantity(benchFormDto.getStockQuantity())
              .material(benchFormDto.getMaterial())
              .size(benchFormDto.getSize())
              .build();
      itemRepository.saveItem(bench);
    }

    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity) {
        Item findItem = itemRepository.findOne(itemId);
        findItem.saveName(name);
        findItem.savePrice(price);
        findItem.saveStockQuantity(stockQuantity);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

}
