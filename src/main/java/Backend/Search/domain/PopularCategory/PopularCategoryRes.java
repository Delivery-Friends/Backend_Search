package Backend.Search.domain.PopularCategory;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PopularCategoryRes {
    String name;
    Long count;

    public PopularCategoryRes(String category, Long count) {
        this.name = category;
        this.count = count;
    }
}
