package outdoorShop.domain.order;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import outdoorShop.domain.delivery.Delivery;
import outdoorShop.domain.delivery.DeliveryStatus;
import outdoorShop.domain.items.OrderItem;
import outdoorShop.domain.member.Member;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    //연관관계 메서드
    public void saveMember(Member member) {
        this.member = member;
        member.getOrders().add(this);

    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.saveOrder(this);
    }

    public void addDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.saveOrder(this);
    }

    //생성메서드
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.saveMember(member);
        order.saveDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.saveStatus(OrderStatus.ORDER);
        order.saveOrderDate(LocalDateTime.now());
        return order;
    }

    //비즈니스 로직
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가불가능합니다.");
        }
        this.saveStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    //조회
    public int getTotalPrice() {
        int totalPrice = 0;
        return orderItems
                .stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();
    }

    public void saveId(Long id) {
        this.id = id;
    }

    public void saveOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public void saveDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public void saveOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public void saveStatus(OrderStatus status) {
        this.status = status;
    }
}
