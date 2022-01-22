package com.cactus.server.Zuul.util;

import com.cactus.server.Zuul.filter.PreFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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

    public static String checkToken(){
        return restTemplate.getForObject("http://authcactus.paas-ta.org/auth/test", String.class);
    }

    public static ResponseEntity<String> getResponseEntity(String url, String key){
        //header setting
        HttpHeaders headers = new HttpHeaders();
        headers.add(PreFilter.AUTHENTICATION_HEADER_NAME, key);


        HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(headers);

        Map<String, String> params = new HashMap<>();

        return restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class, params);
    }

    public static ResponseEntity<String> post(String url){
        return restTemplate.postForEntity(url, "Post Request", String.class);
    }
}
