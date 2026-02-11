package outdoorShop.domain.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import outdoorShop.domain.delivery.address.Address;
import outdoorShop.domain.order.Order;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    public void saveId(Long id) {
        this.id = id;
    }

    public void saveName(String name) {
        this.name = name;
    }

    public void saveAddress(Address address) {
        this.address = address;
    }

    public void saveOrders(List<Order> orders) {
        this.orders = orders;
    }
}
