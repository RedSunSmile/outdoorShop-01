package outdoorShop;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import outdoorShop.domain.delivery.Delivery;
import outdoorShop.domain.delivery.address.Address;
import outdoorShop.domain.items.OrderItem;
import outdoorShop.domain.items.product.Bench;
import outdoorShop.domain.member.Member;
import outdoorShop.domain.order.Order;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit1() {
            Member member1 = createMember("김봉팔", "서울", "1", "12345");
            em.persist(member1);

            Bench bench1 = new Bench();
            bench1.saveName("ADB-232");
            bench1.savePrice(230000);
            bench1.saveStockQuantity(100);
            em.persist(bench1);

            Bench bench2 = new Bench();
            bench2.saveName("ADB-256");
            bench2.savePrice(250000);
            bench2.saveStockQuantity(100);
            em.persist(bench2);

            OrderItem orderItem1 = OrderItem.createOrderItem(bench1, 230000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(bench2, 500000, 2);

            Delivery delivery = createDelivery(member1);
            Order order = Order.createOrder(member1, delivery, orderItem1, orderItem2);
            em.persist(order);

        }

        private Member createMember(String name, String city, String street, String zipcode) {
            Member member = new Member();
            member.saveName(name);
            member.saveAddress(new Address(city, street, zipcode));
            em.persist(member);
            return member;
        }

        public void dbInit2() {
            Member member2 = createMember("나상실","평창","3","59593");
            em.persist(member2);

            Bench bench1 = new Bench();
            bench1.saveName("ADB-231");
            bench1.savePrice(240000);
            bench1.saveStockQuantity(100);
            em.persist(bench1);

            Bench bench2 = new Bench();
            bench2.saveName("ADB-255");
            bench2.savePrice(300000);
            bench2.saveStockQuantity(100);
            em.persist(bench2);

            OrderItem orderItem1 = OrderItem.createOrderItem(bench1, 480000, 2);
            OrderItem orderItem2 = OrderItem.createOrderItem(bench2, 300000, 1);

            Delivery delivery = createDelivery(member2);
            Order order = Order.createOrder(member2, delivery, orderItem1, orderItem2);
            em.persist(order);

        }
    }

    private static Delivery createDelivery(Member member) {
        Delivery delivery = new Delivery();
        delivery.saveAddress(member.getAddress());
        return delivery;
    }

}

