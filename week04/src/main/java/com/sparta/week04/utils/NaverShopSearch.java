package com.sparta.week04.utils;

        import com.sparta.week04.repository.ItemDto;
        import org.json.JSONArray;
        import org.json.JSONObject;
        import org.springframework.http.*;
        import org.springframework.stereotype.Component;
        import org.springframework.web.client.RestTemplate;

        import java.util.ArrayList;
        import java.util.List;

@Component // @RequiredArg~ 와 함께 사용할 경우 스프링이 자동 생성
public class NaverShopSearch {
    public String search(String query) {
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Naver-Client-Id", "CJ3YtKeRuMsr2ikvZM8c");
        headers.add("X-Naver-Client-Secret", "9zFoLsGn0Z");
        String body = "";

        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        ResponseEntity<String> responseEntity = rest.exchange("https://openapi.naver.com/v1/search/shop.json?query="+query, HttpMethod.GET, requestEntity, String.class);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        int status = httpStatus.value();
        String response = responseEntity.getBody();
        System.out.println("Response status: " + status);
        System.out.println(response);

        return response;
    }

    public List<ItemDto> fromJSONtoItems(String result) {
        JSONObject rjson = new JSONObject(result);
        JSONArray items = rjson.getJSONArray("items");
        List<ItemDto> ret = new ArrayList<>();
        for (int i=0; i<items.length(); i++) {
            JSONObject itemJson = items.getJSONObject(i);
            ItemDto itemDto = new ItemDto(itemJson);
            ret.add(itemDto);
        }
        return ret;
    }

    public static void main(String[] args) {
        NaverShopSearch naverShopSearch = new NaverShopSearch();
        String ret = naverShopSearch.search("아이맥");
        naverShopSearch.fromJSONtoItems(ret);
    }

}
