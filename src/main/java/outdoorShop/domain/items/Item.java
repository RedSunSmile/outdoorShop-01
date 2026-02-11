package outdoorShop.domain.items;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import outdoorShop.exception.NotEnoughStockException;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter
public class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    @JsonIgnore
    @OneToMany(mappedBy = "item",cascade = CascadeType.ALL)
    private List<CategoryItem> categoryItems = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "item",cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    private String name;
    private int price;
    private int stockQuantity;

    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.saveItem(this);
    }

    public void addCategoryItem(CategoryItem categoryItem) {
        this.categoryItems.add(categoryItem);
        categoryItem.savetItem(this);
    }
    //비즈니스로직
    //stock 증가,감소
    public void saveStock(int quantity){
        this.stockQuantity+=quantity;
    }

    public void removeStock(int quantity){
        int restStock=this.stockQuantity-quantity;
        if(restStock<0){
            throw new NotEnoughStockException("재고 수량 부족 예외가 발행해야 한다.");
        }
        this.stockQuantity=restStock;
    }

    public void saveId(Long id) {
        this.id = id;
    }

    public void saveCategoryItems(List<CategoryItem> categoryItems) {
        this.categoryItems = categoryItems;
    }

    public void saveOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public void saveName(String name) {
        this.name = name;
    }

    public void savePrice(int price) {
        this.price = price;
    }

    public void saveStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
}
