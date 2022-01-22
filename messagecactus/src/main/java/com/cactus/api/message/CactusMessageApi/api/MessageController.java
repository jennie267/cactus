package com.cactus.api.message.CactusMessageApi.api;

import com.cactus.api.message.CactusMessageApi.domain.Message;
import com.cactus.api.message.CactusMessageApi.domain.dto.MessageDTO;
import com.cactus.api.message.CactusMessageApi.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * CactusMessageApi
 * Class: MessageController
 * Created by hapo on 2019-11-15.
 * Description:
 */

@RestController
@RequestMapping(value = "/message")
public class MessageController {

    @Autowired
    MessageService messageService;

    @GetMapping(value = "/receive/user/{id}")
    public Map<String, List<MessageDTO>> findByReceivedUserIdOrderByInsertTimeDesc(@PathVariable Long id, @RequestHeader(value = "authorization", required = false) String authHeader){

        List<MessageDTO> list = messageService.findByReceivedUserIdOrderByInsertTimeDesc(id,authHeader);

        Map<String, List<MessageDTO>> messagesMap = new HashMap<>();
        messagesMap.put("messages", list);

        return messagesMap;
    }

    @GetMapping(value = "/send/user/{received_user_id}/{send_user_id}")
    public Map findByReceivedUserIdOrderByInsertTimeDesc(@PathVariable Long received_user_id, @PathVariable Long send_user_id, @RequestHeader(value = "authorization", required = false) String authHeader){

//        for (String elem: headers.keySet()) {
//            System.out.println(elem + " : " + headers.get(elem));
//        }

        List<MessageDTO> list = messageService.findByReceivedUserIdAndSendUserIdOrderByInsertTimeDesc(received_user_id, send_user_id,authHeader);

        Map<String, List<MessageDTO>> messagesMap = new HashMap<>();
        messagesMap.put("messages", list);

        return messagesMap;
    }

    @GetMapping(value = "/first/{received_user_id}/{send_user_id}")
    public Optional<Message> findFirstByReceivedUserIdAndSendUserIdOrderByInsertTimeDesc(@PathVariable Long received_user_id, @PathVariable Long send_user_id){
        return messageService.findFirstByReceivedUserIdAndSendUserIdOrderByInsertTimeDesc(received_user_id, send_user_id);
    }

    @PostMapping
    public Optional<Message> sendMessage(@RequestBody Message message){
        return messageService.sendMessage(message);
    }

    @GetMapping(value = "/{message_id}")
    public Optional<Message> findByMessageId(@PathVariable Long message_id){
        return messageService.findByMessageId(message_id);
    }


    @PostMapping("/like")
    public boolean updateByMessageId(@RequestBody Message message){
        if (messageService.updateByMessageId(message.getMessageId(), message.getIsLike())>0) return true;
        else return  false;
    }

    @GetMapping(value = "/today/{received_user_id}")
    public Map findByUserIdAndToday(@PathVariable Long received_user_id, @RequestHeader(value = "authorization", required = false) String authHeader){
        List<MessageDTO> list = messageService.findByUserIdAndToday(received_user_id, authHeader);

        Map<String, List<MessageDTO>> messagesMap = new HashMap<>();
        messagesMap.put("messages", list);

        return messagesMap;
    }

}
