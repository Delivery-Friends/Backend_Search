package Backend.Search.domain.dto.searchResult.Res;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetSearchLog {
    Long took;
    Boolean timed_out;
    ElasticShards _shards;
    Hits hits;

}
