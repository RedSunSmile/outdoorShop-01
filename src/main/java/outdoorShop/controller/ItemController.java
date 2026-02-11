package outdoorShop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import outdoorShop.domain.items.Item;
import outdoorShop.domain.items.product.Bench;
import outdoorShop.service.ItemService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BenchFormDto());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BenchFormDto benchFormDto) throws Exception{
        itemService.insertBench(benchFormDto);
        return "redirect:/";
    }

    @GetMapping("/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping("/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Bench item = (Bench) itemService.findOne(itemId);

        BenchFormDto form = new BenchFormDto();
        form.saveId(item.getId());
        form.saveName(item.getName());
        form.savePrice(item.getPrice());
        form.saveStockQuantity(item.getStockQuantity());
        form.saveSize(item.getSize());

        model.addAttribute("form", form);
        return "items/updateItemForm";
    }

    @PostMapping("/items/{itemId}/edit")
    public String updateItem(@PathVariable Long itemId,@ModelAttribute("form") BenchFormDto form) {

       itemService.updateItem(itemId,form.getName(),form.getPrice(),form.getStockQuantity());
       return "redirect:/items";
    }


}
