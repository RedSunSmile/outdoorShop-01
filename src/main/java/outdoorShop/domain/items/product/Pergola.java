package outdoorShop.domain.items.product;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import outdoorShop.domain.items.Item;

@Entity
@DiscriminatorValue("Per")
@Getter
public class Pergola extends Item {
    private String material;
    private String size;

}
