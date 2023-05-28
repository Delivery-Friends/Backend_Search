package Backend.Search.domain.dto.searchResult.Res;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Fields {
    String requestURI;
    String queryString;
}
