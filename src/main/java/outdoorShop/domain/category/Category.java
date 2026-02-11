package outdoorShop.domain.category;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import outdoorShop.domain.items.CategoryItem;
import outdoorShop.domain.items.Item;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue
    @Column(name="category_id")
    private Long id;

    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    private List<CategoryItem> categoryItems=new ArrayList<>();

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="categorySelf_id")
    private Category category;

   private LocalDateTime categoryDate;

   @Enumerated(EnumType.STRING)
    private CategoryStatus status;

   //연관관계메서드
   public void addCategoryItem(CategoryItem categoryItem){
       categoryItems.add(categoryItem);
       categoryItem.saveCategory(this);
   }

}
