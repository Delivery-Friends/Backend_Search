package Backend.Search.domain.dto.searchResult.Res;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Hits {
    ElasticTotal total;
    float max_score;
    List<HitsData> hits;
}
