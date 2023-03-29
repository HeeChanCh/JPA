package parcticeExample3;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private Category parent;

    private String name;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    // 예시기 때문에 다대다 관계 매핑을 썼지만 실전에선 사용 XXX
    @ManyToMany
    @JoinTable(name = "CATEGORY_ITEM",
               joinColumns = @JoinColumn(name = "CATEGORY_ID"),
               inverseJoinColumns = @JoinColumn(name = "ITEM_ID")
    )
    private List<Item> items = new ArrayList<>();
}
