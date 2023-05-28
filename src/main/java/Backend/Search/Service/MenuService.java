package Backend.Search.Service;

import Backend.Search.Config.feign.*;
import Backend.Search.domain.Menu;
import Backend.Search.Repository.MenuRepository;
import Backend.Search.domain.PopularCategory.PopularCategory;
import Backend.Search.domain.PopularCategory.PopularCategoryRes;
import Backend.Search.domain.dto.MenuReq;
import Backend.Search.domain.dto.MenuRes;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuService {

    private final ElasticsearchOperations elasticsearchOperations;
    private final MenuRepository menuRepository;
    private final CountAnalyzeFeign countAnalyzeFeign;


    public void addCategoryCount(String name) {
        String uuid = UUID.randomUUID().toString().substring(20);
        long nowTime = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        PopularCategory menu = new PopularCategory(uuid, name, nowTime);

        elasticsearchOperations.save(menu);
    }

    public void addMenu(MenuReq req) {
        String uuid = UUID.randomUUID().toString().substring(20);
        Menu menu = new Menu(uuid, req);
        menuRepository.save(menu);
        elasticsearchOperations.save(menu);
    }

    public List<PopularCategoryRes> getPopularCategory() {
        List<PopularCategoryRes> result = new ArrayList<>();
        List<String> names = new ArrayList<>();
        names.add("치킨");
        names.add("양식");
        names.add("일식");
        names.add("한식");
        names.add("피자");
        names.add("중식");

        long pastTime = LocalDateTime.now().minusHours(1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long nowTime = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        System.out.println(pastTime);
        System.out.println(nowTime);
        for (String name : names) {
            String body = "{\n" +
                                "  \"query\" : {\n" +
                                "    \"bool\": {\n" +
                                "      \"must\": [\n" +
                                "        {\n" +
                                "          \"match\": {\n" +
                                "          \"category\": \"" +
                                                        name +
                                                            "\"\n" +
                                "          }\n" +
                                "        },\n" +
                                "        {\n" +
                                "          \"range\": {\n" +
                                "            \"time\": {\n" +
                                "              \"gte\": \"" +
                                                        pastTime +
                                                            "\",\n" +
                                "              \"lte\": \"" +
                                                        nowTime +
                                                            "\"\n" +
                                "            }\n" +
                                "          }\n" +
                                "        }\n" +
                                "      ]\n" +
                                "    }\n" +
                                "  }\n" +
                                "}";

            CategoryAnalyzeDto categoryAnalyzeDto = countAnalyzeFeign.categoryAnalyze(body);
            System.out.println(categoryAnalyzeDto.getCount());
            result.add(new PopularCategoryRes(name, categoryAnalyzeDto.getCount()));
        }
        return result;


//
//        for (String name : names) {
//            NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
//                    .withQuery(boolQuery()
//                    .must(matchQuery("message", "/performance/search"))
//                    .must(matchPhraseQuery("logger_name", "LOGSTASH"))
//                    .must(rangeQuery("@timestamp")
//                            .gte(LocalDateTime.now().truncatedTo(ChronoUnit.HOURS).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
//                            .lte(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())))
//                .build();
//        SearchHits<String> documents = elasticsearchOperations.count(searchQuery, getEn.class);
//        for (SearchHit<String> document : documents) {
//            document.get
//        }
    }

    public List<MenuRes> getMenu(String name) {
        Criteria criteria = Criteria.where("basicProfile.name").contains(name);
        Query query = new CriteriaQuery(criteria);
        SearchHits<Menu> search = elasticsearchOperations.search(query, Menu.class);
        List<Menu> collect = search.stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
        List<MenuRes> res = new ArrayList<>();
        for (Menu menu : collect) {
            res.add(new MenuRes(menu));
        }
        return res;

//        System.out.println(name);
//        List<Menu> menus = menuRepository.searchByName(name);
//        System.out.println("size : " + menus.size());
//        List<MenuRes> result = new ArrayList<>();
//        for (Menu menu : menus) {
//            MenuRes menuRes = new MenuRes(menu.getName());
//            result.add(menuRes);
//        }
//        return result;
    }

//    public List<String> getSearchResult() {
//
//        String str = "{\n" +
//                "  \"query\": {\n" +
//                "    \"bool\": {\n" +
//                "      \"must\": [\n" +
//                "        {\n" +
//                "          \"match\": {\n" +
//                "            \"requestURI\": {\n" +
//                "            \"query\" : \"/menu/search\"" +
//                "          }\n" +
//                "          }\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"range\" : {\n" +
//                "            \"@timestamp\" : {\n" +
//                "              \"from\" : ";
//        str += LocalDateTime.now().truncatedTo(ChronoUnit.HOURS).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
//        str += ",\n" +
//                "              \"to\" : ";
//        str += LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
//        str += ",\n" +
//                "              \"include_lower\" : true,\n" +
//                "              \"include_upper\" : true,\n" +
//                "              \"boost\" : 1.0\n" +
//                "            }\n" +
//                "        }\n" +
//                "        }\n" +
//                "      ]\n" +
//                "    }\n" +
//                "  },\n" +
//                "  \"fields\":[\n" +
//                "      \"queryString\",\n" +
//                "      \"requestURI\",\n" +
//                "      \"@timestamp\"\n" +
//                "      ],\n" +
//                "      \"_source\": false\n" +
//                "}";
////        LocalDateTime.now().truncatedTo(ChronoUnit.HOURS).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
////        LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//        RestTemplate restTemplate = new RestTemplate();
//        HttpEntity<String> request =
//                new HttpEntity<String>(str, httpHeaders);
//
//        GetSearchLog res = restTemplate.postForObject("http://localhost:9200/application-accesslog-2023-05-28/_search", request, GetSearchLog.class);
//        System.out.println(res);
//        return null;
//    }

//        System.out.println(res);
//        System.out.println("총길이 : " + res.length());
//        List<String> result = new ArrayList<>();
//        while(res.indexOf("menu=") < res.length()) {
//            String tempStr = res;
//            System.out.println("찾은 위치 : " + res.indexOf("menu="));
//            System.out.println("찾은 값 : " + res.charAt(res.indexOf("menu=") + 4));
//            System.out.println("해당부분 substring : " + res.substring(res.indexOf("menu=") + 4));
//
//            tempStr = res.substring(res.indexOf("menu=") + 4);
//            res = res.substring(res.indexOf("menu=") + 4);
//            tempStr = tempStr.substring(0, tempStr.indexOf("\""));
//
//            System.out.println("잔여 문자열 : " + res);
//            System.out.println("저장 : " + tempStr);
//            result.add(tempStr);
//        }
////        System.out.println("personResultAsJsonStr = " + personResultAsJsonStr);
//        return null;


//        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
//                .withQuery(boolQuery()
//                        .must(matchQuery("requestURI", "/menu/search"))
//                        .must(rangeQuery("@timestamp")
//                                .gte(LocalDateTime.now().truncatedTo(ChronoUnit.HOURS).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
//                                .lte(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()))
//                ).build();
//        SearchHits<String> search = elasticsearchOperations.search(nativeSearchQuery, String.class);
//
//        System.out.println(nativeSearchQuery.getQuery());
//        System.out.println(nativeSearchQuery.getFields());
//
//        SearchHits<String> documents = elasticsearchOperations
//                .search(nativeSearchQuery, String.class, IndexCoordinates.of("menu"));
//
//        System.out.println(documents.getTotalHits());
//
//        System.out.println(documents.get());
//        List<String> results = new ArrayList<>();
//        for (SearchHit<GetSearchLog> document : documents) {
//            List<HitsData> hits = document.getContent().getHits().getHits();
//            for (HitsData hit : hits) {
//                String substring = hit.getFields().getQueryString().substring(6);
//                System.out.println(substring);
//                results.add(substring);
//            }
//        }
//        return results;

////        return documents;
//
//        String query = nativeSearchQuery.getQuery().toString();
//        List<Person> content = personRepository.search(nativeSearchQuery).getContent();
//
//        /**
//         *
//         */
//
//        List<String> searchResult = new ArrayList<>();
//
//        String reqURL = "http://localhost:9200/application-accesslog-"
//                + LocalDateTime.now().getYear() + "-";
//
//        if (LocalDateTime.now().getMonth().getValue() < 10) {
//            reqURL += "0" + LocalDateTime.now().getMonth().getValue();
//        } else {
//            reqURL += LocalDateTime.now().getMonth().getValue();
//        }
//        reqURL += "-";
//        if (LocalDateTime.now().getDayOfMonth() < 10) {
//            reqURL += "0" + LocalDateTime.now().getDayOfMonth();
//        } else {
//            reqURL += LocalDateTime.now().getDayOfMonth();
//        }
//        reqURL += "/_search";
//
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Type", "application/json");
//
//        ElasticFields elasticFields = new ElasticFields();
//        List<String> stringList = new ArrayList<>();
//        stringList.add("queryString");
//        stringList.add("requestURI");
//        elasticFields.setFields(stringList);
//        HttpEntity<ElasticFields> entity = new HttpEntity<>(elasticFields, headers);
//        System.out.println(reqURL);
//        System.out.println(entity);
//
//        ResponseEntity<GetSearchLog> result = restTemplate.exchange(reqURL, HttpMethod.POST, entity, GetSearchLog.class);
//
//        List<HitsData> hits = result.getBody().getHits().getHits();
//        for (HitsData hit : hits) {
//            String substring = hit.getFields().getQueryString().substring(6);
//            System.out.println(substring);
//            searchResult.add(substring);
//        }
//        System.out.println(searchResult.size());
//        return searchResult;
//    }

//    public List<String> getPopularMenu() {
//        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
//                .withQuery(boolQuery()d
//                        .must(matchQuery("message", "/performance/search"))
//                        .must(matchPhraseQuery("logger_name", "LOGSTASH"))
//                        .must(rangeQuery("@timestamp")
//                                .gte(LocalDateTime.now().truncatedTo(ChronoUnit.HOURS).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
//                                .lte(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())))
//                .build();
//        SearchHits<String> documents = elasticsearchOperations
//                . search(searchQuery, String.class);
//        for (SearchHit<String> document : documents) {
//            document.get
//        }
//        return documents;
//    }
}
