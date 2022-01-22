package com.cactus.user.util;

import com.cactus.user.domain.dto.MessageDTO;
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

    public static ResponseEntity<Map> getToken(String id, String password){
        String url = "http://zuul.paas-ta.org/auth/login";
        //header setting
        HttpHeaders headers = new HttpHeaders();


        String json = " {\n" +
                "        \"id\":\""+id+"\",\n" +
                "        \"password\":\""+password+"\"\n" +
                "    }";

        HttpEntity<String> httpEntity = new HttpEntity<String>(json, headers);

        Map<String, String> params = new HashMap<>();
        params.put("id",id);
        params.put("password",password);


        return restTemplate.exchange(url, HttpMethod.POST, httpEntity, Map.class, params);
    }

    public static ResponseEntity<MessageDTO> getLastMessageFromChild(int parentId, int childId, String authHeader){
        String url = "http://zuul.paas-ta.org/message/first/"+parentId+"/"+childId;
        //header setting
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization",authHeader);

        HttpEntity<String> httpEntity = new HttpEntity<String>(headers);

        Map<String, String> params = new HashMap<>();

        return restTemplate.exchange(url, HttpMethod.GET, httpEntity, MessageDTO.class, params);
    }

    public static ResponseEntity<String> post(String url){
        return restTemplate.postForEntity(url, "Post Request", String.class);
    }
}
