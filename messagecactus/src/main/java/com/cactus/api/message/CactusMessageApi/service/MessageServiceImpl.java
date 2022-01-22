package com.cactus.api.message.CactusMessageApi.service;

import com.cactus.api.message.CactusMessageApi.domain.Message;
import com.cactus.api.message.CactusMessageApi.domain.MessageRepository;
import com.cactus.api.message.CactusMessageApi.domain.Push;
import com.cactus.api.message.CactusMessageApi.domain.SendType;
import com.cactus.api.message.CactusMessageApi.domain.dto.MessageDTO;
import com.cactus.api.message.CactusMessageApi.domain.dto.UserDTO;
import com.cactus.api.message.CactusMessageApi.util.RestTemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * CactusMessageApi
 * Class: MessageServiceImpl
 * Created by hapo on 2019-11-15.
 * Description:
 */

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    PushService pushService;

    @Override
    public List<MessageDTO> findByReceivedUserIdOrderByInsertTimeDesc(Long receivedUserId, String authHeader) {

        List<Message> messages = messageRepository.findByReceivedUserIdOrderByInsertTimeDesc(receivedUserId);
        List<MessageDTO> messageDTOs = new ArrayList<>();

        for (Message message:messages) {

            UserDTO receiveUser = RestTemplateUtil.getUserName(message.getReceivedUserId(),authHeader).getBody();
            UserDTO sendUser = RestTemplateUtil.getUserName(message.getSendUserId(),authHeader).getBody();

            MessageDTO dto = MessageDTO.builder()
                    .messageId(message.getMessageId())
                    .contents(message.getContents())
                    .receivedUserId(message.getReceivedUserId())
                    .receivedUserName(receiveUser.getName())
                    .receivedUserProfileUrl(receiveUser.getProfileUrl())
                    .sendUserId(message.getSendUserId())
                    .sendUserName(sendUser.getName())
                    .sendUserProfileUrl(sendUser.getProfileUrl())
                    .insertTime(message.getInsertTime())
                    .isLike(message.getIsLike())
                    .build();

            messageDTOs.add(dto);
        }

        return messageDTOs;
    }

    @Override
    public List<MessageDTO> findByUserIdAndToday(Long receivedUserId, String authHeader) {
        List<Message> messages = messageRepository.findByUserIdAndToday(receivedUserId);
        List<MessageDTO> messageDTOs = new ArrayList<>();

        for (Message message:messages) {

            UserDTO receiveUser = RestTemplateUtil.getUserName(message.getReceivedUserId(),authHeader).getBody();
            UserDTO sendUser = RestTemplateUtil.getUserName(message.getSendUserId(),authHeader).getBody();

            MessageDTO dto = MessageDTO.builder()
                    .messageId(message.getMessageId())
                    .contents(message.getContents())
                    .receivedUserId(message.getReceivedUserId())
                    .receivedUserName(receiveUser.getName())
                    .receivedUserProfileUrl(receiveUser.getProfileUrl())
                    .sendUserId(message.getSendUserId())
                    .sendUserName(sendUser.getName())
                    .sendUserProfileUrl(sendUser.getProfileUrl())
                    .insertTime(message.getInsertTime())
                    .isLike(message.getIsLike())
                    .build();

            messageDTOs.add(dto);
        }
        return messageDTOs;
    }


    @Override
    @Transactional
    public List<MessageDTO> findByReceivedUserIdAndSendUserIdOrderByInsertTimeDesc(Long receivedUserId, Long sendUserId, String authHeader) {
        List<Message> messages = messageRepository.findByReceivedUserIdAndSendUserIdOrderByInsertTimeDesc(receivedUserId,sendUserId);
        List<MessageDTO> messageDTOs = new ArrayList<>();
        for (Message message:messages) {

            UserDTO receiveUser = RestTemplateUtil.getUserName(message.getReceivedUserId(),authHeader).getBody();
            UserDTO sendUser = RestTemplateUtil.getUserName(message.getSendUserId(),authHeader).getBody();

            MessageDTO dto = MessageDTO.builder()
                    .messageId(message.getMessageId())
                    .contents(message.getContents())
                    .receivedUserId(message.getReceivedUserId())
                    .receivedUserName(receiveUser.getName())
                    .receivedUserProfileUrl(receiveUser.getProfileUrl())
                    .sendUserId(message.getSendUserId())
                    .sendUserName(sendUser.getName())
                    .sendUserProfileUrl(sendUser.getProfileUrl())
                    .insertTime(message.getInsertTime())
                    .isLike(message.getIsLike())
                    .build();

            messageDTOs.add(dto);
        }

        return messageDTOs;
    }

    @Override
    public Optional<Message> findFirstByReceivedUserIdAndSendUserIdOrderByInsertTimeDesc(Long receivedUserId, Long sendUserId) {
        return messageRepository.findFirstByReceivedUserIdAndSendUserIdOrderByInsertTimeDesc(receivedUserId, sendUserId);
    }

    @Override
    public Optional<Message> sendMessage(Message message) {
        //유저 정보에서 token 가져오기

        UserDTO receivedUser = RestTemplateUtil.getUserTokenForPush(message.getReceivedUserId()).getBody();
        UserDTO sendUser = RestTemplateUtil.getUserTokenForPush(message.getSendUserId()).getBody();

        if(receivedUser != null){
            //create Push data
            Push push = Push.builder()
                    .title("[마파랑] "+sendUser.getName()+" 님의 메세지가 도착했습니다.")
                    .contents(message.getContents())
                    .insertTime(LocalDateTime.now())
                    .userId(message.getReceivedUserId())
                    .userToken(receivedUser.getPushToken())
                    .sendType(SendType.MESSAGE)
                    .sendId(message.getMessageId())
                    .build();

            //푸시 보내기.
            pushService.sendPushMessage(push);
        }


        //메세지 저장하기
        message.setInsertTime(LocalDateTime.now());
        return Optional.of(messageRepository.save(message));
    }

    @Override
    public Optional<Message> findByMessageId(Long messageId) {
        return messageRepository.findByMessageId(messageId);
    }

    @Override
    public int updateByMessageId(Long messageId, String isLike) {
        return messageRepository.updateByMessageId(messageId, isLike);
    }



}
