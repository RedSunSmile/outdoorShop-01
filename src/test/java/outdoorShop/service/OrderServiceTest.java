package outdoorShop.service;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import outdoorShop.domain.delivery.address.Address;
import outdoorShop.domain.items.Item;
import outdoorShop.domain.items.product.Bench;
import outdoorShop.domain.member.Member;
import outdoorShop.domain.order.Order;
import outdoorShop.domain.order.OrderStatus;
import outdoorShop.exception.NotEnoughStockException;
import outdoorShop.repository.OrderRepository;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class OrderServiceTest {
    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        //given
        Member member = createMember();

        Bench bench = createBench("ADB-256", 250000, 10);

        em.flush(); //즉시 ID생성
        int orderCount = 2;

        //when
        Long orderId = orderService.order(member.getId(), bench.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "상품 주문시 상태는 ORDER");
        assertEquals(getOrder.getOrderItems().size(), 1, "주문한 상품 종류 수가 정확해야 한다.");
        assertEquals(250000 * orderCount, getOrder.getTotalPrice());
        assertEquals(8, bench.getStockQuantity(), "주문 수량만큼 재고가 줄어야 한다.");

    }

    @Test
    public void 상품주문_재고수량초과() throws Exception {
        //given
        Member member = createMember();
        Item item = createBench("ADB-256", 250000, 10);
        int orderCount = 11;

        //when & then
        assertThatExceptionOfType(NotEnoughStockException.class)
                .isThrownBy(() -> orderService.order(member.getId(), item.getId(), orderCount))
                .withMessageContaining("재고 수량 부족 예외가 발행해야 한다.");

    }

    @Test
    public void 주문취소() throws Exception {
        //given
        Member member = createMember();
        Bench item = createBench("ADB-256", 250000, 10);

        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.CANCEL, getOrder.getStatus(), "주문 취소시 상태는 CANCEL 이다.");
        assertEquals(10, item.getStockQuantity(), "주문이 취소된 상품은 그만큼 재고가 증가해야 한다.");

    }

    private Bench createBench(String name, int price, int stockQuantity) {
        Bench bench = new Bench();
        bench.saveName(name);
        bench.savePrice(price);
        bench.saveStockQuantity(stockQuantity);
        em.persist(bench);
        return bench;
    }

    private Member createMember() {
        Member member = new Member();
        member.saveName("강짱구");
        member.saveAddress(new Address("경기", "부천", "14758"));
        em.persist(member);
        return member;
    }
}
