package com.cactus.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Auth
 * Class: RestTemplateUtil
 * Created by hapo on 2019-11-22.
 * Description:
 */

@Component
public class RestTemplateUtil {

    private static RestTemplate restTemplate;


    @Autowired
    public RestTemplateUtil(RestTemplate restTemplate) {
        this.restTemplate=restTemplate;
    }

    public static ResponseEntity<Map> alertScheduleMessage(Map<String, String> scheduleMap){
        String url = "http://zuul.paas-ta.org/message/push";
        //header setting
        HttpHeaders headers = new HttpHeaders();
        Charset utf8 = Charset.forName("UTF-8");
        MediaType mediaType = new MediaType("application","json",utf8);
        headers.setContentType(mediaType);

        String title = String.valueOf(scheduleMap.get("title"));
        String contents = String.valueOf(scheduleMap.get("contents"));
        String userId = String.valueOf(scheduleMap.get("userId"));
        String sendType = "SCHEDULE";
        String sendId = String.valueOf(scheduleMap.get("schdId"));

        String json = " {\n" +
                "        \"title\":\""+title+"\",\n" +
                "        \"contents\":\""+contents+"\",\n" +
                "        \"userId\":\""+userId+"\",\n" +
                "        \"sendType\":\""+sendType+"\",\n" +
                "        \"sendId\":\""+sendId+"\"\n" +
                "    }";

        HttpEntity<String> httpEntity = new HttpEntity<String>(json, headers);

        Map<String, String> params = new HashMap<>();
        params.put("title",title);
        params.put("contents",contents);
        params.put("userId",userId);
        params.put("sendType",sendType);
        params.put("sendId",sendId);

        return restTemplate.exchange(url, HttpMethod.POST, httpEntity, Map.class, params);
    }

    public static ResponseEntity<String> post(String url){
        return restTemplate.postForEntity(url, "Post Request", String.class);
    }
}
