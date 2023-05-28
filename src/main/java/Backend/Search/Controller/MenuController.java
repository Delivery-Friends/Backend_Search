package Backend.Search.Controller;

import Backend.Search.Service.MenuService;
import Backend.Search.domain.Menu;
import Backend.Search.domain.PopularCategory.PopularCategoryReq;
import Backend.Search.domain.PopularCategory.PopularCategoryRes;
import Backend.Search.domain.dto.MenuReq;
import Backend.Search.domain.dto.MenuRes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @PostMapping("/addMenu")
    public void addMenu(@RequestBody MenuReq req) {
        menuService.addMenu(req);
    }

    @PostMapping("/addCategoryFound")
    public void addCategoryCount(@RequestBody PopularCategoryReq req) {
        menuService.addCategoryCount(req.getName());
    }

    @GetMapping("/category/analyze")
    public List<PopularCategoryRes> getPopularCategory() {
        return menuService.getPopularCategory();
    }

    @GetMapping("/menu/search")
    public List<MenuRes> getMenu(String menu) {
        return menuService.getMenu(menu);
    }

//    @GetMapping("/analyze")
//    public List<String> getMenu() {
//        return menuService.getSearchResult();
//    }
}