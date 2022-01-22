package com.cactus.api.message.CactusMessageApi.util;

import com.cactus.api.message.CactusMessageApi.domain.dto.UserDTO;
import org.apache.catalina.User;
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

    public static ResponseEntity<UserDTO> getUserName(Long id, String authHeader){
        String url = "http://zuul.paas-ta.org/user/user-id/"+id;
        //header setting
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization",authHeader);

        HttpEntity<String> httpEntity = new HttpEntity<String>(headers);

        Map<String, String> params = new HashMap<>();

        return restTemplate.exchange(url, HttpMethod.GET, httpEntity, UserDTO.class, params);
    }
    public static ResponseEntity<UserDTO> getUserTokenForPush(Long id){
        String url = "http://usercactus.paas-ta.org/user/user-id/"+id;
        //header setting
        HttpHeaders headers = new HttpHeaders();
        headers.add("AUTH-OWNER","CACTUS");

        HttpEntity<String> httpEntity = new HttpEntity<String>(headers);

        Map<String, String> params = new HashMap<>();

        return restTemplate.exchange(url, HttpMethod.GET, httpEntity, UserDTO.class, params);
    }

    public static ResponseEntity<String> post(String url){
        return restTemplate.postForEntity(url, "Post Request", String.class);
    }
}
