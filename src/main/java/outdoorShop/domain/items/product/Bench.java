package outdoorShop.domain.items.product;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import outdoorShop.domain.items.Item;

@Entity
@Getter
@NoArgsConstructor
public class Bench extends Item {

    private String material;
    private String size;

    @Builder
    public Bench(String name,int price, int stockQuantity,String material, String size) {
        this.saveName(name);
        this.savePrice(price);
        this.saveStock(stockQuantity);
        this.material = material;
        this.size = size;
    }


    public void saveMaterial(String material) {
        this.material = material;
    }

    public void saveSize(String size) {
        this.size = size;
    }
}
