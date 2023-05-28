package Backend.Search.Config.feign;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(name="analyze", url="http://jaehwan.shop:9200" )
public interface CountAnalyzeFeign {
    @GetMapping(value = "/category/_count", headers = "Content-Type= application/json")
    CategoryAnalyzeDto categoryAnalyze(String str);
}
