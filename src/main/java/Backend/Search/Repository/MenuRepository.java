package Backend.Search.Repository;

import Backend.Search.domain.Menu;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface MenuRepository extends ElasticsearchRepository<Menu, Long> {
    List<Menu> searchByName(String name);
}
