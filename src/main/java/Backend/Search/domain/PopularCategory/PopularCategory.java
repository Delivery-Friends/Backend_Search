package Backend.Search.domain.PopularCategory;

import lombok.Getter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;

@Document(indexName = "category")
@Getter
public class PopularCategory {
    @Id
    @Field(type = FieldType.Keyword)
    String id;

    @Field(type = FieldType.Text)
    String category;

    @Field(type = FieldType.Keyword)
    Long time;

    public PopularCategory(String id, String category, Long time) {
        this.id = id;
        this.category = category;
        this.time = time;
    }
}
