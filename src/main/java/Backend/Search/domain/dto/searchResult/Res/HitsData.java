package Backend.Search.domain.dto.searchResult.Res;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HitsData {
    String _index;
    String _type;
    String _id;
    float _score;

    Fields fields;
}
