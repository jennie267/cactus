package com.cactus.api.message.CactusMessageApi.service;

import com.cactus.api.message.CactusMessageApi.domain.Push;
import com.cactus.api.message.CactusMessageApi.domain.PushRepository;
import com.cactus.api.message.CactusMessageApi.domain.SendType;
import com.cactus.api.message.CactusMessageApi.domain.dto.UserDTO;
import com.cactus.api.message.CactusMessageApi.domain.push.EtcData;
import com.cactus.api.message.CactusMessageApi.domain.push.Notification;
import com.cactus.api.message.CactusMessageApi.domain.push.PushMessage;
import com.cactus.api.message.CactusMessageApi.util.Const;
import com.cactus.api.message.CactusMessageApi.util.RestTemplateUtil;
import com.google.gson.Gson;
import com.squareup.okhttp.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * CactusMessageApi
 * Class: PushServiceImpl
 * Created by hapo on 2019-11-15.
 * Description:
 */

@Service
public class PushServiceImpl implements PushService {

    @Autowired
    PushRepository pushRepository;

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    public List<Push> findBySendTypeAndSendIdOrderByInsertTimeDesc(SendType sendType, Long sendId) {
        return pushRepository.findBySendTypeAndSendIdOrderByInsertTimeDesc(sendType, sendId);
    }

    @Override
    public List<Push> findByUserIdOrderByInsertTimeDesc(Long userId) {
        return pushRepository.findByUserIdOrderByInsertTimeDesc(userId);
    }

    @Override
    public void sendPushMessage(Push push) {
        if (push.getUserToken()==null || "".equals(push.getUserToken())){
            if(push.getUserId() == null) return;

            UserDTO receivedUser = RestTemplateUtil.getUserTokenForPush(push.getUserId()).getBody();
            push.setUserToken(receivedUser.getPushToken());
        }

        //push 보내기.
        sendPush(push);
        //push 저장하기
        save(push);
    }

    @Override
    public Optional<Push> save(Push push) {
        return Optional.of(pushRepository.save(push));
    }

    void sendPush(Push push){
        //json 객체 생성
        String json = pushMessageObjectToJson(push);

        //HTTP Client 객체 생성하고 위에서 만든 json 넣기.
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(Const.PUSH_SEND_URL)
                .addHeader("Authorization", "key=" + Const.PUSH_API_KEY)
                .post(body)
                .build();

        //HTTP post 요청
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()) {

                }
            }
        });
    }

    private String pushMessageObjectToJson(Push push){

        Notification notification = Notification.builder()
                .title(push.getTitle())
                .body(push.getContents())
                .click_action("FLUTTER_NOTIFICATION_CLICK")
                .build();

        EtcData data = EtcData.builder()
                .title(push.getTitle())
                .body(push.getContents())
                .click_action("FLUTTER_NOTIFICATION_CLICK")
                .build();

        PushMessage pushMessage = PushMessage.builder()
                .notification(notification)
                .data(data)
                .priority("high")
                .to(push.getUserToken())
                .build();

        Gson gson = new Gson();
        String json = gson.toJson(pushMessage);

        return json;
    }
}
