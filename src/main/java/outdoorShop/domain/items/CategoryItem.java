package outdoorShop.domain.items;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import outdoorShop.domain.category.Category;

@Entity
@Getter
public class CategoryItem {
    @Id
    @GeneratedValue
    @Column(name="category_item_id")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;

    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="category_id")
    private Category category;

    public void saveId(Long id) {
        this.id = id;
    }

    public void saveItem(Item item) {
        this.item = item;
    }

    public void saveCategory(Category category) {
        this.category = category;
    }

    public void savetItem(Item item) {

    }
}
