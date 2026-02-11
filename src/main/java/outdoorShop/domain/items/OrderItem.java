package outdoorShop.domain.items;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import outdoorShop.domain.order.Order;

@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class OrderItem {
    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;
    private int count;//주문수량


    //생성메서드
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.saveItem(item);
        orderItem.saveOrderPrice(orderPrice);
        orderItem.saveCount(count);

        item.removeStock(count);
        return orderItem;
    }

    //비즈니스로직
    public void cancel() {
        getItem().saveStock(count);
    }

//조회
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }

    public void saveId(Long id) {
        this.id = id;
    }

    public void saveItem(Item item) {
        this.item = item;
    }

    public void saveOrder(Order order) {
        this.order = order;
    }

    public void saveOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }

    public void saveCount(int count) {
        this.count = count;
    }
}
