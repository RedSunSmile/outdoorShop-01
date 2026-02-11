package outdoorShop.controller;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter //Thymeleaf용
@NoArgsConstructor
public class BenchFormDto {

    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private String material;
    private String size;

    // 명시적 호출용 (Controller에서 사용)
    public void saveId(Long id) { this.id = id; }
    public void saveName(String name) { this.name = name; }
    public void savePrice(int price) { this.price = price; }
    public void saveStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }
    public void saveMaterial(String material) { this.material = material; }
    public void saveSize(String size) { this.size = size; }
}
