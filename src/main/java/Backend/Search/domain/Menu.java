package Backend.Search.domain;

import Backend.Search.domain.dto.MenuReq;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Document(indexName = "menu")
@Getter
@NoArgsConstructor
public class Menu {
    @Id
    @Field(type = FieldType.Keyword)
    String id;

    @Field(type = FieldType.Text)
    String name;

    public Menu(String id, MenuReq req) {
        this.id = id;
        this.name = req.getName();
    }
}
