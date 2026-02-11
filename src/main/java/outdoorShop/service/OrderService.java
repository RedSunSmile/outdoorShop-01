package outdoorShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import outdoorShop.domain.delivery.Delivery;
import outdoorShop.domain.items.Item;
import outdoorShop.domain.items.OrderItem;
import outdoorShop.domain.member.Member;
import outdoorShop.domain.order.Order;
import outdoorShop.repository.ItemRepository;
import outdoorShop.repository.MemberRepository;
import outdoorShop.repository.OrderRepository;
import outdoorShop.repository.OrderSearch;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    //주문
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        //회원(엔터티) & 제품조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        Delivery delivery = new Delivery();
        delivery.saveAddress(member.getAddress());

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        Order order = Order.createOrder(member, delivery, orderItem);

        //주문저장
        orderRepository.save(order);

        return order.getId();
    }

    //취소
    @Transactional
    public void cancelOrder(Long Id) {
        Order order = orderRepository.findOne(Id);
        order.cancel();
    }


    //주문검색
    public List<Order> findOrders(OrderSearch orderSearch){
        return orderRepository.findAllByString(orderSearch);
    }
}
