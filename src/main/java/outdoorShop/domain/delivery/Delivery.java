package outdoorShop.domain.delivery;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import outdoorShop.domain.delivery.address.Address;
import outdoorShop.domain.order.Order;

@Entity
@Getter
public class Delivery {
    @Id
    @GeneratedValue
    @Column(name="delivery_id")
    private Long id;

    @JsonIgnore
    @OneToOne(mappedBy = "delivery",fetch=FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;//READY,COMP


    public void saveId(Long id) {
        this.id = id;
    }

    public void saveOrder(Order order) {
        this.order = order;
    }

    public void saveAddress(Address address) {
        this.address = address;
    }

    public void saveStatus(DeliveryStatus status) {
        this.status = status;
    }
}
