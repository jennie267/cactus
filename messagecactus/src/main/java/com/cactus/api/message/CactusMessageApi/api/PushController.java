package com.cactus.api.message.CactusMessageApi.api;

import com.cactus.api.message.CactusMessageApi.domain.Push;
import com.cactus.api.message.CactusMessageApi.domain.SendType;
import com.cactus.api.message.CactusMessageApi.service.PushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * CactusMessageApi
 * Class: PushController
 * Created by hapo on 2019-11-15.
 * Description:
 */

@RestController
@RequestMapping(value = "/message/push")
public class PushController {

    @Autowired
    PushService pushService;

    @GetMapping(value = "/type/{sendType}/id/{sendId}")
    public List<Push> findBySendTypeAndSendIdOrderBySendTimeDesc(@PathVariable SendType sendType, @PathVariable Long sendId){
        return pushService.findBySendTypeAndSendIdOrderByInsertTimeDesc(sendType, sendId);
    }

    @GetMapping(value = "/user/{id}")
    public List<Push> findByUserIdOrderBySendTimeDesc(@PathVariable Long id){
        return pushService.findByUserIdOrderByInsertTimeDesc(id);
    }

    @PostMapping
    public Optional<Push> sendPush(@RequestBody Push push){
        pushService.sendPushMessage(push);
        return Optional.of(push);
    }

}
